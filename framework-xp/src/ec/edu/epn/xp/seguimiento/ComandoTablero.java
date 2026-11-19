package ec.edu.epn.xp.seguimiento;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ec.edu.epn.xp.ComandoXP;
import ec.edu.epn.xp.ContextoXP;
import ec.edu.epn.xp.desarrollo.LectorEquipo;
import ec.edu.epn.xp.desarrollo.Equipo;
import ec.edu.epn.xp.desarrollo.Integrante;
import ec.edu.epn.xp.directrices.ComandoVerificar;
import ec.edu.epn.xp.directrices.Fase;
import ec.edu.epn.xp.planificacion.LectorBacklog;
import ec.edu.epn.xp.pruebas.ComandoProbar;
import ec.edu.epn.xp.pruebas.ReporteAceptacion;

/** Genera un tablero HTML autocontenido a partir de los artefactos del proyecto. */
public final class ComandoTablero implements ComandoXP {

    @Override
    public String nombre() {
        return "tablero";
    }

    @Override
    public String descripcion() {
        return "Genera el tablero de equipo, iteraciones e historias [--abrir] [--salida ARCHIVO]";
    }

    @Override
    public int ejecutar(List<String> argumentos, ContextoXP contexto) throws IOException {
        List<String> resto = new ArrayList<>(argumentos);
        boolean abrir = resto.remove("--abrir");
        if (!resto.isEmpty() && !(resto.size() == 2 && resto.getFirst().equals("--salida"))) {
            throw new IllegalArgumentException("Uso: xp tablero [--abrir] [--salida ARCHIVO]");
        }
        Path destino = resto.isEmpty() ? contexto.ruta("tablero", "build/xp/tablero.html")
                : contexto.getRaiz().resolve(resto.getLast()).normalize();
        var equipo = new LectorEquipo().leer(contexto.ruta("equipo", "config"));
        var historias = new LectorBacklog().leer(contexto.ruta("backlog", "xp/historias.csv"));
        var practicas = ComandoVerificar.verificar(contexto, List.of(Fase.values()));
        Path resultados = contexto.ruta("resultados", "build/pruebas/resultados.csv");
        boolean hayResultados = Files.isRegularFile(resultados);
        Map<String, ReporteAceptacion.Resumen> aceptacion = hayResultados
                ? ReporteAceptacion.resumir(Files.readAllLines(resultados, StandardCharsets.UTF_8)) : Map.of();
        String html = TableroHtml.generar(contexto.propiedad("proyecto.nombre", equipo.getNombre()),
                equipo, historias, practicas, aceptacion, hayResultados,
                leerTareas(contexto, equipo), leerSuite(contexto));
        Files.createDirectories(destino.getParent());
        Files.writeString(destino, html, StandardCharsets.UTF_8);
        contexto.getSalida().println("Tablero XP generado en " + destino);
        if (abrir) {
            abrirEnNavegador(destino, contexto);
        }
        return 0;
    }

    private static List<TableroHtml.TareaEquipo> leerTareas(ContextoXP contexto, Equipo equipo) throws IOException {
        Path archivo = contexto.ruta("tareas", "xp/tareas.csv");
        if (!Files.isRegularFile(archivo)) {
            return List.of();
        }
        Map<String, String> integrantes = equipo.getIntegrantes().stream()
                .collect(Collectors.toMap(Integrante::getUsuario, Integrante::getNombre, (primero, otro) -> primero));
        List<TableroHtml.TareaEquipo> tareas = new ArrayList<>();
        for (String fila : Files.readAllLines(archivo)) {
            String linea = fila.replace("\uFEFF", "").strip();
            if (linea.isBlank() || linea.startsWith("#") || linea.regionMatches(true, 0, "id;", 0, 3)) {
                continue;
            }
            String[] c = linea.split(";", -1);
            if (c.length != 8) {
                throw new IOException("Fila de tareas inválida en " + archivo + ": " + c[0]);
            }
            String responsables = Arrays.stream(c[3].split(",")).map(String::strip)
                    .map(u -> u.equals("todos") ? "Todo el equipo" : integrantes.getOrDefault(u, u))
                    .collect(Collectors.joining(", "));
            boolean tieneCaptura = c[6].startsWith("docs/evidencias/")
                    && Files.isRegularFile(contexto.getRaiz().resolve(c[6]));
            String estado = "Por hacer";
            if ("HECHA".equalsIgnoreCase(c[7]) || tieneCaptura) {
                estado = "Hecha";
            } else if ("EN_CURSO".equalsIgnoreCase(c[7])
                    || (!c[5].isBlank() && Files.exists(contexto.getRaiz().resolve(c[5])))) {
                estado = "En curso";
            }
            tareas.add(new TableroHtml.TareaEquipo(c[0], c[1], c[2], responsables, c[4], c[5], c[6], estado));
        }
        return tareas;
    }

    private static List<String[]> leerSuite(ContextoXP contexto) throws IOException {
        Path archivo = contexto.getRaiz().resolve(ComandoProbar.DETALLE);
        if (!Files.isRegularFile(archivo)) {
            return List.of();
        }
        try (var lineas = Files.lines(archivo)) {
            return lineas.skip(1).map(linea -> linea.split("\t", -1)).toList();
        }
    }

    private static void abrirEnNavegador(Path destino, ContextoXP contexto) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(destino.toFile());
                return;
            }
        } catch (IOException | UnsupportedOperationException e) {
            contexto.getErrores().println("Aviso: " + e.getMessage());
        }
        contexto.getSalida().println("Abra el archivo HTML generado en el navegador.");
    }
}
