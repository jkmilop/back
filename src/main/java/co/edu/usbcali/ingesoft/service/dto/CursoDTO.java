package co.edu.usbcali.ingesoft.service.dto;

import co.edu.usbcali.ingesoft.domain.enumeration.TipoCurso;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usbcali.ingesoft.domain.Curso} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CursoDTO implements Serializable {

    private Long id;

    private String cursoName;

    private TipoCurso tipoCurso;

    private ProfesorDTO profesor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCursoName() {
        return cursoName;
    }

    public void setCursoName(String cursoName) {
        this.cursoName = cursoName;
    }

    public TipoCurso getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(TipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public ProfesorDTO getProfesor() {
        return profesor;
    }

    public void setProfesor(ProfesorDTO profesor) {
        this.profesor = profesor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CursoDTO)) {
            return false;
        }

        CursoDTO cursoDTO = (CursoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cursoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CursoDTO{" +
            "id=" + getId() +
            ", cursoName='" + getCursoName() + "'" +
            ", tipoCurso='" + getTipoCurso() + "'" +
            ", profesor=" + getProfesor() +
            "}";
    }
}
