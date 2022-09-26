package co.edu.usbcali.ingesoft.service.mapper;

import co.edu.usbcali.ingesoft.domain.Actividad;
import co.edu.usbcali.ingesoft.domain.Curso;
import co.edu.usbcali.ingesoft.service.dto.ActividadDTO;
import co.edu.usbcali.ingesoft.service.dto.CursoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Actividad} and its DTO {@link ActividadDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActividadMapper extends EntityMapper<ActividadDTO, Actividad> {
    @Mapping(target = "curso", source = "curso", qualifiedByName = "cursoId")
    ActividadDTO toDto(Actividad s);

    @Named("cursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CursoDTO toDtoCursoId(Curso curso);
}
