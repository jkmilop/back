package co.edu.usbcali.ingesoft.service.mapper;

import co.edu.usbcali.ingesoft.domain.Profesor;
import co.edu.usbcali.ingesoft.service.dto.ProfesorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profesor} and its DTO {@link ProfesorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfesorMapper extends EntityMapper<ProfesorDTO, Profesor> {}
