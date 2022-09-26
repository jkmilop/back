package co.edu.usbcali.ingesoft.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Desarrollo.
 */
@Entity
@Table(name = "desarrollo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Desarrollo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "terminado")
    private Boolean terminado;

    @Column(name = "nota")
    private Double nota;

    @ManyToOne
    private Estudiante estudiante;

    @ManyToOne
    @JsonIgnoreProperties(value = { "curso" }, allowSetters = true)
    private Actividad actividad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Desarrollo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTerminado() {
        return this.terminado;
    }

    public Desarrollo terminado(Boolean terminado) {
        this.setTerminado(terminado);
        return this;
    }

    public void setTerminado(Boolean terminado) {
        this.terminado = terminado;
    }

    public Double getNota() {
        return this.nota;
    }

    public Desarrollo nota(Double nota) {
        this.setNota(nota);
        return this;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Desarrollo estudiante(Estudiante estudiante) {
        this.setEstudiante(estudiante);
        return this;
    }

    public Actividad getActividad() {
        return this.actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Desarrollo actividad(Actividad actividad) {
        this.setActividad(actividad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Desarrollo)) {
            return false;
        }
        return id != null && id.equals(((Desarrollo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Desarrollo{" +
            "id=" + getId() +
            ", terminado='" + getTerminado() + "'" +
            ", nota=" + getNota() +
            "}";
    }
}
