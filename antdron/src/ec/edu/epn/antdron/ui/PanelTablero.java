package ec.edu.epn.antdron.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import ec.edu.epn.antdron.MotorAntdron;
import ec.edu.epn.antdron.config.Integrante;
import ec.edu.epn.antdron.pkAntCiberDronContract.Utility;
import ec.edu.epn.antdron.tablero.EstadoTarea;
import ec.edu.epn.antdron.tablero.TableroXp;
import ec.edu.epn.antdron.tablero.Tarea;

/**
 * Tablero de trabajo XP con un menú lateral y secciones separadas: resumen, actividades (división por fases en
 * columnas Por hacer / En curso / Hecha), historias de usuario, pruebas ejecutadas, división de trabajo por
 * integrante y prácticas XP.
 */
final class PanelTablero {

    private enum Seccion {
        RESUMEN("Resumen", "Cómo va el equipo de un vistazo."),
        ACTIVIDADES("Actividades", "Tareas de la división por fases. Una tarea pasa sola a «Hecha» cuando su captura está "
                + "en docs/evidencias/ y a «En curso» cuando su artefacto ya existe (xp/tareas.csv)."),
        HISTORIAS("Historias de usuario", "El backlog (xp/historias.csv) con el resultado de sus pruebas de aceptación."),
        PRUEBAS("Pruebas", "Cada prueba ejecutada en la última corrida de «Pruebas + reporte de aceptación»."),
        DIVISION("División de trabajo", "Qué le toca a cada integrante: sus actividades por sesión y sus historias."),
        PRACTICAS("Prácticas XP", "Las 17 prácticas XP y si ya tienen todas sus evidencias.");

        final String titulo;
        final String descripcion;

        Seccion(String titulo, String descripcion) {
            this.titulo = titulo;
            this.descripcion = descripcion;
        }
    }

    private static final String TODOS = "Todo el equipo";
    private static final String TODAS = "Todas las sesiones";
    private static final String TODAS_ITERACIONES = "Todas las iteraciones";
    private static final String TODOS_RESULTADOS = "Todos los resultados";

    private final MotorAntdron motor;
    private final VBox raiz = new VBox(12);
    private final ComboBox<String> integrante = new ComboBox<>();
    private final ComboBox<String> sesion = new ComboBox<>();
    private final HBox resumen = new HBox(12);
    private final HBox columnas = new HBox(12);
    private final TabPane detalle = new TabPane();
    private final Label error = Ui.aviso("", true);
    private TableroXp tablero;
    private Seccion actual = Seccion.RESUMEN;

    // Los filtros se conservan al cambiar de sección o al actualizar.
    private String filtroIntegrante = TODOS;
    private String filtroSesion = TODAS;
    private String filtroTexto = "";
    private String filtroIteracion = TODAS_ITERACIONES;
    private String filtroPrueba = "";
    private String filtroResultado = TODOS_RESULTADOS;

    PanelTablero(MotorAntdron motor) {
        this.motor = motor;
        raiz.setId("panel-tablero");
        raiz.getStyleClass().add("fondo");
        raiz.setLeft(menuLateral());
        raiz.setCenter(contenido);
        cargar();
    }

    // ------------------------------------------------------------------ Menú y carga

