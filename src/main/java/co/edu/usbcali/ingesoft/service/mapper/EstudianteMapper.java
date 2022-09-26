package co.edu.usbcali.ingesoft.service.mapper;

import co.edu.usbcali.ingesoft.domain.Estudiante;
import co.edu.usbcali.ingesoft.service.dto.EstudianteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estudiante} and its DTO {@link EstudianteDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstudianteMapper extends EntityMapper<EstudianteDTO, Estudiante> {}
