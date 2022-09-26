package co.edu.usbcali.ingesoft.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usbcali.ingesoft.domain.Profesor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfesorDTO implements Serializable {

    private Long id;

    private String profesorName;

    private String codigoProfesor;

    private String correo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesorName() {
        return profesorName;
    }

    public void setProfesorName(String profesorName) {
        this.profesorName = profesorName;
    }

    public String getCodigoProfesor() {
        return codigoProfesor;
    }

    public void setCodigoProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfesorDTO)) {
            return false;
        }

        ProfesorDTO profesorDTO = (ProfesorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profesorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfesorDTO{" +
            "id=" + getId() +
            ", profesorName='" + getProfesorName() + "'" +
            ", codigoProfesor='" + getCodigoProfesor() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
