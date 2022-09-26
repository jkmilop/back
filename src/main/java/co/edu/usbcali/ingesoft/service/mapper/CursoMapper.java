package co.edu.usbcali.ingesoft.service.mapper;

import co.edu.usbcali.ingesoft.domain.Curso;
import co.edu.usbcali.ingesoft.domain.Profesor;
import co.edu.usbcali.ingesoft.service.dto.CursoDTO;
import co.edu.usbcali.ingesoft.service.dto.ProfesorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curso} and its DTO {@link CursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CursoMapper extends EntityMapper<CursoDTO, Curso> {
    @Mapping(target = "profesor", source = "profesor", qualifiedByName = "profesorId")
    CursoDTO toDto(Curso s);

    @Named("profesorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfesorDTO toDtoProfesorId(Profesor profesor);
}
