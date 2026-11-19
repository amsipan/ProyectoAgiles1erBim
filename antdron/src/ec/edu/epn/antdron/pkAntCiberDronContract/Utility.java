package ec.edu.epn.antdron.pkAntCiberDronContract;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.Locale;

/**
 * Utilidades compartidas por los módulos del AntCiberDron (diagrama de clases del enunciado).
 * Reúne el manejo de líneas CSV separadas por ';' y la normalización de textos, que antes estaba
 * repetido en SistemaRuso, LectorConfiguracion y FabricaAlimentos.
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

    /** Reconoce líneas vacías, comentarios y cabeceras de CSV. */
    public static boolean esLineaIgnorable(String linea, String cabecera) {
        String limpia = limpiarLinea(linea);
        return limpia.isEmpty() || esComentario(limpia)
                || (cabecera != null && limpia.regionMatches(true, 0, cabecera, 0, cabecera.length()));
    }

    /** Lee UTF-8 con o sin BOM y admite CSV anteriores guardados en Windows-1252. */
    public static String leerTexto(Path archivo) throws IOException {
        byte[] bytes = Files.readAllBytes(archivo);
        String texto;
        try {
            texto = StandardCharsets.UTF_8.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException e) {
            texto = new String(bytes, Charset.forName("windows-1252"));
        }
        return texto.startsWith("\uFEFF") ? texto.substring(1) : texto;
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
