package co.edu.usbcali.ingesoft.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usbcali.ingesoft.domain.Estudiante} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstudianteDTO implements Serializable {

    private Long id;

    private String estudianteName;

    private String codigoEstudiante;

    private String correo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstudianteName() {
        return estudianteName;
    }

    public void setEstudianteName(String estudianteName) {
        this.estudianteName = estudianteName;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
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
        if (!(o instanceof EstudianteDTO)) {
            return false;
        }

        EstudianteDTO estudianteDTO = (EstudianteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estudianteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstudianteDTO{" +
            "id=" + getId() +
            ", estudianteName='" + getEstudianteName() + "'" +
            ", codigoEstudiante='" + getCodigoEstudiante() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
