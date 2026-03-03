package com.dermacore.generadorexamenes;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HelloController {
    // ComboBoxes para la selección jerárquica
    @FXML private ComboBox<Asignatura> cmbAsignaturas;
    @FXML private ComboBox<Unidad> cmbUnidades;
    @FXML private ComboBox<Tema> cmbTemas;
    @FXML private Button btnAddAsignatura;
    @FXML private Button btnDeleteAsignatura;
    @FXML private Button btnAddUnidad;
    @FXML private Button btnDeleteUnidad;
    @FXML private Button btnAddTema;
    @FXML private Button btnDeleteTema;

    // Tabla para mostrar las preguntas
    @FXML private TableView<Pregunta> tblPreguntas;
    @FXML private TableColumn<Pregunta, String> colPreguntaEnunciado;

    // Formulario para crear/editar preguntas
    @FXML private TextArea txtAreaPregunta;
    @FXML private TextField txtIncisoA;
    @FXML private TextField txtIncisoB;
    @FXML private TextField txtIncisoC;
    @FXML private TextField txtIncisoD;
    @FXML private RadioButton radioA;
    @FXML private RadioButton radioB;
    @FXML private RadioButton radioC;
    @FXML private RadioButton radioD;
    @FXML private ToggleGroup correctAnswerGroup; // Grupo para asegurar una sola selección

    // Botones de acción
    @FXML private Button btnNuevo;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;

    // --- Variables para manejar los datos (El "Modelo") ---
    private Profesor profesorLogueado; // Simula el profesor que ha iniciado sesión
    private Pregunta preguntaSeleccionada; // Guarda la pregunta que se está editando

    @FXML
    public void initialize() {
        profesorLogueado = GuardadoEnXML.cargarProfesor();

        if (profesorLogueado == null) {
            crearDatosDePrueba(); // Si no hay archivo XML se crearan datos de prueba
        }

        configurarListeners();
        cmbAsignaturas.setItems(FXCollections.observableArrayList(profesorLogueado.getAsignaturas()));
    }


    @FXML
    private void onAddAsignatura() {
        // Pide al usuario el nombre de la nueva asignatura.
        Optional<String> resultado = pedirTextoAUsuario("Nueva Asignatura", "Introduce el nombre de la nueva asignatura:");
        resultado.ifPresent(nombre -> {
            Asignatura nuevaAsignatura = new Asignatura(nombre);
            profesorLogueado.agregarAsignatura(nuevaAsignatura);
            refrescarComboBoxAsignaturas(); // Actualiza la lista
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Asignatura '" + nombre + "' creada.");
        });
    }

    @FXML
    private void onDeleteAsignatura() {
        Asignatura seleccionada = cmbAsignaturas.getValue();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Selecciona una asignatura para eliminar.");
            return;
        }
        // Pide confirmación antes de borrar.
        if (confirmarAccion("¿Seguro que quieres eliminar la asignatura '" + seleccionada.getNombre() + "'? Se borrarán todas sus unidades y preguntas.")) {
            profesorLogueado.eliminarAsignatura(seleccionada);
            refrescarComboBoxAsignaturas();
        }
    }

    @FXML
    private void onAddUnidad() {
        Asignatura asignaturaSeleccionada = cmbAsignaturas.getValue();
        if (asignaturaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Selecciona una asignatura para añadirle una unidad.");
            return;
        }
        Optional<String> resultado = pedirTextoAUsuario("Nueva Unidad", "Introduce el nombre de la nueva unidad para " + asignaturaSeleccionada.getNombre() + ":");
        resultado.ifPresent(nombre -> {
            Unidad nuevaUnidad = new Unidad(nombre);
            asignaturaSeleccionada.agregarUnidad(nuevaUnidad);
            refrescarComboBoxUnidades();
        });
    }

    @FXML
    private void onDeleteUnidad() {
        Unidad seleccionada = cmbUnidades.getValue();
        Asignatura asignaturaPadre = cmbAsignaturas.getValue();
        if (seleccionada == null || asignaturaPadre == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Selecciona una unidad para eliminar.");
            return;
        }
        if (confirmarAccion("¿Seguro que quieres eliminar la unidad '" + seleccionada.getNombre() + "'?")) {
            asignaturaPadre.eliminarUnidad(seleccionada);
            refrescarComboBoxUnidades();
        }
    }

    @FXML
    private void onAddTema() {
        Unidad unidadSeleccionada = cmbUnidades.getValue();
        if (unidadSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Selecciona una unidad para añadirle un tema.");
            return;
        }
        Optional<String> resultado = pedirTextoAUsuario("Nuevo Tema", "Introduce el nombre del nuevo tema para " + unidadSeleccionada.getNombre() + ":");
        resultado.ifPresent(nombre -> {
            Tema nuevoTema = new Tema(nombre);
            unidadSeleccionada.agregarTema(nuevoTema);
            refrescarComboBoxTemas();
        });
    }

    @FXML
    private void onDeleteTema() {
        Tema seleccionado = cmbTemas.getValue();
        Unidad unidadPadre = cmbUnidades.getValue();
        if (seleccionado == null || unidadPadre == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Selecciona un tema para eliminar.");
            return;
        }
        if (confirmarAccion("¿Seguro que quieres eliminar el tema '" + seleccionado.getNombre() + "'?")) {
            unidadPadre.eliminarTema(seleccionado);
            refrescarComboBoxTemas();
        }
    }

    // --- Métodos para refrescar las listas desplegables ---

    private void refrescarComboBoxAsignaturas() {
        cmbAsignaturas.setItems(FXCollections.observableArrayList(profesorLogueado.getAsignaturas()));
        cmbUnidades.getItems().clear();
        cmbTemas.getItems().clear();
        tblPreguntas.getItems().clear();
    }

    private void refrescarComboBoxUnidades() {
        Asignatura asignaturaSeleccionada = cmbAsignaturas.getValue();
        if (asignaturaSeleccionada != null) {
            cmbUnidades.setItems(FXCollections.observableArrayList(asignaturaSeleccionada.getUnidades()));
        }
        cmbTemas.getItems().clear();
        tblPreguntas.getItems().clear();
    }

    private void refrescarComboBoxTemas() {
        Unidad unidadSeleccionada = cmbUnidades.getValue();
        if (unidadSeleccionada != null) {
            cmbTemas.setItems(FXCollections.observableArrayList(unidadSeleccionada.getTemas()));
        }
        tblPreguntas.getItems().clear();
    }

    // --- Lógica de Listeners (actualizada para usar los métodos de refresco) ---

    private void configurarListeners() {
        cmbAsignaturas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> refrescarComboBoxUnidades());
        cmbUnidades.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> refrescarComboBoxTemas());
        cmbTemas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> actualizarTablaPreguntas());

        tblPreguntas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            preguntaSeleccionada = newVal;
            if (preguntaSeleccionada != null) {
                mostrarDetallesPregunta(preguntaSeleccionada);
            }
        });
    }

    // --- Métodos de Ayuda (reutilizables) ---

    private Optional<String> pedirTextoAUsuario(String titulo, String contenido) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.setContentText(contenido);
        return dialog.showAndWait();
    }

    private boolean confirmarAccion(String mensaje) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Acción");
        confirmacion.setHeaderText(mensaje);
        confirmacion.setContentText("Esta acción no se puede deshacer.");
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    /**
     * Actualiza la tabla con las preguntas del tema actualmente seleccionado en el ComboBox.
     */
    private void actualizarTablaPreguntas() {
        Tema temaSeleccionado = cmbTemas.getSelectionModel().getSelectedItem();
        if (temaSeleccionado != null) {
            // "PropertyValueFactory" conecta la columna de la tabla con el método getArgumento() de la clase Pregunta.
            colPreguntaEnunciado.setCellValueFactory(new PropertyValueFactory<>("Argumento"));
            tblPreguntas.setItems(FXCollections.observableArrayList(temaSeleccionado.getPreguntas()));
        }
    }

    /**
     * Rellena los campos del formulario con la información de una pregunta.
     * @param pregunta La pregunta a mostrar.
     */
    private void mostrarDetallesPregunta(Pregunta pregunta) {
        txtAreaPregunta.setText(pregunta.getArgumento());

        List<Inciso> incisos = pregunta.getIncisos();
        if (incisos != null && incisos.size() >= 4) {
            txtIncisoA.setText(incisos.get(0).getRespuesta());
            radioA.setSelected(incisos.get(0).isValorVerdad());

            txtIncisoB.setText(incisos.get(1).getRespuesta());
            radioB.setSelected(incisos.get(1).isValorVerdad());

            txtIncisoC.setText(incisos.get(2).getRespuesta());
            radioC.setSelected(incisos.get(2).isValorVerdad());

            txtIncisoD.setText(incisos.get(3).getRespuesta());
            radioD.setSelected(incisos.get(3).isValorVerdad());
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarFormulario() {
        txtAreaPregunta.clear();
        txtIncisoA.clear();
        txtIncisoB.clear();
        txtIncisoC.clear();
        txtIncisoD.clear();
        if (correctAnswerGroup.getSelectedToggle() != null) {
            correctAnswerGroup.getSelectedToggle().setSelected(false);
        }
        preguntaSeleccionada = null; // Deseleccionamos la pregunta actual
        tblPreguntas.getSelectionModel().clearSelection();
    }

    // --- Métodos para los Botones (Acciones del Usuario) ---

    @FXML
    private void onNuevoButtonClick() {
        limpiarFormulario();
    }

// --- LÓGICA DE BOTONES CORREGIDA ---

    @FXML
    private void onGuardarButtonClick() {
        // --- CORRECCIÓN 1: Validación de campos ---
        if (!validarFormulario()) {
            return; // Si la validación falla, no hacemos nada más.
        }

        Tema temaSeleccionado = cmbTemas.getSelectionModel().getSelectedItem();
        List<Inciso> incisosDelFormulario = new ArrayList<>();
        incisosDelFormulario.add(new Inciso(txtIncisoA.getText(), radioA.isSelected()));
        incisosDelFormulario.add(new Inciso(txtIncisoB.getText(), radioB.isSelected()));
        incisosDelFormulario.add(new Inciso(txtIncisoC.getText(), radioC.isSelected()));
        incisosDelFormulario.add(new Inciso(txtIncisoD.getText(), radioD.isSelected()));

        // --- CORRECCIÓN 2: Lógica de Edición ---
        if (preguntaSeleccionada == null) { // Si es una pregunta NUEVA
            Pregunta nuevaPregunta = new Pregunta(txtAreaPregunta.getText(), incisosDelFormulario);
            temaSeleccionado.agregarPregunta(nuevaPregunta);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "La pregunta ha sido guardada correctamente.");
        } else { // Si es una ACTUALIZACIÓN de una pregunta existente
            // Modificamos el objeto existente directamente en lugar de crear uno nuevo.
            preguntaSeleccionada.setArgumento(txtAreaPregunta.getText());
            preguntaSeleccionada.setIncisos(incisosDelFormulario);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "La pregunta ha sido actualizada correctamente.");
        }

        actualizarTablaPreguntas(); // Refrescamos la tabla para ver los cambios
        limpiarFormulario();
        GuardadoEnXML.guardarProfesor(profesorLogueado);
    }

    @FXML
    private void onEliminarButtonClick() {
        Tema temaSeleccionado = cmbTemas.getSelectionModel().getSelectedItem();
        // --- CORRECCIÓN 3: Lógica de Eliminación ---
        if (preguntaSeleccionada == null || temaSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Acción inválida", "Por favor, selecciona una pregunta de la tabla para eliminar.");
            return;
        }

        // Pedimos confirmación al usuario antes de borrar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de que deseas eliminar esta pregunta?");
        confirmacion.setContentText(preguntaSeleccionada.getArgumento());

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            boolean eliminado = temaSeleccionado.eliminarPregunta(preguntaSeleccionada);
            if (eliminado) {
                actualizarTablaPreguntas();
                limpiarFormulario();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "La pregunta fue eliminada.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la pregunta.");
            }
        }
    }

    // --- NUEVOS MÉTODOS DE AYUDA ---

    private boolean validarFormulario() {
        String mensajeError = "";
        if (txtAreaPregunta.getText().trim().isEmpty()) {
            mensajeError += "El texto de la pregunta no puede estar vacío.\n";
        }
        if (txtIncisoA.getText().trim().isEmpty() || txtIncisoB.getText().trim().isEmpty() ||
                txtIncisoC.getText().trim().isEmpty() || txtIncisoD.getText().trim().isEmpty()) {
            mensajeError += "Todos los incisos deben tener texto.\n";
        }
        if (correctAnswerGroup.getSelectedToggle() == null) {
            mensajeError += "Debes seleccionar una respuesta como la correcta.\n";
        }
        if (cmbTemas.getSelectionModel().getSelectedItem() == null) {
            mensajeError += "Debes seleccionar una asignatura, unidad y tema.\n";
        }

        if (mensajeError.isEmpty()) {
            return true;
        } else {
            // Mostramos una alerta con todos los errores encontrados
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", mensajeError);
            return false;
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void crearDatosDePrueba() {
        // 1. Crear Profesor
        this.profesorLogueado = new Profesor("PROF01", "Edgar Israel", "Otamendi", "Garcia", "otamendi", "123", "edgar@mail.com");

        // 2. Crear Asignatura
        Asignatura matematicas = new Asignatura("Matemáticas Discretas");
        Asignatura programacion = new Asignatura("Programación Orientada a Objetos");

        // 3. Crear Unidades
        Unidad unidad1_mate = new Unidad("Lógica Proposicional");
        Unidad unidad2_poo = new Unidad("Conceptos Fundamentales de POO");

        // 4. Crear Temas
        Tema tema_tablasVerdad = new Tema("Tablas de Verdad");
        Tema tema_encapsulamiento = new Tema("Encapsulamiento y Herencia");

        // 5. Crear Preguntas de ejemplo
        Pregunta p1 = new Pregunta("En POO, ¿qué es la encapsulación?", Arrays.asList(
                new Inciso("Ocultar la complejidad y proteger los datos", true),
                new Inciso("Permitir que una clase herede de otra", false),
                new Inciso("Crear múltiples métodos con el mismo nombre", false),
                new Inciso("Una forma de bucle", false)
        ));
        tema_encapsulamiento.agregarPregunta(p1);

        // 6. Conectar todo
        unidad1_mate.agregarTema(tema_tablasVerdad);
        unidad2_poo.agregarTema(tema_encapsulamiento);
        matematicas.agregarUnidad(unidad1_mate);
        programacion.agregarUnidad(unidad2_poo);
        profesorLogueado.agregarAsignatura(matematicas);
        profesorLogueado.agregarAsignatura(programacion);
    }
}