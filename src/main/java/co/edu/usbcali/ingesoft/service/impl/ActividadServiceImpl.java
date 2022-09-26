package co.edu.usbcali.ingesoft.service.impl;

import co.edu.usbcali.ingesoft.domain.Actividad;
import co.edu.usbcali.ingesoft.repository.ActividadRepository;
import co.edu.usbcali.ingesoft.service.ActividadService;
import co.edu.usbcali.ingesoft.service.dto.ActividadDTO;
import co.edu.usbcali.ingesoft.service.mapper.ActividadMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Actividad}.
 */
@Service
@Transactional
public class ActividadServiceImpl implements ActividadService {

    private final Logger log = LoggerFactory.getLogger(ActividadServiceImpl.class);

    private final ActividadRepository actividadRepository;

    private final ActividadMapper actividadMapper;

    public ActividadServiceImpl(ActividadRepository actividadRepository, ActividadMapper actividadMapper) {
        this.actividadRepository = actividadRepository;
        this.actividadMapper = actividadMapper;
    }

    @Override
    public ActividadDTO save(ActividadDTO actividadDTO) {
        log.debug("Request to save Actividad : {}", actividadDTO);
        Actividad actividad = actividadMapper.toEntity(actividadDTO);
        actividad = actividadRepository.save(actividad);
        return actividadMapper.toDto(actividad);
    }

    @Override
    public ActividadDTO update(ActividadDTO actividadDTO) {
        log.debug("Request to update Actividad : {}", actividadDTO);
        Actividad actividad = actividadMapper.toEntity(actividadDTO);
        actividad = actividadRepository.save(actividad);
        return actividadMapper.toDto(actividad);
    }

    @Override
    public Optional<ActividadDTO> partialUpdate(ActividadDTO actividadDTO) {
        log.debug("Request to partially update Actividad : {}", actividadDTO);

        return actividadRepository
            .findById(actividadDTO.getId())
            .map(existingActividad -> {
                actividadMapper.partialUpdate(existingActividad, actividadDTO);

                return existingActividad;
            })
            .map(actividadRepository::save)
            .map(actividadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDTO> findAll() {
        log.debug("Request to get all Actividads");
        return actividadRepository.findAll().stream().map(actividadMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActividadDTO> findOne(Long id) {
        log.debug("Request to get Actividad : {}", id);
        return actividadRepository.findById(id).map(actividadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Actividad : {}", id);
        actividadRepository.deleteById(id);
    }
}
