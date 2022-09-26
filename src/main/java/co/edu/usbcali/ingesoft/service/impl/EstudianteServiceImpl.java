package co.edu.usbcali.ingesoft.service.impl;

import co.edu.usbcali.ingesoft.domain.Estudiante;
import co.edu.usbcali.ingesoft.repository.EstudianteRepository;
import co.edu.usbcali.ingesoft.service.EstudianteService;
import co.edu.usbcali.ingesoft.service.dto.EstudianteDTO;
import co.edu.usbcali.ingesoft.service.mapper.EstudianteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Estudiante}.
 */
@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final Logger log = LoggerFactory.getLogger(EstudianteServiceImpl.class);

    private final EstudianteRepository estudianteRepository;

    private final EstudianteMapper estudianteMapper;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, EstudianteMapper estudianteMapper) {
        this.estudianteRepository = estudianteRepository;
        this.estudianteMapper = estudianteMapper;
    }

    @Override
    public EstudianteDTO save(EstudianteDTO estudianteDTO) {
        log.debug("Request to save Estudiante : {}", estudianteDTO);
        Estudiante estudiante = estudianteMapper.toEntity(estudianteDTO);
        estudiante = estudianteRepository.save(estudiante);
        return estudianteMapper.toDto(estudiante);
    }

    @Override
    public EstudianteDTO update(EstudianteDTO estudianteDTO) {
        log.debug("Request to update Estudiante : {}", estudianteDTO);
        Estudiante estudiante = estudianteMapper.toEntity(estudianteDTO);
        estudiante = estudianteRepository.save(estudiante);
        return estudianteMapper.toDto(estudiante);
    }

    @Override
    public Optional<EstudianteDTO> partialUpdate(EstudianteDTO estudianteDTO) {
        log.debug("Request to partially update Estudiante : {}", estudianteDTO);

        return estudianteRepository
            .findById(estudianteDTO.getId())
            .map(existingEstudiante -> {
                estudianteMapper.partialUpdate(existingEstudiante, estudianteDTO);

                return existingEstudiante;
            })
            .map(estudianteRepository::save)
            .map(estudianteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteDTO> findAll() {
        log.debug("Request to get all Estudiantes");
        return estudianteRepository.findAll().stream().map(estudianteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstudianteDTO> findOne(Long id) {
        log.debug("Request to get Estudiante : {}", id);
        return estudianteRepository.findById(id).map(estudianteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estudiante : {}", id);
        estudianteRepository.deleteById(id);
    }
}
