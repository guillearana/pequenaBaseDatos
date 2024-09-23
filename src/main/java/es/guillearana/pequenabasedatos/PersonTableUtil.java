package es.guillearana.pequenabasedatos;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Utilidad para la creación y manejo de datos y columnas de la tabla de personas.
 */
public class PersonTableUtil {

    /**
     * Devuelve una lista observable de objetos Person predefinidos.
     * Esta lista se puede utilizar para poblar una TableView en JavaFX.
     *
     * @return ObservableList de objetos Person.
     */
    public static ObservableList<Person> getPersonList() {
        Person p1 = new Person("Ashwin", "Sharan", LocalDate.of(2012, 10, 11));
        Person p2 = new Person("Advik", "Sharan", LocalDate.of(2012, 10, 11));
        Person p3 = new Person("Layne", "Estes", LocalDate.of(2011, 12, 16));
        Person p4 = new Person("Mason", "Boyd", LocalDate.of(2003, 4, 20));
        Person p5 = new Person("Babalu", "Sharan", LocalDate.of(1980, 1, 10));
        return FXCollections.observableArrayList(p1, p2, p3, p4, p5);
    }

    /**
     * Devuelve una columna de TableView configurada para mostrar el ID de la persona.
     *
     * @return TableColumn configurada para el campo personId de la clase Person.
     */
    public static TableColumn<Person, Integer> getIdColumn() {
        TableColumn<Person, Integer> personIdCol = new TableColumn<>("Id");
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        return personIdCol;
    }

    /**
     * Devuelve una columna de TableView configurada para mostrar el primer nombre de la persona.
     *
     * @return TableColumn configurada para el campo firstName de la clase Person.
     */
    public static TableColumn<Person, String> getFirstNameColumn() {
        TableColumn<Person, String> fNameCol = new TableColumn<>("First Name");
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        return fNameCol;
    }

    /**
     * Devuelve una columna de TableView configurada para mostrar el apellido de la persona.
     *
     * @return TableColumn configurada para el campo lastName de la clase Person.
     */
    public static TableColumn<Person, String> getLastNameColumn() {
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        return lastNameCol;
    }

    /**
     * Devuelve una columna de TableView configurada para mostrar la fecha de nacimiento de la persona.
     *
     * @return TableColumn configurada para el campo birthDate de la clase Person.
     */
    public static TableColumn<Person, LocalDate> getBirthDateColumn() {
        TableColumn<Person, LocalDate> bDateCol = new TableColumn<>("Birth Date");
        bDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        return bDateCol;
    }
}
