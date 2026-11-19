package ec.edu.epn.antdron.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import ec.edu.epn.antdron.MotorAntdron;
import ec.edu.epn.antdron.auth.Autenticador;

/**
 * Pantalla inicial: autenticación de los integrantes y del profesor (máximo 3 intentos) y acceso
 * aparte para los usuarios de la KGD al hormiguero virtual.
 */
final class VistaIngreso {

    private final VBox raiz;

    VistaIngreso(AntdronFx app, MotorAntdron motor, Autenticador autenticador) {

        TextField usuario = new TextField();
        usuario.setPromptText("usuario");
        usuario.setId("usuario");
        PasswordField clave = new PasswordField();
        clave.setPromptText("contraseña");
        clave.setId("clave");
        Button ingresar = new Button("Ingresar");
        ingresar.getStyleClass().add("primario");
        ingresar.setDefaultButton(true);
        ingresar.setId("ingresar");
        Label intentos = new Label("Intentos disponibles: " + autenticador.intentosRestantes());
        intentos.getStyleClass().add("tenue");
        Label mensaje = Ui.aviso("", true);
        mensaje.setVisible(false);
        mensaje.setManaged(false);

        Runnable bloquear = () -> {
            Ui.mensaje(mensaje, "Acceso denegado: se superó el máximo de " + Autenticador.MAX_INTENTOS
                    + " intentos. Cierre y vuelva a abrir la aplicación.", true);
            intentos.setText("Intentos disponibles: 0");
            usuario.setDisable(true);
            clave.setDisable(true);
            ingresar.setDisable(true);
        };
        if (autenticador.estaBloqueado()) {
            bloquear.run();
        }

        ingresar.setOnAction(e -> {
            switch (autenticador.intentar(usuario.getText(), clave.getText())) {
                case ACEPTADO -> app.mostrarPrincipal(usuario.getText().trim(),
                        autenticador.nombreDe(usuario.getText().trim()));
                case RECHAZADO -> {
                    Ui.mensaje(mensaje, "Usuario o contraseña incorrectos.", true);
                    intentos.setText("Intentos disponibles: " + autenticador.intentosRestantes());
                    clave.clear();
                    clave.requestFocus();
                }
                case BLOQUEADO -> bloquear.run();
                default -> throw new IllegalStateException();
            }
        });

        ingresar.setMaxWidth(Double.MAX_VALUE);
        usuario.setPrefHeight(36);
        clave.setPrefHeight(36);
        Label etiquetaUsuario = new Label("Usuario");
        etiquetaUsuario.getStyleClass().add("seccion");
        Label etiquetaClave = new Label("Contraseña");
        etiquetaClave.getStyleClass().add("seccion");
        Label ayudaEquipo = Ui.texto("Integrantes del Grupo " + motor.getEquipo().grupo() + " y profesor. Máximo "
                + Autenticador.MAX_INTENTOS + " intentos.");
        ayudaEquipo.getStyleClass().add("tenue");
        VBox equipo = Ui.tarjeta(
                Ui.subtitulo("Ingresar"), ayudaEquipo,
                etiquetaUsuario, usuario, etiquetaClave, clave, ingresar, intentos, mensaje);
        equipo.setPadding(new Insets(24));
        equipo.setSpacing(10);
        equipo.setPrefWidth(380);

        Button kgd = new Button("Entrar al hormiguero virtual");
        kgd.setId("kgd");
        kgd.setMaxWidth(Double.MAX_VALUE);
        kgd.setOnAction(e -> app.mostrarHormigueroKgd());
        Label pasos = Ui.texto("1. Cree su cuenta y su hormiguero.\n2. Agregue hormigas: todas nacen como HLarva.\n"
                + "3. Aliméntelas y vea cómo se transforman.\nTodo queda guardado en la base de datos SQLite.");
        pasos.getStyleClass().add("tenue");
        VBox usuariosKgd = Ui.tarjeta(
                Ui.subtitulo("Usuarios de la KGD"),
                Ui.texto("¿No es del equipo? Tenga su propio hormiguero virtual."),
                pasos, kgd);
        usuariosKgd.setPadding(new Insets(24));
        usuariosKgd.setSpacing(12);
        usuariosKgd.setPrefWidth(340);

        HBox tarjetas = new HBox(24, equipo, usuariosKgd);
        tarjetas.setAlignment(Pos.TOP_CENTER);

        Label letra = new Label("A");
        StackPane icono = new StackPane(letra);
        icono.getStyleClass().add("ingreso-icono");
        Label titulo = new Label("AntCiberDron");
        titulo.getStyleClass().add("ingreso-marca");
        Label sub = new Label("ANTDRON · KGD  ·  TEAM [" + motor.getEquipo().grupo() + "]: " + motor.getEquipo().nombre());
        sub.getStyleClass().add("ingreso-sub");

        raiz = new VBox(14, icono, titulo, sub, espacio(10), tarjetas);
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.setPadding(new Insets(56, 24, 24, 24));
        raiz.getStyleClass().add("fondo");
        for (String a : motor.getAvisos()) {
            Label l = Ui.aviso(a, false);
            l.setMaxWidth(744);
            raiz.getChildren().add(l);
        }
        VBox.setVgrow(tarjetas, Priority.NEVER);
        usuario.requestFocus();
    }

    private static Region espacio(double alto) {
        Region r = new Region();
        r.setMinHeight(alto);
        return r;
    }

    Parent getRaiz() {
        return raiz;
    }
}