    private Node menuLateral() {
        Label titulo = new Label("Tablero XP");
        titulo.getStyleClass().add("tablero-menu-titulo");
        Label grupo = new Label("Grupo " + motor.getEquipo().grupo() + " · " + motor.getEquipo().nombre());
        grupo.getStyleClass().add("tenue");
        VBox menuBox = new VBox(4, titulo, grupo, espacio(12));
        for (Seccion s : Seccion.values()) {
            Label nombre = new Label(s.titulo);
            nombre.setMinWidth(Region.USE_PREF_SIZE);
            Label contador = new Label("");
            contador.getStyleClass().add("nav-contador");
            contador.setMinWidth(Region.USE_PREF_SIZE);
            contador.visibleProperty().bind(contador.textProperty().isNotEmpty());
            contador.managedProperty().bind(contador.visibleProperty());
            contadores.put(s, contador);
            HBox fila = new HBox(8, nombre, crecer(), contador);
            fila.setAlignment(Pos.CENTER_LEFT);
            ToggleButton b = new ToggleButton();
            b.setGraphic(fila);
            b.getStyleClass().setAll("nav");
            b.setToggleGroup(menu);
            b.setMaxWidth(Double.MAX_VALUE);
            b.setId("nav-" + s.name().toLowerCase());
            fila.prefWidthProperty().bind(b.widthProperty().subtract(26));
            b.setOnAction(e -> mostrar(s));
            botones.put(s, b);
            menuBox.getChildren().add(b);
        }
        // Siempre queda una sección elegida.
        menu.selectedToggleProperty().addListener((obs, antes, ahora) -> {
            if (ahora == null && antes != null) {
                antes.setSelected(true);
            }
        });
        Button actualizar = new Button("Actualizar datos");
        actualizar.setId("actualizar-tablero");
        actualizar.setMaxWidth(Double.MAX_VALUE);
        actualizar.setOnAction(e -> cargar());
        Label ayuda = Ui.texto("Lee xp/tareas.csv, xp/historias.csv, las capturas de docs/evidencias/ y el último "
                + "resultado de las pruebas.");
        ayuda.getStyleClass().add("tenue");
        Label ver = new Label("Ver:");
        for (Region r : List.of(ver, integrante, sesion, actualizar)) {
            r.setMinWidth(Region.USE_PREF_SIZE);
        }
        integrante.setPrefWidth(230);
        sesion.setPrefWidth(250);
        HBox filtros = new HBox(10, ver, integrante, sesion, actualizar);
        filtros.setAlignment(Pos.CENTER_LEFT);

        columnas.setMinHeight(260);
        VBox.setVgrow(columnas, Priority.ALWAYS);
        detalle.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        detalle.setPrefHeight(230);
        detalle.setMinHeight(200);
        error.setVisible(false);
        error.setManaged(false);

        raiz.getChildren().addAll(Ui.titulo("Tablero de trabajo XP · Grupo " + motor.getEquipo().grupo() + " "
                + motor.getEquipo().nombre()), filtros, error, resumen, ayuda, columnas, detalle);
        cargar();
    }

    private void cargar() {
        try {
            tablero = TableroXp.cargar(motor.getRaiz(), motor.getEquipo());
        } catch (IOException | IllegalArgumentException e) {
            tablero = null;
            resumen.getChildren().clear();
            columnas.getChildren().clear();
            detalle.getTabs().clear();
            Ui.mensaje(error, e.getMessage(), true);
            return;
        }
        error.setVisible(false);
        error.setManaged(false);
        pintarResumen();
        pintarColumnas();
        pintarDetalle();
    }

    private void pintarResumen() {
        int total = tablero.getTareas().size();
        long hechas = tablero.contar(EstadoTarea.HECHA);
        List<TableroXp.AvancePractica> practicas = tablero.porPractica();
        long defendidas = practicas.stream().filter(TableroXp.AvancePractica::defendida).count();
        List<TableroXp.AvanceIteracion> iteraciones = tablero.porIteracion();
        String velocidad = iteraciones.stream()
                .map(it -> "Iteración " + it.numero() + ": " + it.terminados() + "/" + it.planificados() + " pts")
                .collect(Collectors.joining("\n"));
        int terminados = iteraciones.stream().mapToInt(TableroXp.AvanceIteracion::terminados).sum();
        boolean hayResultados = tablero.getHistorias().stream().anyMatch(h -> !"SIN_PRUEBAS".equals(h.aceptacion()));
        int fallidas = tablero.pruebasFallidas();
        String resultados = hayResultados ? fallidas + " resultados fallidos" : "Pruebas pendientes de ejecutar";
        resumen.getChildren().setAll(
                cifra("Tareas hechas", hechas + " / " + total,
                        tablero.contar(EstadoTarea.EN_CURSO) + " en curso · " + tablero.contar(EstadoTarea.POR_HACER)
                                + " por hacer", total == 0 ? 0 : (double) hechas / total),
                cifra("Prácticas XP defendidas", defendidas + " / " + practicas.size(),
                        "Con todas sus evidencias entregadas", practicas.isEmpty() ? 0 : (double) defendidas / practicas.size()),
                cifra("Historias aceptadas", tablero.historiasAceptadas() + " / " + tablero.getHistorias().size(),
                        resultados,
                        tablero.getHistorias().isEmpty() ? 0 : (double) tablero.historiasAceptadas() / tablero.getHistorias().size()),
                cifra("Velocidad (puntos terminados)", terminados + " pts", velocidad, -1));
    }

    private Node filaPendiente(Tarea t) {
        Label texto = Ui.texto(t.descripcion());
        HBox.setHgrow(texto, Priority.ALWAYS);
        texto.setMaxWidth(Double.MAX_VALUE);
        HBox fila = new HBox(10, Ui.insignia(corta(t.sesion()), "indigo"), texto, avatares(t, 24),
                estadoInsignia(tablero.estado(t)));
        fila.setAlignment(Pos.CENTER_LEFT);
        return fila;
    }

