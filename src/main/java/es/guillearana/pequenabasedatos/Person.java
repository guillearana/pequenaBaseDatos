package es.guillearana.pequenabasedatos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa una persona con detalles como ID, nombre, apellido y fecha de nacimiento.
 * Incluye reglas de validación específicas del dominio y una categorización de edad.
 */
public class Person {
    private static AtomicInteger personSequence = new AtomicInteger(0); // Secuencia para generar IDs únicos
    private int personId; // ID de la persona
    private String firstName; // Primer nombre
    private String lastName; // Apellido
    private LocalDate birthDate; // Fecha de nacimiento

    /**
     * Enum que define las categorías de edad de una persona.
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    };

    /**
     * Constructor por defecto que inicializa la persona con valores nulos.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor que inicializa una persona con nombre, apellido y fecha de nacimiento.
     *
     * @param firstName  Primer nombre de la persona.
     * @param lastName   Apellido de la persona.
     * @param birthDate  Fecha de nacimiento de la persona.
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    // Métodos getter y setter para los campos de la clase

    /**
     * Obtiene el ID de la persona.
     *
     * @return ID de la persona.
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Establece el ID de la persona.
     *
     * @param personId ID de la persona a establecer.
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Obtiene el primer nombre de la persona.
     *
     * @return Primer nombre de la persona.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el primer nombre de la persona.
     *
     * @param firstName Primer nombre de la persona a establecer.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido de la persona.
     *
     * @return Apellido de la persona.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido de la persona.
     *
     * @param lastName Apellido de la persona a establecer.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     *
     * @return Fecha de nacimiento de la persona.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Establece la fecha de nacimiento de la persona.
     *
     * @param birthDate Fecha de nacimiento de la persona a establecer.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Valida si la fecha de nacimiento es válida (no está en el futuro).
     *
     * @param bdate Fecha de nacimiento a validar.
     * @return true si la fecha es válida, false de lo contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /**
     * Valida si la fecha de nacimiento es válida y agrega mensajes de error si es necesario.
     *
     * @param bdate     Fecha de nacimiento a validar.
     * @param errorList Lista para almacenar mensajes de error.
     * @return true si la fecha es válida, false de lo contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        // La fecha de nacimiento no puede estar en el futuro
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future.");
            return false;
        }
        return true;
    }

    /**
     * Valida si la persona es válida según las reglas de negocio.
     *
     * @param errorList Lista para almacenar mensajes de error.
     * @return true si la persona es válida, false de lo contrario.
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /**
     * Valida si una persona específica es válida y agrega mensajes de error si es necesario.
     *
     * @param p         Persona a validar.
     * @param errorList Lista para almacenar mensajes de error.
     * @return true si la persona es válida, false de lo contrario.
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().length() == 0) {
            errorList.add("First name must contain minimum one character.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Determina la categoría de edad de la persona basada en la fecha de nacimiento.
     *
     * @return Categoría de edad de la persona.
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }

    /**
     * Guarda la persona si es válida y muestra un mensaje de éxito. Agrega mensajes de error si la persona no es válida.
     *
     * @param errorList Lista para almacenar mensajes de error.
     * @return true si la persona se guarda correctamente, false de lo contrario.
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }

    /**
     * Devuelve una representación en forma de cadena de la persona.
     *
     * @return String que representa la persona con su ID, nombre, apellido y fecha de nacimiento.
     */
    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}
