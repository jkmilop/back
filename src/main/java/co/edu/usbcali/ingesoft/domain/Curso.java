package co.edu.usbcali.ingesoft.domain;

import co.edu.usbcali.ingesoft.domain.enumeration.TipoCurso;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Curso.
 */
@Entity
@Table(name = "curso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "curso_name")
    private String cursoName;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_curso")
    private TipoCurso tipoCurso;

    @ManyToOne
    private Profesor profesor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Curso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCursoName() {
        return this.cursoName;
    }

    public Curso cursoName(String cursoName) {
        this.setCursoName(cursoName);
        return this;
    }

    public void setCursoName(String cursoName) {
        this.cursoName = cursoName;
    }

    public TipoCurso getTipoCurso() {
        return this.tipoCurso;
    }

    public Curso tipoCurso(TipoCurso tipoCurso) {
        this.setTipoCurso(tipoCurso);
        return this;
    }

    public void setTipoCurso(TipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Curso profesor(Profesor profesor) {
        this.setProfesor(profesor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Curso)) {
            return false;
        }
        return id != null && id.equals(((Curso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", cursoName='" + getCursoName() + "'" +
            ", tipoCurso='" + getTipoCurso() + "'" +
            "}";
    }
}
