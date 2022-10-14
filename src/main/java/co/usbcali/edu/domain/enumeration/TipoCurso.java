package co.usbcali.edu.domain.enumeration;

/**
 * The TipoCurso enumeration.
 */
public enum TipoCurso {
    CALCULO("Calculo"),
    AVANZADA("Avanzada"),
    MAGANA("Magana");

    private final String value;

    TipoCurso(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
