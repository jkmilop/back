package co.edu.usbcali.ingesoft.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estudiante.
 */
@Entity
@Table(name = "estudiante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "estudiante_name")
    private String estudianteName;

    @Column(name = "codigo_estudiante")
    private String codigoEstudiante;

    @Column(name = "correo")
    private String correo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estudiante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstudianteName() {
        return this.estudianteName;
    }

    public Estudiante estudianteName(String estudianteName) {
        this.setEstudianteName(estudianteName);
        return this;
    }

    public void setEstudianteName(String estudianteName) {
        this.estudianteName = estudianteName;
    }

    public String getCodigoEstudiante() {
        return this.codigoEstudiante;
    }

    public Estudiante codigoEstudiante(String codigoEstudiante) {
        this.setCodigoEstudiante(codigoEstudiante);
        return this;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Estudiante correo(String correo) {
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
        if (!(o instanceof Estudiante)) {
            return false;
        }
        return id != null && id.equals(((Estudiante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estudiante{" +
            "id=" + getId() +
            ", estudianteName='" + getEstudianteName() + "'" +
            ", codigoEstudiante='" + getCodigoEstudiante() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
