package ec.edu.epn.xp.seguimiento;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ec.edu.epn.xp.ComandoXP;
import ec.edu.epn.xp.ContextoXP;
import ec.edu.epn.xp.desarrollo.LectorEquipo;
import ec.edu.epn.xp.directrices.ComandoVerificar;
import ec.edu.epn.xp.directrices.Fase;
import ec.edu.epn.xp.planificacion.LectorBacklog;
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
                equipo, historias, practicas, aceptacion, hayResultados);
        Files.createDirectories(destino.getParent());
        Files.writeString(destino, html, StandardCharsets.UTF_8);
        contexto.getSalida().println("Tablero XP generado en " + destino);
        if (abrir) {
            abrirEnNavegador(destino, contexto);
        }
        return 0;
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
