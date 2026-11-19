package ec.edu.epn.antdron.ui;

import java.util.List;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/** Utilidades de las pantallas: tarjetas, títulos, columnas de tablas, insignias, avatares y mensajes. */
final class Ui {

    private static final List<String> ROLES = List.of("ENTRENADOR", "CLIENTE", "ENCARGADO_PRUEBAS",
            "ENCARGADO_SEGUIMIENTO", "CONSULTOR", "GESTOR");

    private Ui() {
    }

    /** Etiqueta redondeada; {@code color}: gris, indigo, verde, ambar o roja. */
    static Label insignia(String texto, String color) {
        Label l = new Label(texto);
        l.getStyleClass().addAll("insignia", "insignia-" + color);
        l.setMinWidth(Label.USE_PREF_SIZE);
        return l;
    }

    /** Círculo con las iniciales del nombre, del color del rol XP. */
    static StackPane avatar(String nombre, String rol, double tamano) {
        Label l = new Label(iniciales(nombre));
        l.setStyle("-fx-font-size: " + Math.round(tamano * 0.38) + "px;");
        StackPane circulo = new StackPane(l);
        circulo.getStyleClass().addAll("avatar", "avatar-" + (ROLES.contains(rol) ? rol : "OTRO"));
        circulo.setMinSize(tamano, tamano);
        circulo.setMaxSize(tamano, tamano);
        return circulo;
    }

    /** Primeras letras de las dos primeras palabras: «Kened Lugmaña» → «KL», «Profesor (patmic)» → «P». */
    static String iniciales(String nombre) {
        List<String> palabras = java.util.Arrays.stream(nombre.trim().split("\\s+"))
                .filter(p -> !p.isEmpty() && Character.isLetter(p.charAt(0))).toList();
        return palabras.isEmpty() ? "?"
                : (palabras.get(0).substring(0, 1) + (palabras.size() > 1 ? palabras.get(1).substring(0, 1) : ""))
                        .toUpperCase();
    }

    /** «ENCARGADO_PRUEBAS» → «Encargado de pruebas (Tester)». */
    static String rolLegible(String rol) {
        return switch (rol == null ? "" : rol) {
            case "ENTRENADOR" -> "Entrenador (Coach)";
            case "CLIENTE" -> "Cliente";
            case "ENCARGADO_PRUEBAS" -> "Encargado de pruebas (Tester)";
            case "ENCARGADO_SEGUIMIENTO" -> "Encargado de seguimiento (Tracker)";
            case "CONSULTOR" -> "Consultor";
            case "GESTOR" -> "Gestor";
            case "" -> "Sin rol";
            default -> rol;
        };
    }

    static String estilos() {
        return Ui.class.getResource("/ec/edu/epn/antdron/ui/antdron.css").toExternalForm();
    }

    static Label titulo(String texto) {
        Label l = new Label(texto);
        l.getStyleClass().add("titulo");
        return l;
    }

    static Label subtitulo(String texto) {
        Label l = new Label(texto);
        l.getStyleClass().add("subtitulo");
        return l;
    }

    static Label texto(String texto) {
        Label l = new Label(texto);
        l.setWrapText(true);
        l.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        return l;
    }

    static VBox tarjeta(Node... hijos) {
        VBox v = new VBox(10, hijos);
        v.getStyleClass().add("tarjeta");
        v.setPadding(new Insets(16));
        return v;
    }

    static HBox fila(List<? extends Node> hijos) {
        HBox h = new HBox(10);
        h.getChildren().addAll(hijos);
        h.setAlignment(Pos.CENTER_LEFT);
        return h;
    }

    /** Etiqueta de aviso (amarilla) o error (roja); vacía la oculta. */
    static Label aviso(String texto, boolean error) {
        Label l = new Label(texto);
        l.setWrapText(true);
        l.setMaxWidth(Double.MAX_VALUE);
        l.getStyleClass().add(error ? "aviso-error" : "aviso");
        return l;
    }

    static void mensaje(Label etiqueta, String texto, boolean error) {
        etiqueta.setText(texto);
        etiqueta.getStyleClass().removeAll("aviso", "aviso-error", "aviso-ok");
        etiqueta.getStyleClass().add(error ? "aviso-error" : "aviso-ok");
        etiqueta.setVisible(texto != null && !texto.isEmpty());
        etiqueta.setManaged(etiqueta.isVisible());
    }

    static TextArea monoespaciado(String contenido) {
        TextArea t = new TextArea(contenido);
        t.setEditable(false);
        t.getStyleClass().add("mono");
        t.setWrapText(false);
        VBox.setVgrow(t, Priority.ALWAYS);
        return t;
    }

    static <T> TableColumn<T, String> columna(String titulo, Function<T, String> valor, double ancho) {
        TableColumn<T, String> c = new TableColumn<>(titulo);
        c.setCellValueFactory(d -> new ReadOnlyStringWrapper(valor.apply(d.getValue())));
        c.setPrefWidth(ancho);
        c.setSortable(false);
        return c;
    }

    static <T> TableView<T> tabla(String sinDatos) {
        TableView<T> t = new TableView<>();
        t.setPlaceholder(new Label(sinDatos));
        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox.setVgrow(t, Priority.ALWAYS);
        return t;
    }

    /** Muestra los elementos del combo con un texto legible en lugar de su nombre interno. */
    static <T> void mostrarCon(ComboBox<T> combo, Function<T, String> texto) {
        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(T valor) {
                return valor == null ? "" : texto.apply(valor);
            }

            @Override
            public T fromString(String s) {
                return null;
            }
        });
    }

    static void error(String titulo, String detalle) {
        Alert a = new Alert(Alert.AlertType.ERROR, detalle);
        a.setHeaderText(titulo);
        a.setTitle(AntdronFx.TITULO);
        a.show();
    }
}
