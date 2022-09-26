package co.edu.usbcali.ingesoft.service.dto;

import co.edu.usbcali.ingesoft.domain.enumeration.NombreFormato;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usbcali.ingesoft.domain.Actividad} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActividadDTO implements Serializable {

    private Long id;

    private String actividadName;

    private String description;

    private Instant fechaInicio;

    private Instant fechaFin;

    private NombreFormato formato;

    private CursoDTO curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActividadName() {
        return actividadName;
    }

    public void setActividadName(String actividadName) {
        this.actividadName = actividadName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public NombreFormato getFormato() {
        return formato;
    }

    public void setFormato(NombreFormato formato) {
        this.formato = formato;
    }

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActividadDTO)) {
            return false;
        }

        ActividadDTO actividadDTO = (ActividadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, actividadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActividadDTO{" +
            "id=" + getId() +
            ", actividadName='" + getActividadName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", formato='" + getFormato() + "'" +
            ", curso=" + getCurso() +
            "}";
    }
}
