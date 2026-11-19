package ec.edu.epn.antdron.ui;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import ec.edu.epn.antdron.MotorAntdron;
import ec.edu.epn.antdron.alimento.Alimento;
import ec.edu.epn.antdron.alimento.FabricaAlimentos;
import ec.edu.epn.antdron.hormiga.TipoHormiga;
import ec.edu.epn.antdron.persistencia.HormigaRegistro;
import ec.edu.epn.antdron.persistencia.HormigueroServicio;

/**
 * Pantallas del hormiguero virtual de los usuarios de la KGD: registro, ingreso, creación de hormigas
 * (nacen como HLarva) y alimentación. Se usa como ventana propia o como pestaña de la ventana principal.
 */
final class VistaKgd {

    private final StackPane raiz = new StackPane();
    private final MotorAntdron motor;
    private final Runnable volver;
    private HormigueroServicio servicio;

    /** @param volver acción del botón «Volver» (null si la vista está dentro de una pestaña) */
    VistaKgd(MotorAntdron motor, Runnable volver) {
        this.motor = motor;
        this.volver = volver;
        raiz.getStyleClass().add("fondo");
        raiz.setPadding(new Insets(24));
        try {
            servicio = motor.hormiguero();
            mostrarIngreso();
        } catch (RuntimeException e) {
            VBox caja = new VBox(12, Ui.titulo("Hormiguero virtual"), Ui.aviso(e.getMessage(), true));
            agregarVolver(caja);
            raiz.getChildren().setAll(caja);
        }
    }

    private void mostrarIngreso() {
        TextField usuario = new TextField();
        usuario.setPromptText("usuario (3 a 20 letras, números o _)");
        usuario.setId("kgd-usuario");
        PasswordField clave = new PasswordField();
        clave.setPromptText("contraseña (mínimo 4 caracteres)");
        clave.setId("kgd-clave");
        Label mensaje = Ui.aviso("", true);
        mensaje.setVisible(false);
        mensaje.setManaged(false);

        Button ingresar = new Button("Ingresar");
        ingresar.getStyleClass().add("primario");
        ingresar.setId("kgd-ingresar");
        ingresar.setOnAction(e -> ejecutar(mensaje, () -> servicio.autenticar(usuario.getText(), clave.getText())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos.")),
                this::mostrarHormiguero));
        Button registrar = new Button("Crear cuenta y hormiguero");
        registrar.setId("kgd-registrar");
        registrar.setOnAction(e -> ejecutar(mensaje, () -> servicio.registrar(usuario.getText().trim(), clave.getText()),
                this::mostrarHormiguero));
        clave.setOnAction(e -> ingresar.fire());

        VBox tarjeta = Ui.tarjeta(Ui.subtitulo("Usuarios de la KGD"), new Label("Usuario"), usuario,
                new Label("Contraseña"), clave, Ui.fila(List.of(ingresar, registrar)), mensaje);
        tarjeta.setMaxWidth(460);
        VBox caja = new VBox(16, Ui.titulo("Hormiguero virtual"), tarjeta);
        caja.setAlignment(Pos.TOP_CENTER);
        agregarVolver(caja);
        raiz.getChildren().setAll(caja);
    }

    private void mostrarHormiguero(int usuarioId) {
        TipoHormiga tipo = servicio.getTipoDelGrupo();
        Label mensaje = Ui.aviso("", false);
        mensaje.setVisible(false);
        mensaje.setManaged(false);
        mensaje.setId("kgd-mensaje");

        TableView<HormigaRegistro> tabla = Ui.tabla("Su hormiguero está vacío: cree su primera hormiga");
        tabla.getColumns().add(Ui.columna("Id", h -> "#" + h.id(), 60));
        tabla.getColumns().add(Ui.columna("Tipo", HormigaRegistro::tipo, 140));
        tabla.getColumns().add(Ui.columna("Sexo", HormigaRegistro::sexo, 100));
        tabla.getColumns().add(Ui.columna("Estado", HormigaRegistro::estado, 100));
        tabla.getColumns().add(Ui.columna("Creada", HormigaRegistro::creadaEn, 160));
        tabla.setId("kgd-tabla");
        Runnable refrescar = () -> tabla.setItems(FXCollections.observableArrayList(servicio.listar(usuarioId)));
        refrescar.run();

        ComboBox<String> sexo = new ComboBox<>(FXCollections.observableArrayList("HEMBRA", "MACHO"));
        sexo.getSelectionModel().selectFirst();
        Button crear = new Button("Crear HLarva");
        crear.getStyleClass().add("primario");
        crear.setId("kgd-crear");
        crear.setOnAction(e -> ejecutar(mensaje, () -> servicio.crearHormiga(usuarioId, sexo.getValue()), id -> {
            refrescar.run();
            tabla.getSelectionModel().selectLast();
            Ui.mensaje(mensaje, "Nació la HLarva #" + id + ". Aliméntela con Néctar para que viva o con "
                    + tipo.alimento() + " para que se transforme en " + tipo.getNombre() + ".", false);
        }));

        ComboBox<String> alimento = new ComboBox<>(FXCollections.observableArrayList(
                FabricaAlimentos.todos().stream().map(Alimento::getNombre).toList()));
        alimento.getSelectionModel().selectFirst();
        alimento.setId("kgd-alimento");
        Button alimentar = new Button("Alimentar hormiga seleccionada");
        alimentar.setId("kgd-alimentar");
        alimentar.setOnAction(e -> {
            HormigaRegistro h = tabla.getSelectionModel().getSelectedItem();
            if (h == null) {
                Ui.mensaje(mensaje, "Seleccione una hormiga de la tabla.", true);
                return;
            }
            ejecutar(mensaje, () -> servicio.alimentar(usuarioId, h.id(), alimento.getValue()), texto -> {
                int fila = tabla.getSelectionModel().getSelectedIndex();
                refrescar.run();
                tabla.getSelectionModel().select(fila);
                Ui.mensaje(mensaje, texto, texto.contains("murió") || texto.contains("muerta"));
            });
        });

        Button salir = new Button("Cerrar sesión KGD");
        salir.setOnAction(e -> mostrarIngreso());

        Label reglas = Ui.texto("Reglas: toda hormiga nace como HLarva y vive comiendo Néctar. Si come "
                + tipo.alimento() + " se transforma en " + tipo.getNombre() + ", que vive solo si come "
                + tipo.alimento() + ". Cualquier otro alimento la mata.");
        reglas.getStyleClass().add("tenue");

        VBox caja = new VBox(12,
                Ui.fila(List.of(Ui.titulo("Hormiguero de " + servicio.nombreUsuario(usuarioId)), salir)),
                reglas,
                Ui.fila(List.of(new Label("Sexo"), sexo, crear)),
                Ui.fila(List.of(new Label("Alimento"), alimento, alimentar)),
                mensaje, tabla);
        agregarVolver(caja);
        raiz.getChildren().setAll(caja);
    }

    private static <T> void ejecutar(Label mensaje, Supplier<T> operacion, Consumer<T> exito) {
        try {
            exito.accept(operacion.get());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            Ui.mensaje(mensaje, ex.getMessage(), true);
        }
    }

    private void agregarVolver(VBox caja) {
        if (volver != null) {
            Button b = new Button("Volver al inicio");
            b.setOnAction(e -> volver.run());
            caja.getChildren().add(b);
        }
    }

    Parent getRaiz() {
        return raiz;
    }

    MotorAntdron getMotor() {
        return motor;
    }
}