    private VBox cifra(String titulo, String valor, String detalle, double avance, String color, Seccion destino) {
        Label t = new Label(titulo);
        t.getStyleClass().add("cifra-titulo");
        Label v = new Label(valor);
        v.getStyleClass().add("cifra");
        Label d = Ui.texto(detalle);
        d.getStyleClass().add("tenue");
        ProgressBar barra = barra(avance, color);
        VBox caja = Ui.tarjeta(t, v, d, barra);
        caja.setSpacing(6);
        caja.setPadding(new Insets(14, 16, 14, 16));
        caja.setPrefWidth(240);
        caja.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(caja, Priority.ALWAYS);
        caja.setOnMouseClicked(e -> mostrar(destino));
        caja.setStyle("-fx-cursor: hand;");
        return caja;
    }

    // ------------------------------------------------------------------ Actividades

    private Node actividades() {
        ComboBox<String> integrante = new ComboBox<>();
        integrante.getItems().add(TODOS);
        motor.getEquipo().integrantes().forEach(i -> integrante.getItems().add(i.nombre()));
        integrante.setValue(integrante.getItems().contains(filtroIntegrante) ? filtroIntegrante : TODOS);
        integrante.setId("filtro-integrante");
        ComboBox<String> sesion = new ComboBox<>();
        sesion.getItems().add(TODAS);
        Tarea.SESIONES.forEach(s -> sesion.getItems().add(Tarea.nombreSesion(s)));
        sesion.setValue(filtroSesion);
        sesion.setId("filtro-sesion");
        TextField buscar = new TextField(filtroTexto);
        buscar.setPromptText("Buscar actividad, práctica o archivo");
        buscar.setPrefWidth(260);
        Button limpiar = new Button("Quitar filtros");
        HBox filtros = new HBox(10, integrante, sesion, buscar, limpiar);
        filtros.setAlignment(Pos.CENTER_LEFT);

        HBox columnas = new HBox(14);
        VBox.setVgrow(columnas, Priority.ALWAYS);
        Runnable pintar = () -> {
            filtroIntegrante = integrante.getValue();
            filtroSesion = sesion.getValue();
            filtroTexto = buscar.getText();
            pintarColumnas(columnas);
        };
        integrante.setOnAction(e -> pintar.run());
        sesion.setOnAction(e -> pintar.run());
        buscar.textProperty().addListener((o, a, b) -> pintar.run());
        limpiar.setOnAction(e -> {
            integrante.setValue(TODOS);
            sesion.setValue(TODAS);
            buscar.clear();
            pintar.run();
        });
        pintar.run();
        return new VBox(14, filtros, columnas);
    }

    private void pintarColumnas(HBox columnas) {
        String texto = Utility.normalizar(filtroTexto);
        List<Tarea> visibles = tablero.getTareas().stream()
                .filter(t -> TODOS.equals(filtroIntegrante)
                        || tablero.responsables(t).stream().anyMatch(i -> i.nombre().equals(filtroIntegrante)))
                .filter(t -> TODAS.equals(filtroSesion) || Tarea.nombreSesion(t.sesion()).equals(filtroSesion))
                .filter(t -> texto.isEmpty() || Utility.normalizar(String.join(" ", t.id(), t.descripcion(), t.practica(),
                        t.artefacto(), t.evidencia())).contains(texto))
                .toList();
        columnas.getChildren().clear();
        for (EstadoTarea estado : EstadoTarea.values()) {
            List<Tarea> deEstado = visibles.stream().filter(t -> tablero.estado(t) == estado).toList();
            VBox tarjetas = new VBox(10);
            tarjetas.setPadding(new Insets(2, 4, 8, 2));
            deEstado.forEach(t -> tarjetas.getChildren().add(tarjetaTarea(t, estado)));
            if (deEstado.isEmpty()) {
                Label vacio = new Label("Nada por aquí");
                vacio.getStyleClass().add("tenue");
                vacio.setPadding(new Insets(8));
                tarjetas.getChildren().add(vacio);
            }
            ScrollPane scroll = new ScrollPane(tarjetas);
            scroll.setFitToWidth(true);
            VBox.setVgrow(scroll, Priority.ALWAYS);
            Label cabecera = Ui.subtitulo(estado.getEtiqueta() + " (" + deEstado.size() + ")");
            VBox columna = new VBox(8, cabecera, scroll);
            String estilo = estado.name().toLowerCase(Locale.ROOT);
            columna.getStyleClass().addAll("columna-tablero", "columna-" + estilo);
            columna.setPadding(new Insets(10));
            columna.setId("columna-" + estilo.replace('_', '-'));
            HBox.setHgrow(columna, Priority.ALWAYS);
            columna.setPrefWidth(320);
            columna.setMaxWidth(Double.MAX_VALUE);
            columnas.getChildren().add(columna);
        }
    }

