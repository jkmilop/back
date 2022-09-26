package co.edu.usbcali.ingesoft.service.impl;

import co.edu.usbcali.ingesoft.domain.Profesor;
import co.edu.usbcali.ingesoft.repository.ProfesorRepository;
import co.edu.usbcali.ingesoft.service.ProfesorService;
import co.edu.usbcali.ingesoft.service.dto.ProfesorDTO;
import co.edu.usbcali.ingesoft.service.mapper.ProfesorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Profesor}.
 */
@Service
@Transactional
public class ProfesorServiceImpl implements ProfesorService {

    private final Logger log = LoggerFactory.getLogger(ProfesorServiceImpl.class);

    private final ProfesorRepository profesorRepository;

    private final ProfesorMapper profesorMapper;

    public ProfesorServiceImpl(ProfesorRepository profesorRepository, ProfesorMapper profesorMapper) {
        this.profesorRepository = profesorRepository;
        this.profesorMapper = profesorMapper;
    }

    @Override
    public ProfesorDTO save(ProfesorDTO profesorDTO) {
        log.debug("Request to save Profesor : {}", profesorDTO);
        Profesor profesor = profesorMapper.toEntity(profesorDTO);
        profesor = profesorRepository.save(profesor);
        return profesorMapper.toDto(profesor);
    }

    @Override
    public ProfesorDTO update(ProfesorDTO profesorDTO) {
        log.debug("Request to update Profesor : {}", profesorDTO);
        Profesor profesor = profesorMapper.toEntity(profesorDTO);
        profesor = profesorRepository.save(profesor);
        return profesorMapper.toDto(profesor);
    }

    @Override
    public Optional<ProfesorDTO> partialUpdate(ProfesorDTO profesorDTO) {
        log.debug("Request to partially update Profesor : {}", profesorDTO);

        return profesorRepository
            .findById(profesorDTO.getId())
            .map(existingProfesor -> {
                profesorMapper.partialUpdate(existingProfesor, profesorDTO);

                return existingProfesor;
            })
            .map(profesorRepository::save)
            .map(profesorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfesorDTO> findAll() {
        log.debug("Request to get all Profesors");
        return profesorRepository.findAll().stream().map(profesorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProfesorDTO> findOne(Long id) {
        log.debug("Request to get Profesor : {}", id);
        return profesorRepository.findById(id).map(profesorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profesor : {}", id);
        profesorRepository.deleteById(id);
    }
}
