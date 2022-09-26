package co.edu.usbcali.ingesoft.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Profesor.
 */
@Entity
@Table(name = "profesor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "profesor_name")
    private String profesorName;

    @Column(name = "codigo_profesor")
    private String codigoProfesor;

    @Column(name = "correo")
    private String correo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profesor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesorName() {
        return this.profesorName;
    }

    public Profesor profesorName(String profesorName) {
        this.setProfesorName(profesorName);
        return this;
    }

    public void setProfesorName(String profesorName) {
        this.profesorName = profesorName;
    }

    public String getCodigoProfesor() {
        return this.codigoProfesor;
    }

    public Profesor codigoProfesor(String codigoProfesor) {
        this.setCodigoProfesor(codigoProfesor);
        return this;
    }

    public void setCodigoProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Profesor correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profesor)) {
            return false;
        }
        return id != null && id.equals(((Profesor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profesor{" +
            "id=" + getId() +
            ", profesorName='" + getProfesorName() + "'" +
            ", codigoProfesor='" + getCodigoProfesor() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
