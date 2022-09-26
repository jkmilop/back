package co.edu.usbcali.ingesoft.domain;

import co.edu.usbcali.ingesoft.domain.enumeration.NombreFormato;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Actividad.
 */
@Entity
@Table(name = "actividad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "actividad_name")
    private String actividadName;

    @Column(name = "description")
    private String description;

    @Column(name = "fecha_inicio")
    private Instant fechaInicio;

    @Column(name = "fecha_fin")
    private Instant fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "formato")
    private NombreFormato formato;

    @ManyToOne
    @JsonIgnoreProperties(value = { "profesor" }, allowSetters = true)
    private Curso curso;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Actividad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActividadName() {
        return this.actividadName;
    }

    public Actividad actividadName(String actividadName) {
        this.setActividadName(actividadName);
        return this;
    }

    public void setActividadName(String actividadName) {
        this.actividadName = actividadName;
    }

    public String getDescription() {
        return this.description;
    }

    public Actividad description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getFechaInicio() {
        return this.fechaInicio;
    }

    public Actividad fechaInicio(Instant fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return this.fechaFin;
    }

    public Actividad fechaFin(Instant fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public NombreFormato getFormato() {
        return this.formato;
    }

    public Actividad formato(NombreFormato formato) {
        this.setFormato(formato);
        return this;
    }

    public void setFormato(NombreFormato formato) {
        this.formato = formato;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Actividad curso(Curso curso) {
        this.setCurso(curso);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actividad)) {
            return false;
        }
        return id != null && id.equals(((Actividad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Actividad{" +
            "id=" + getId() +
            ", actividadName='" + getActividadName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", formato='" + getFormato() + "'" +
            "}";
    }
}
