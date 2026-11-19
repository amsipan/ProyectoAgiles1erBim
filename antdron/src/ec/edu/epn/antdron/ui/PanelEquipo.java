package ec.edu.epn.antdron.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import ec.edu.epn.antdron.MotorAntdron;
import ec.edu.epn.antdron.config.Equipo;
import ec.edu.epn.antdron.config.Integrante;

/** "[+] TEAM [n]: nombre" con los alumnos (cédula, nombre) y la metáfora del sistema. */
final class PanelEquipo {

    private final VBox raiz;

    private record Fila(int numero, Integrante integrante) {
    }

    PanelEquipo(MotorAntdron motor) {
        Equipo equipo = motor.getEquipo();
        TableView<Fila> tabla = Ui.tabla("Sin integrantes");
        tabla.getColumns().add(Ui.columna("Alumno", f -> "Alumno " + f.numero(), 90));
        tabla.getColumns().add(Ui.columna("Cédula", f -> f.integrante().cedulaVisible(), 170));
        tabla.getColumns().add(Ui.columna("Nombre", f -> f.integrante().nombre(), 260));
        tabla.getColumns().add(Ui.columna("Rol XP", f -> Ui.rolLegible(f.integrante().rol()), 240));
        tabla.getColumns().add(Ui.columna("Usuario", f -> f.integrante().usuario(), 140));
        List<Fila> filas = new ArrayList<>();
        for (int i = 0; i < equipo.integrantes().size(); i++) {
            filas.add(new Fila(i + 1, equipo.integrantes().get(i)));
        }
        tabla.setItems(FXCollections.observableArrayList(filas));
        tabla.setId("tabla-equipo");
        // Alto justo para las filas: sin renglones vacíos debajo.
        tabla.setFixedCellSize(34);
        tabla.setPrefHeight(34 * (filas.size() + 1) + 8);
        tabla.setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(tabla, Priority.NEVER);

        Label etiquetaMetafora = new Label("Metáfora del sistema");
        etiquetaMetafora.getStyleClass().add("seccion");
        VBox metafora = Ui.tarjeta(etiquetaMetafora, Ui.texto(equipo.metafora()));
        Label archivo = Ui.texto("Las cédulas, nombres, usuarios y claves se editan en config/integrantes.csv; "
                + "el número y nombre del grupo en config/equipo.properties. Luego pulse «Recargar configuración».");
        archivo.getStyleClass().add("tenue");
        Label cedulas = Ui.insignia("Cédulas registradas: " + equipo.cedulas().size() + " de "
                + equipo.integrantes().size(), equipo.cedulasPendientes() == 0 ? "verde" : "ambar");

        raiz = new VBox(14, Ui.titulo("[+] TEAM [" + equipo.grupo() + "]: " + equipo.nombre()),
                cedulas, metafora, tabla, archivo);
        raiz.setPadding(new Insets(22, 26, 22, 26));
    }

    Parent getRaiz() {
        return raiz;
    }
}