    private Node tarjetaTarea(Tarea t, EstadoTarea estado) {
        HBox cabecera = new HBox(6, Ui.insignia(t.id(), "gris"), Ui.insignia(corta(t.sesion()), "indigo"));
        if (!t.practica().isEmpty()) {
            Label practica = new Label(t.practica());
            practica.getStyleClass().add("tenue");
            practica.setStyle("-fx-font-size: 11px;");
            cabecera.getChildren().add(practica);
        }
        cabecera.setAlignment(Pos.CENTER_LEFT);
        Label tarea = Ui.texto(t.descripcion());
        tarea.getStyleClass().add("tarea-texto");
        String quienes = t.responsables().contains(TableroXp.TODOS) ? "Todo el equipo"
                : tablero.responsables(t).stream().map(Integrante::nombre).collect(Collectors.joining(", "));
        Label nombres = Ui.texto(quienes);
        nombres.getStyleClass().add("tenue");
        HBox responsables = new HBox(8, avatares(t, 22), nombres);
        responsables.setAlignment(Pos.CENTER_LEFT);
        VBox caja = new VBox(7, cabecera, tarea, responsables);
        if (!t.artefacto().isEmpty()) {
            caja.getChildren().add(detalle("Artefacto: " + archivo(t.artefacto()), tablero.existe(t.artefacto())));
        }
        if (!t.evidencia().isEmpty()) {
            caja.getChildren().add(detalle("Evidencia: " + archivo(t.evidencia()), estado == EstadoTarea.HECHA));
        }
        caja.getStyleClass().addAll("tarea", "tarea-" + estado.name().toLowerCase(Locale.ROOT));
        caja.setPadding(new Insets(8, 10, 8, 10));
        caja.setMinHeight(Region.USE_PREF_SIZE);
        return caja;
    }

    private Node avatares(Tarea t, double tamano) {
        List<Integrante> quienes = tablero.responsables(t);
        HBox caja = new HBox(-6);
        quienes.stream().limit(4).forEach(i -> caja.getChildren().add(Ui.avatar(i.nombre(), i.rol(), tamano)));
        if (quienes.size() > 4) {
            caja.getChildren().add(Ui.insignia("+" + (quienes.size() - 4), "gris"));
        }
        caja.setAlignment(Pos.CENTER_LEFT);
        caja.setMinWidth(Region.USE_PREF_SIZE);
        return caja;
    }

    // ------------------------------------------------------------------ Historias de usuario

    private Node historias() {
        List<TableroXp.Historia> todas = tablero.getHistorias();
        if (todas.isEmpty()) {
            return Ui.aviso("No se encontró el backlog en xp/historias.csv.", false);
        }
        ComboBox<String> iteracion = new ComboBox<>();
        iteracion.getItems().add(TODAS_ITERACIONES);
        todas.stream().map(TableroXp.Historia::iteracion).filter(n -> n > 0).distinct().sorted()
                .forEach(n -> iteracion.getItems().add("Iteración " + n));
        iteracion.setValue(iteracion.getItems().contains(filtroIteracion) ? filtroIteracion : TODAS_ITERACIONES);
        int puntos = todas.stream().mapToInt(TableroXp.Historia::puntos).sum();
        Label totales = new Label(todas.size() + " historias · " + puntos + " puntos · " + tablero.historiasAceptadas()
                + " aceptadas");
        totales.getStyleClass().add("tenue");
        HBox filtros = new HBox(12, iteracion, totales);
        filtros.setAlignment(Pos.CENTER_LEFT);

        FlowPane tarjetas = new FlowPane(14, 14);
        Runnable pintar = () -> {
            filtroIteracion = iteracion.getValue();
            tarjetas.getChildren().clear();
            todas.stream()
                    .filter(h -> TODAS_ITERACIONES.equals(filtroIteracion) || ("Iteración " + h.iteracion()).equals(filtroIteracion))
                    .forEach(h -> tarjetas.getChildren().add(tarjetaHistoria(h)));
        };
        iteracion.setOnAction(e -> pintar.run());
        pintar.run();
        return new VBox(14, filtros, tarjetas);
    }

