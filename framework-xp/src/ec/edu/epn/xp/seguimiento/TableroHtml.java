package ec.edu.epn.xp.seguimiento;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ec.edu.epn.xp.desarrollo.Equipo;
import ec.edu.epn.xp.directrices.ComandoVerificar.Resultado;
import ec.edu.epn.xp.directrices.Fase;
import ec.edu.epn.xp.planificacion.HistoriaUsuario;
import ec.edu.epn.xp.pruebas.ReporteAceptacion.Estado;
import ec.edu.epn.xp.pruebas.ReporteAceptacion.Resumen;

final class TableroHtml {

    record TareaEquipo(String id, String sesion, String practica, String responsables, String tarea,
                       String artefacto, String evidencia, String estado) {
    }

    private TableroHtml() {
    }

    static String generar(String proyecto, Equipo equipo, List<HistoriaUsuario> historias, List<Resultado> practicas,
                          Map<String, Resumen> aceptacion, boolean hayResultados, List<TareaEquipo> tareas,
                          List<String[]> suite) {
        List<Iteracion> iteraciones = PlanEntregas.agrupar(historias);
        long terminadas = historias.stream().filter(HistoriaUsuario::estaTerminada).count();
        long aceptadas = historias.stream().filter(h -> estado(h, aceptacion) == Estado.ACEPTADA).count();
        long verificables = practicas.stream().filter(Resultado::verificable).count();
        long verificadas = practicas.stream().filter(r -> r.verificable() && r.cumple()).count();

        StringBuilder html = new StringBuilder(INICIO);
        html.append("<header><p>Grupo ").append(esc(equipo.numeroConFormato())).append(" · ")
                .append(esc(equipo.getNombre())).append("</p><h1>Tablero XP · ").append(esc(proyecto))
                .append("</h1></header><main>");
        html.append("<div class='metricas'>");
        metrica(html, "Historias terminadas", terminadas + " / " + historias.size());
        metrica(html, "Historias aceptadas", hayResultados ? aceptadas + " / " + historias.size() : "Pendiente");
        metrica(html, "Artefactos XP verificados", verificadas + " / " + verificables);
        metrica(html, "Puntos en la última iteración con avance", String.valueOf(Velocidad.climaDeAyer(iteraciones)));
        html.append("</div><nav aria-label='Secciones'><a href='#equipo'>Equipo</a><a href='#iteraciones'>Iteraciones</a>"
                + "<a href='#division'>División por fase</a><a href='#historias'>Historias</a>"
                + "<a href='#practicas'>Prácticas</a><a href='#pruebas'>Pruebas ejecutadas</a></nav>");

        aporteDelEquipo(html, equipo, historias);
        html.append("<section id='iteraciones'><h2>Iteraciones</h2><p>Velocidad media: ")
                .append(String.format(Locale.forLanguageTag("es"), "%.1f", Velocidad.promedio(iteraciones)))
                .append(" puntos</p><div class='iteraciones'>");
        for (Iteracion i : iteraciones) {
            html.append("<article><h3>Iteración ").append(i.getNumero()).append("</h3><progress aria-label='Avance de iteración ")
                    .append(i.getNumero()).append("' max='100' value='").append(i.avance()).append("'></progress><p>")
                    .append(i.puntosCompletados()).append(" / ").append(i.puntosPlanificados())
                    .append(" puntos · ").append(i.avance()).append(" %</p></article>");
        }
        if (iteraciones.isEmpty()) {
            html.append("<p>El backlog todavía no tiene historias asignadas a iteraciones.</p>");
        }
        html.append("</div></section>");
        divisionPorFase(html, tareas);
        html.append("<section id='historias'><h2>Historias</h2>");
        if (!hayResultados) {
            html.append("<p>Ejecute las pruebas con <code>xp probar</code> y vuelva a generar el tablero para ver la aceptación.</p>");
        }
        tabla(html, "ID", "Historia", "Responsable", "Iteración", "Puntos", "Estado", "Aceptación");
        for (HistoriaUsuario historia : historias) {
            fila(html, historia.getId(), historia.getTitulo(), String.join(", ", historia.getResponsables()),
                    historia.getIteracion() == 0 ? "Sin asignar" : String.valueOf(historia.getIteracion()),
                    String.valueOf(historia.getPuntos()), etiqueta(historia.getEstado()),
                    hayResultados ? etiqueta(estado(historia, aceptacion)) : "Pendiente de ejecutar");
        }
        html.append("</tbody></table></div></section><section id='practicas'><h2>Prácticas XP</h2>");
        tabla(html, "Fase", "Práctica", "Rol responsable", "Artefacto");
        for (Resultado resultado : practicas) {
            Fase fase = resultado.practica().getFase();
            String estado = resultado.verificable() ? (resultado.cumple() ? "Disponible" : "Falta") : "Sin artefacto";
            fila(html, fase.titulo(), resultado.practica().getNombre(),
                    resultado.practica().getResponsable().getEtiqueta(), estado);
        }
        html.append("</tbody></table></div></section>");
        pruebas(html, suite);
        return html.append("</main></body></html>").toString();
    }

    private static void divisionPorFase(StringBuilder html, List<TareaEquipo> tareas) {
        html.append("<section id='division'><h2>División por fase</h2>");
        mostrarFase(html, tareas, "PREP", "Preparación");
        mostrarFase(html, tareas, "S1", "S1 · Planificación");
        mostrarFase(html, tareas, "S2", "S2 · Diseño");
        mostrarFase(html, tareas, "S3", "S3 · Desarrollo");
        mostrarFase(html, tareas, "S4", "S4 · Pruebas y retrospectiva");
        if (tareas.isEmpty()) {
            html.append("<p>Agregue las asignaciones al archivo <code>xp/tareas.csv</code>.</p>");
        }
        html.append("</section>");
    }

