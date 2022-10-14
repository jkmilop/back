package co.usbcali.edu.domain.enumeration;

/**
 * The Formato enumeration.
 */
public enum Formato {
    QUIZ("Quiz"),
    ACTIVIDAD("Actividad"),
    PARCIAL("Parcial");

    private final String value;

    Formato(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
