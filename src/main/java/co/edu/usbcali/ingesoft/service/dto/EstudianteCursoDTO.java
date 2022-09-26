package co.edu.usbcali.ingesoft.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usbcali.ingesoft.domain.EstudianteCurso} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstudianteCursoDTO implements Serializable {

    private Long id;

    private CursoDTO curso;

    private EstudianteDTO estudiante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    public EstudianteDTO getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteDTO estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstudianteCursoDTO)) {
            return false;
        }

        EstudianteCursoDTO estudianteCursoDTO = (EstudianteCursoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estudianteCursoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstudianteCursoDTO{" +
            "id=" + getId() +
            ", curso=" + getCurso() +
            ", estudiante=" + getEstudiante() +
            "}";
    }
}
