package es.guillearana.pequenabasedatos;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;

import javafx.scene.layout.HBox;
import static javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * Clase principal que demuestra cómo agregar y eliminar filas en un TableView en JavaFX.
 * Permite gestionar una lista de personas mostrando sus nombres y fecha de nacimiento.
 */
public class TableViewAddDeleteRows extends Application {
    // Campos para añadir detalles de la persona
    private TextField fNameField; // Campo de texto para el primer nombre
    private TextField lNameField; // Campo de texto para el apellido
    private DatePicker dobField;  // Campo de selección de fecha de nacimiento
    private TableView<Person> table; // TableView para mostrar la lista de personas

    /**
     * Método principal para lanzar la aplicación JavaFX.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Método de inicio que configura el escenario principal de la aplicación.
     *
     * @param stage El escenario principal de la aplicación.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        fNameField = new TextField(); // Inicializa el campo de primer nombre
        lNameField = new TextField(); // Inicializa el campo de apellido
        dobField = new DatePicker(); // Inicializa el selector de fecha de nacimiento
        table = new TableView<>(PersonTableUtil.getPersonList()); // Configura la tabla con datos iniciales de personas

        // Activa la selección de múltiples filas en el TableView
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);

        // Añade las columnas al TableView
        table.getColumns().addAll(
                PersonTableUtil.getIdColumn(),
                PersonTableUtil.getFirstNameColumn(),
                PersonTableUtil.getLastNameColumn(),
                PersonTableUtil.getBirthDateColumn()
        );

        // Crea el panel para ingresar datos de nuevas personas
        GridPane newDataPane = this.getNewPersonDataPane();

        // Botones para restaurar y eliminar filas
        Button restoreBtn = new Button("Restore Rows");
        restoreBtn.setOnAction(e -> restoreRows());

        Button deleteBtn = new Button("Delete Selected Rows");
        deleteBtn.setOnAction(e -> deleteSelectedRows());

        // Configuración de tooltips para los botones
        Tooltip deleteTool = new Tooltip("Eliminar fila");
        deleteBtn.setTooltip(deleteTool);

        Tooltip restoreTool = new Tooltip("Restaurar filas");
        restoreBtn.setTooltip(restoreTool);

        // Configuración del layout principal
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn), table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Crear y configurar la escena
        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
        stage.setMinWidth(400);
        stage.setMaxWidth(400);
        stage.setMinHeight(500);
        stage.setMaxHeight(550);
    }

    /**
     * Crea un GridPane que contiene los campos para añadir los datos de una nueva persona.
     *
     * @return GridPane con los campos de entrada de datos.
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("First Name:"), fNameField);
        pane.addRow(1, new Label("Last Name:"), lNameField);
        pane.addRow(2, new Label("Birth Date:"), dobField);

        // Botón para agregar una nueva persona
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> addPerson());

        // Configuración del Tooltip para el botón de agregar
        Tooltip addTool = new Tooltip("Agregar información");
        addBtn.setTooltip(addTool);

        // Añadir el botón al panel
        pane.add(addBtn, 3, 0);
        return pane;
    }

    /**
     * Elimina las filas seleccionadas en el TableView.
     * Si no hay filas seleccionadas, muestra un mensaje de error en la consola.
     */
    public void deleteSelectedRows() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }
        // Obtiene los índices de todas las filas seleccionadas
        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        // Ordena el arreglo de índices
        Arrays.sort(selectedIndices);

        // Elimina las filas (de la última a la primera)
        for (int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }

    /**
     * Restaura las filas originales en el TableView desde la lista de personas.
     */
    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(PersonTableUtil.getPersonList());
    }

    /**
     * Crea y devuelve una nueva instancia de Person basada en los campos de entrada.
     *
     * @return Una nueva instancia de Person.
     */
    public Person getPerson() {
        return new Person(fNameField.getText(), lNameField.getText(), dobField.getValue());
    }

    /**
     * Añade una nueva persona al TableView si los campos no están vacíos.
     * Muestra una alerta de error si algún campo está vacío.
     */
    public void addPerson() {
        Person p = getPerson();
        if (fNameField.getText().isEmpty() || lNameField.getText().isEmpty() || dobField.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No se pueden añadir filas vacías");
            alert.show();
        } else {
            table.getItems().add(p);
            clearFields();
        }
    }

    /**
     * Limpia los campos de entrada de texto y fecha.
     */
    public void clearFields() {
        fNameField.setText(null);
        lNameField.setText(null);
        dobField.setValue(null);
    }
}
