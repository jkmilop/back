package co.edu.usbcali.ingesoft.service.mapper;

import co.edu.usbcali.ingesoft.domain.Actividad;
import co.edu.usbcali.ingesoft.domain.Desarrollo;
import co.edu.usbcali.ingesoft.domain.Estudiante;
import co.edu.usbcali.ingesoft.service.dto.ActividadDTO;
import co.edu.usbcali.ingesoft.service.dto.DesarrolloDTO;
import co.edu.usbcali.ingesoft.service.dto.EstudianteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Desarrollo} and its DTO {@link DesarrolloDTO}.
 */
@Mapper(componentModel = "spring")
public interface DesarrolloMapper extends EntityMapper<DesarrolloDTO, Desarrollo> {
    @Mapping(target = "estudiante", source = "estudiante", qualifiedByName = "estudianteId")
    @Mapping(target = "actividad", source = "actividad", qualifiedByName = "actividadId")
    DesarrolloDTO toDto(Desarrollo s);

    @Named("estudianteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstudianteDTO toDtoEstudianteId(Estudiante estudiante);

    @Named("actividadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ActividadDTO toDtoActividadId(Actividad actividad);
}