    private Node tarjetaHistoria(TableroXp.Historia h) {
        Label id = new Label(h.id());
        id.getStyleClass().add("historia-id");
        Label aceptacion = switch (h.aceptacion()) {
            case "ACEPTADA" -> Ui.insignia("✔ Aceptada", "verde");
            case "RECHAZADA" -> Ui.insignia("✘ Rechazada", "roja");
            default -> Ui.insignia("Sin pruebas", "gris");
        };
        HBox cabecera = new HBox(8, id, crecer(), aceptacion);
        cabecera.setAlignment(Pos.CENTER_LEFT);
        Label titulo = Ui.texto(h.titulo());
        titulo.getStyleClass().add("tarea-texto");
        titulo.setStyle("-fx-font-size: 14px;");

        HBox responsables = new HBox(8);
        responsables.setAlignment(Pos.CENTER_LEFT);
        for (String nombre : h.responsables()) {
            String rol = buscarPorNombre(nombre).map(Integrante::rol).orElse("");
            responsables.getChildren().add(Ui.avatar(nombre, rol, 24));
        }
        Label nombres = Ui.texto(h.responsables().isEmpty() ? "Sin responsable" : String.join(", ", h.responsables()));
        nombres.getStyleClass().add("tenue");
        responsables.getChildren().add(nombres);

        FlowPane datos = new FlowPane(6, 6, Ui.insignia(h.iteracion() > 0 ? "Iteración " + h.iteracion() : "Sin iteración",
                "indigo"), Ui.insignia(h.puntos() + (h.puntos() == 1 ? " punto" : " puntos"), "gris"), estadoHistoria(h.estado()));

        Label pruebas = new Label(h.pasadas() + h.fallidas() == 0 ? "Sin pruebas registradas"
                : h.pasadas() + " ✔" + (h.fallidas() > 0 ? "  " + h.fallidas() + " ✘" : "") + " pruebas");
        pruebas.getStyleClass().add(h.fallidas() > 0 ? "aviso-error" : "tenue");
        Button ver = new Button("Ver pruebas");
        ver.setOnAction(e -> {
            filtroPrueba = h.id();
            filtroResultado = TODOS_RESULTADOS;
            mostrar(Seccion.PRUEBAS);
        });
        HBox pie = new HBox(8, pruebas, crecer(), ver);
        pie.setAlignment(Pos.CENTER_LEFT);

        VBox caja = Ui.tarjeta(cabecera, titulo, responsables, datos, pie);
        caja.setSpacing(9);
        caja.setPrefWidth(300);
        caja.setMaxWidth(300);
        return caja;
    }

    private static Label estadoHistoria(String estado) {
        return switch (estado) {
            case "TERMINADA" -> Ui.insignia("Terminada", "verde");
            case "EN_PROGRESO" -> Ui.insignia("En progreso", "ambar");
            default -> Ui.insignia("Pendiente", "gris");
        };
    }

    // ------------------------------------------------------------------ Pruebas

