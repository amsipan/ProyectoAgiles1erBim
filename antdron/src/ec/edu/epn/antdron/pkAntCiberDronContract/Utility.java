package ec.edu.epn.antdron.pkAntCiberDronContract;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Utilidades compartidas por los módulos del AntCiberDron (diagrama de clases del enunciado).
 * Reúne el manejo de líneas CSV separadas por ';' y la normalización de textos, que antes estaba
 * repetido en SistemaRuso, LectorConfiguracion, TablaCsv y FabricaAlimentos.
 */
public final class Utility {

    /** Separador de los archivos CSV del proyecto. */
    public static final String SEPARADOR = ";";

    private Utility() {
    }

    /** Quita la marca BOM y los espacios de los extremos; {@code null} se trata como línea vacía. */
    public static String limpiarLinea(String linea) {
        return linea == null ? "" : linea.replace("﻿", "").strip();
    }

    /** Parte una línea CSV en sus campos, conservando los campos vacíos del final. */
    public static String[] campos(String linea) {
        return linea.split(SEPARADOR, -1);
    }

    /** True si la línea es un comentario ({@code #...}). */
    public static boolean esComentario(String linea) {
        return linea.startsWith("#");
    }

    /** Valor seguro para escribir en un campo CSV: sin separadores ni saltos de línea. */
    public static String campoSeguro(String valor) {
        return valor == null ? "" : valor.replace(SEPARADOR, ",").replace("\n", " ").replace("\r", " ");
    }

    /** Texto sin tildes, sin espacios en los extremos y en minúsculas: "Carnívoro " → "carnivoro". */
    public static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }
}
