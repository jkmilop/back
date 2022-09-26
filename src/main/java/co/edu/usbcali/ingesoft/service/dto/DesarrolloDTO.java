package co.edu.usbcali.ingesoft.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usbcali.ingesoft.domain.Desarrollo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DesarrolloDTO implements Serializable {

    private Long id;

    private Boolean terminado;

    private Double nota;

    private EstudianteDTO estudiante;

    private ActividadDTO actividad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(Boolean terminado) {
        this.terminado = terminado;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public EstudianteDTO getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteDTO estudiante) {
        this.estudiante = estudiante;
    }

    public ActividadDTO getActividad() {
        return actividad;
    }

    public void setActividad(ActividadDTO actividad) {
        this.actividad = actividad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DesarrolloDTO)) {
            return false;
        }

        DesarrolloDTO desarrolloDTO = (DesarrolloDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, desarrolloDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DesarrolloDTO{" +
            "id=" + getId() +
            ", terminado='" + getTerminado() + "'" +
            ", nota=" + getNota() +
            ", estudiante=" + getEstudiante() +
            ", actividad=" + getActividad() +
            "}";
    }
}
