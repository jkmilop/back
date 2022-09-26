package co.edu.usbcali.ingesoft.service.mapper;

import co.edu.usbcali.ingesoft.domain.Curso;
import co.edu.usbcali.ingesoft.domain.Estudiante;
import co.edu.usbcali.ingesoft.domain.EstudianteCurso;
import co.edu.usbcali.ingesoft.service.dto.CursoDTO;
import co.edu.usbcali.ingesoft.service.dto.EstudianteCursoDTO;
import co.edu.usbcali.ingesoft.service.dto.EstudianteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstudianteCurso} and its DTO {@link EstudianteCursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstudianteCursoMapper extends EntityMapper<EstudianteCursoDTO, EstudianteCurso> {
    @Mapping(target = "curso", source = "curso", qualifiedByName = "cursoId")
    @Mapping(target = "estudiante", source = "estudiante", qualifiedByName = "estudianteId")
    EstudianteCursoDTO toDto(EstudianteCurso s);

    @Named("cursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CursoDTO toDtoCursoId(Curso curso);

    @Named("estudianteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstudianteDTO toDtoEstudianteId(Estudiante estudiante);
}