    private Node pruebas() {
        List<TableroXp.PruebaEjecutada> todas = tablero.getPruebas();
        if (todas.isEmpty()) {
            return Ui.aviso("Aún no hay resultados. Ejecute «Pruebas + reporte de aceptación» en VS Code (en Docker se "
                    + "ejecutan al construir la imagen) y pulse «Actualizar datos».", false);
        }
        long pasadas = todas.stream().filter(PanelTablero::pasó).count();
        long clases = todas.stream().map(TableroXp.PruebaEjecutada::clase).distinct().count();
        long historias = todas.stream().flatMap(p -> List.of(p.historias().split(",")).stream()).map(String::strip)
                .filter(s -> !s.isEmpty()).distinct().count();
        HBox cifras = new HBox(14,
                cifraSimple("PRUEBAS", String.valueOf(todas.size()), "en " + clases + " clases de prueba"),
                cifraSimple("PASARON", String.valueOf(pasadas), "en verde"),
                cifraSimple("FALLARON", String.valueOf(todas.size() - pasadas), todas.size() == pasadas ? "ninguna" : "revisar"),
                cifraSimple("HISTORIAS CUBIERTAS", String.valueOf(historias), "con al menos una prueba"));

        TextField buscar = new TextField(filtroPrueba);
        buscar.setPromptText("Buscar por prueba, clase o historia (p. ej. HU-01)");
        buscar.setPrefWidth(340);
        ComboBox<String> resultado = new ComboBox<>(FXCollections.observableArrayList(TODOS_RESULTADOS, "Pasaron", "Fallaron"));
        resultado.setValue(filtroResultado);
        Label cuantas = new Label();
        cuantas.getStyleClass().add("tenue");
        HBox filtros = new HBox(10, buscar, resultado, cuantas);
        filtros.setAlignment(Pos.CENTER_LEFT);

        FilteredList<TableroXp.PruebaEjecutada> filtradas = new FilteredList<>(FXCollections.observableArrayList(todas));
        TableView<TableroXp.PruebaEjecutada> tabla = Ui.tabla("Ninguna prueba coincide con el filtro");
        TableColumn<TableroXp.PruebaEjecutada, String> res = new TableColumn<>("Resultado");
        res.setCellValueFactory(d -> new javafx.beans.property.ReadOnlyStringWrapper(d.getValue().resultado()));
        res.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(String valor, boolean vacio) {
                super.updateItem(valor, vacio);
                setText(null);
                setGraphic(vacio || valor == null ? null
                        : "PASO".equals(valor) ? Ui.insignia("✔ Pasó", "verde") : Ui.insignia("✘ " + valor, "roja"));
            }
        });
        res.setPrefWidth(110);
        res.setSortable(false);
        tabla.getColumns().add(res);
        tabla.getColumns().add(Ui.columna("Prueba", p -> humanizar(p.metodo()), 400));
        tabla.getColumns().add(Ui.columna("Clase", TableroXp.PruebaEjecutada::clase, 190));
        tabla.getColumns().add(Ui.columna("Historias", p -> p.historias().isBlank() ? "—" : p.historias(), 150));
        tabla.getColumns().add(Ui.columna("Tiempo", p -> p.milisegundos() + " ms", 90));
        tabla.getColumns().add(Ui.columna("Detalle", TableroXp.PruebaEjecutada::detalle, 200));
        tabla.setItems(filtradas);
        tabla.setId("tabla-pruebas");

        Runnable filtrar = () -> {
            filtroPrueba = buscar.getText();
            filtroResultado = resultado.getValue();
            String texto = Utility.normalizar(filtroPrueba);
            filtradas.setPredicate(p -> (texto.isEmpty() || Utility.normalizar(String.join(" ", p.clase(), p.metodo(),
                    humanizar(p.metodo()), p.historias())).contains(texto))
                    && (TODOS_RESULTADOS.equals(filtroResultado) || ("Pasaron".equals(filtroResultado) == pasó(p))));
            cuantas.setText("Mostrando " + filtradas.size() + " de " + todas.size());
        };
        buscar.textProperty().addListener((o, a, b) -> filtrar.run());
        resultado.setOnAction(e -> filtrar.run());
        filtrar.run();
        VBox vista = new VBox(14, cifras, filtros, tabla);
        VBox.setVgrow(tabla, Priority.ALWAYS);
        return vista;
    }

    private static VBox cifraSimple(String titulo, String valor, String detalle) {
        Label t = new Label(titulo);
        t.getStyleClass().add("cifra-titulo");
        Label v = new Label(valor);
        v.getStyleClass().add("cifra");
        Label d = new Label(detalle);
        d.getStyleClass().add("tenue");
        VBox caja = Ui.tarjeta(t, v, d);
        caja.setSpacing(4);
        caja.setPadding(new Insets(12, 16, 12, 16));
        caja.setPrefWidth(200);
        caja.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(caja, Priority.ALWAYS);
        return caja;
    }

    // ------------------------------------------------------------------ División de trabajo

    private Node division() {
        FlowPane tarjetas = new FlowPane(14, 14);
        for (TableroXp.AvanceIntegrante a : tablero.porIntegrante()) {
            tarjetas.getChildren().add(tarjetaIntegrante(a));
        }
        return tarjetas;
    }

    private Node tarjetaIntegrante(TableroXp.AvanceIntegrante a) {
        Integrante i = a.integrante();
        Label nombre = new Label(i.nombre());
        nombre.getStyleClass().add("tarea-texto");
        nombre.setStyle("-fx-font-size: 15px;");
        Label rol = Ui.texto(Ui.rolLegible(i.rol()));
        rol.getStyleClass().add("tenue");
        HBox cabecera = new HBox(12, Ui.avatar(i.nombre(), i.rol(), 46), new VBox(2, nombre, rol));
        cabecera.setAlignment(Pos.CENTER_LEFT);

        Label avance = new Label(a.hechas() + "/" + a.total() + " hechas"
                + (a.enCurso() > 0 ? " · " + a.enCurso() + " en curso" : ""));
        Label porcentaje = new Label(a.porcentaje() + " %");
        porcentaje.getStyleClass().add("seccion");
        HBox lineaAvance = new HBox(8, avance, crecer(), porcentaje);

        FlowPane sesiones = new FlowPane(6, 6);
        tablero.tareasPorSesion(i).forEach((s, n) -> sesiones.getChildren().add(Ui.insignia(corta(s) + " · " + n, "indigo")));
        FlowPane historias = new FlowPane(6, 6);
        a.historias().forEach(h -> historias.getChildren().add(Ui.insignia(h, "verde")));
        if (a.historias().isEmpty()) {
            historias.getChildren().add(new Label("—"));
        }

        Button ver = new Button("Ver sus actividades");
        ver.getStyleClass().add("primario");
        ver.setOnAction(e -> {
            filtroIntegrante = i.nombre();
            filtroSesion = TODAS;
            filtroTexto = "";
            mostrar(Seccion.ACTIVIDADES);
        });

        VBox caja = Ui.tarjeta(cabecera, lineaAvance, barra(a.porcentaje() / 100.0, ""),
                etiquetaSeccion("Actividades por sesión"), sesiones,
                etiquetaSeccion("Historias de usuario a su cargo"), historias, ver);
        caja.setSpacing(9);
        caja.setPrefWidth(305);
        caja.setMaxWidth(305);
        return caja;
    }

    // ------------------------------------------------------------------ Prácticas XP

    private Node practicas() {
        GridPane rejilla = rejilla();
        int fila = 0;
        for (TableroXp.AvancePractica p : tablero.porPractica()) {
            Label nombre = new Label(p.practica());
            nombre.setMinWidth(Region.USE_PREF_SIZE);
            ProgressBar b = barra(fraccion(p.hechas(), p.total()), p.defendida() ? "verde" : "");
            Label cuenta = new Label(p.hechas() + "/" + p.total() + " evidencias");
            cuenta.getStyleClass().add("tenue");
            rejilla.addRow(fila++, nombre, b, cuenta, p.defendida() ? Ui.insignia("✔ Defendida", "verde")
                    : Ui.insignia("Falta evidencia", "ambar"));
        }
        return Ui.tarjeta(rejilla);
    }

    // ------------------------------------------------------------------ Utilidades

    private static GridPane rejilla() {
        GridPane g = new GridPane();
        g.setHgap(14);
        g.setVgap(10);
        javafx.scene.layout.ColumnConstraints c0 = new javafx.scene.layout.ColumnConstraints();
        c0.setMinWidth(Region.USE_PREF_SIZE);
        javafx.scene.layout.ColumnConstraints c1 = new javafx.scene.layout.ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        c1.setMinWidth(120);
        g.getColumnConstraints().addAll(c0, c1);
        return g;
    }

    private static void agregarFilaAvance(GridPane g, int fila, String nombre, double avance, String detalle, String color) {
        Label n = new Label(nombre);
        Label d = new Label(detalle);
        d.getStyleClass().add("tenue");
        d.setMinWidth(Region.USE_PREF_SIZE);
        g.addRow(fila, n, barra(avance, color), d);
    }

    private static ProgressBar barra(double avance, String color) {
        ProgressBar b = new ProgressBar(avance);
        b.setMaxWidth(Double.MAX_VALUE);
        if (!color.isEmpty()) {
            b.getStyleClass().add(color);
        }
        return b;
    }

    private static Label etiquetaSeccion(String texto) {
        Label l = new Label(texto);
        l.getStyleClass().add("seccion");
        return l;
    }

    private static Label detalle(String texto, boolean listo) {
        Label l = new Label((listo ? "✔ " : "○ ") + texto);
        l.setWrapText(true);
        l.getStyleClass().add(listo ? "listo" : "pendiente");
        return l;
    }

    private void pintarDetalle() {
        TableView<TableroXp.AvanceIntegrante> personas = Ui.tabla("Sin integrantes");
        personas.getColumns().add(Ui.columna("Integrante", a -> a.integrante().nombre(), 170));
        personas.getColumns().add(Ui.columna("Rol XP", a -> a.integrante().rol(), 170));
        personas.getColumns().add(Ui.columna("Tareas hechas", a -> a.hechas() + " / " + a.total() + "  (" + a.porcentaje() + " %)", 120));
        personas.getColumns().add(Ui.columna("En curso", a -> String.valueOf(a.enCurso()), 80));
        personas.getColumns().add(Ui.columna("Historias del backlog", a -> String.join(", ", a.historias()), 250));
        personas.setItems(FXCollections.observableArrayList(tablero.porIntegrante()));
        personas.setId("tabla-integrantes");

        TableView<TableroXp.Historia> historias = Ui.tabla("No se encontró xp/historias.csv");
        historias.getColumns().add(Ui.columna("Historia", TableroXp.Historia::id, 70));
        historias.getColumns().add(Ui.columna("Título", TableroXp.Historia::titulo, 230));
        historias.getColumns().add(Ui.columna("Responsable", h -> String.join(", ", h.responsables()), 150));
        historias.getColumns().add(Ui.columna("Iteración", h -> String.valueOf(h.iteracion()), 70));
        historias.getColumns().add(Ui.columna("Puntos", h -> String.valueOf(h.puntos()), 60));
        historias.getColumns().add(Ui.columna("Estado", TableroXp.Historia::estado, 100));
        historias.getColumns().add(Ui.columna("Pruebas", h -> h.pasadas() + " ✔  " + h.fallidas() + " ✘", 90));
        historias.getColumns().add(Ui.columna("Aceptación", TableroXp.Historia::aceptacion, 110));
        historias.setItems(FXCollections.observableArrayList(tablero.getHistorias()));
        historias.setId("tabla-historias");

        TableView<TableroXp.AvancePractica> practicas = Ui.tabla("Sin prácticas");
        practicas.getColumns().add(Ui.columna("Práctica XP", TableroXp.AvancePractica::practica, 260));
        practicas.getColumns().add(Ui.columna("Tareas hechas", p -> p.hechas() + " / " + p.total(), 120));
        practicas.getColumns().add(Ui.columna("Estado", p -> p.defendida() ? "Defendida" : "Falta evidencia", 160));
        practicas.setItems(FXCollections.observableArrayList(tablero.porPractica()));
        practicas.setId("tabla-practicas");

        detalle.getTabs().setAll(new Tab("Por integrante", personas),
                new Tab("Historias (HU) y pruebas", historias),
                new Tab("Pruebas ejecutadas", vistaPruebas()),
                new Tab("Prácticas XP", practicas));
    }

    private VBox vistaPruebas() {
        List<TableroXp.PruebaEjecutada> pruebas = tablero.getPruebas();
        long pasadas = pruebas.stream().filter(p -> "PASO".equals(p.resultado())).count();
        Label resumenPruebas = new Label(pruebas.isEmpty()
                ? "Ejecute las pruebas y pulse Actualizar para cargar los resultados."
                : pruebas.size() + " pruebas · " + pasadas + " pasaron · " + (pruebas.size() - pasadas) + " fallaron");
        resumenPruebas.setId("resumen-pruebas-xp");
        TextField filtro = new TextField();
        filtro.setPromptText("Buscar por clase, prueba, historia o resultado");
        TableView<TableroXp.PruebaEjecutada> tabla = Ui.tabla("Sin pruebas para mostrar");
        tabla.getColumns().add(Ui.columna("Clase", TableroXp.PruebaEjecutada::clase, 190));
        tabla.getColumns().add(Ui.columna("Prueba", TableroXp.PruebaEjecutada::metodo, 350));
        tabla.getColumns().add(Ui.columna("Historias", TableroXp.PruebaEjecutada::historias, 140));
        tabla.getColumns().add(Ui.columna("Resultado", TableroXp.PruebaEjecutada::resultado, 90));
        tabla.getColumns().add(Ui.columna("ms", TableroXp.PruebaEjecutada::milisegundos, 60));
        tabla.getColumns().add(Ui.columna("Detalle", TableroXp.PruebaEjecutada::detalle, 200));
        tabla.setId("tabla-pruebas-xp");
        tabla.setItems(FXCollections.observableArrayList(pruebas));
        filtro.textProperty().addListener((propiedad, anterior, valor) -> {
            String consulta = valor.strip().toLowerCase(Locale.ROOT);
            tabla.getItems().setAll(pruebas.stream().filter(p -> String.join(" ", p.clase(), p.metodo(),
                    p.historias(), p.resultado()).toLowerCase(Locale.ROOT).contains(consulta)).toList());
        });
        VBox.setVgrow(tabla, Priority.ALWAYS);
        VBox vista = new VBox(8, resumenPruebas, filtro, tabla);
        vista.setPadding(new Insets(8));
        return vista;
    }

    Parent getRaiz() {
        return raiz;
    }
}