    private static void mostrarFase(StringBuilder html, List<TareaEquipo> tareas, String clave, String nombre) {
        List<TareaEquipo> fase = tareas.stream().filter(t -> clave.equals(t.sesion())).toList();
        if (fase.isEmpty()) {
            return;
        }
        html.append("<details><summary>").append(esc(nombre)).append(" · ").append(fase.size())
                .append(" tareas</summary>");
        tabla(html, "ID", "Tarea", "Responsable", "Práctica XP", "Artefacto", "Evidencia", "Estado");
        for (TareaEquipo t : fase) {
            fila(html, t.id(), t.tarea(), t.responsables(), t.practica(), t.artefacto(), t.evidencia(), t.estado());
        }
        html.append("</tbody></table></div></details>");
    }

    private static void pruebas(StringBuilder html, List<String[]> suite) {
        html.append("<section id='pruebas'><h2>Pruebas ejecutadas</h2>");
        if (suite.isEmpty()) {
            html.append("<p>Ejecute <code>xp probar</code> para cargar la lista de pruebas.</p>");
        } else {
            long pasadas = suite.stream().filter(c -> c.length == 6 && "PASO".equals(c[3])).count();
            html.append("<p>").append(suite.size()).append(" pruebas · ").append(pasadas).append(" pasaron</p>");
            tabla(html, "Clase", "Prueba", "Historias", "Resultado", "ms", "Detalle");
            suite.forEach(resultados -> fila(html, resultados));
            html.append("</tbody></table></div>");
        }
        html.append("</section>");
    }

    private static void aporteDelEquipo(StringBuilder html, Equipo equipo, List<HistoriaUsuario> historias) {
        html.append("<section id='equipo'><h2>Equipo</h2>");
        tabla(html, "Integrante", "Rol XP", "Historias terminadas", "Puntos terminados");
        for (var integrante : equipo.getIntegrantes()) {
            List<HistoriaUsuario> asignadas = historias.stream()
                    .filter(h -> h.getResponsables().contains(integrante.getNombre())).toList();
            List<HistoriaUsuario> terminadas = asignadas.stream().filter(HistoriaUsuario::estaTerminada).toList();
            fila(html, integrante.getNombre(), integrante.getRol().getEtiqueta(), terminadas.size() + " / " + asignadas.size(),
                    terminadas.stream().mapToInt(HistoriaUsuario::getPuntos).sum() + " / "
                            + asignadas.stream().mapToInt(HistoriaUsuario::getPuntos).sum());
        }
        html.append("</tbody></table></div></section>");
    }

    private static Estado estado(HistoriaUsuario h, Map<String, Resumen> aceptacion) {
        Resumen resumen = aceptacion.get(h.getId());
        return resumen == null ? Estado.SIN_PRUEBAS : resumen.estado();
    }

    private static String etiqueta(Enum<?> estado) {
        String texto = estado.name().toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(texto.charAt(0)) + texto.substring(1);
    }

    private static void metrica(StringBuilder html, String titulo, String valor) {
        html.append("<article><strong>").append(esc(valor)).append("</strong><p>").append(esc(titulo)).append("</p></article>");
    }

    private static void tabla(StringBuilder html, String... columnas) {
        html.append("<div class='tabla'><table><thead><tr>");
        for (String columna : columnas) {
            html.append("<th scope='col'>").append(esc(columna)).append("</th>");
        }
        html.append("</tr></thead><tbody>");
    }

    private static void fila(StringBuilder html, String... valores) {
        html.append("<tr>");
        for (String valor : valores) {
            html.append("<td>").append(esc(valor)).append("</td>");
        }
        html.append("</tr>");
    }

    private static String esc(String valor) {
        return valor.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    private static final String INICIO = """
            <!doctype html>
            <html lang="es"><head><meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Tablero XP</title>
            <style>
              * { box-sizing: border-box; }
              body { margin: 0; background: #f4f6f2; color: #17251d; font: 1rem/1.5 system-ui, sans-serif; }
              header { background: #142c23; color: #f7fbf8; padding: 2.5rem max(1rem, calc((100% - 76rem) / 2)); }
              header p { color: #b8d6c7; }
              h1 { margin: 0; font-size: clamp(1.8rem, 5vw, 3rem); }
              h2 { margin: 0 0 0.9rem; font-size: 1.45rem; }
              h3, p { margin: 0 0 0.5rem; }
              main { max-width: 78rem; margin: auto; padding: 1.5rem 1rem 3rem; }
              .metricas, .iteraciones { display: grid; grid-template-columns: repeat(auto-fit, minmax(12rem, 1fr)); gap: 0.75rem; }
              article, section { background: #fff; border: 1px solid #dce6dd; border-radius: 0.7rem; padding: 1.1rem; }
              article strong { display: block; font-size: 1.55rem; color: #146044; }
              article p { margin: 0.35rem 0 0; }
              nav { display: flex; flex-wrap: wrap; gap: 0.75rem 1.25rem; margin: 1.5rem 0 1rem; }
              a { color: #14593e; font-weight: 650; text-underline-offset: 0.2em; }
              section { margin-bottom: 1rem; }
              .tabla { overflow-x: auto; }
              details { padding: 0.55rem 0; }
              summary { cursor: pointer; font-weight: 650; }
              table { width: 100%; border-collapse: collapse; text-align: left; }
              th, td { padding: 0.65rem 0.7rem; border-bottom: 1px solid #e3eae5; }
              th { background: #eaf2ed; }
              progress { width: 100%; accent-color: #197453; }
            </style></head><body>
            """;
}
