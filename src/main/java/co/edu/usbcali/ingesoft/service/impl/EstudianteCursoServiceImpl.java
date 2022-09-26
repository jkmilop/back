package co.edu.usbcali.ingesoft.service.impl;

import co.edu.usbcali.ingesoft.domain.EstudianteCurso;
import co.edu.usbcali.ingesoft.repository.EstudianteCursoRepository;
import co.edu.usbcali.ingesoft.service.EstudianteCursoService;
import co.edu.usbcali.ingesoft.service.dto.EstudianteCursoDTO;
import co.edu.usbcali.ingesoft.service.mapper.EstudianteCursoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstudianteCurso}.
 */
@Service
@Transactional
public class EstudianteCursoServiceImpl implements EstudianteCursoService {

    private final Logger log = LoggerFactory.getLogger(EstudianteCursoServiceImpl.class);

    private final EstudianteCursoRepository estudianteCursoRepository;

    private final EstudianteCursoMapper estudianteCursoMapper;

    public EstudianteCursoServiceImpl(EstudianteCursoRepository estudianteCursoRepository, EstudianteCursoMapper estudianteCursoMapper) {
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.estudianteCursoMapper = estudianteCursoMapper;
    }

    @Override
    public EstudianteCursoDTO save(EstudianteCursoDTO estudianteCursoDTO) {
        log.debug("Request to save EstudianteCurso : {}", estudianteCursoDTO);
        EstudianteCurso estudianteCurso = estudianteCursoMapper.toEntity(estudianteCursoDTO);
        estudianteCurso = estudianteCursoRepository.save(estudianteCurso);
        return estudianteCursoMapper.toDto(estudianteCurso);
    }

    @Override
    public EstudianteCursoDTO update(EstudianteCursoDTO estudianteCursoDTO) {
        log.debug("Request to update EstudianteCurso : {}", estudianteCursoDTO);
        EstudianteCurso estudianteCurso = estudianteCursoMapper.toEntity(estudianteCursoDTO);
        estudianteCurso = estudianteCursoRepository.save(estudianteCurso);
        return estudianteCursoMapper.toDto(estudianteCurso);
    }

    @Override
    public Optional<EstudianteCursoDTO> partialUpdate(EstudianteCursoDTO estudianteCursoDTO) {
        log.debug("Request to partially update EstudianteCurso : {}", estudianteCursoDTO);

        return estudianteCursoRepository
            .findById(estudianteCursoDTO.getId())
            .map(existingEstudianteCurso -> {
                estudianteCursoMapper.partialUpdate(existingEstudianteCurso, estudianteCursoDTO);

                return existingEstudianteCurso;
            })
            .map(estudianteCursoRepository::save)
            .map(estudianteCursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteCursoDTO> findAll() {
        log.debug("Request to get all EstudianteCursos");
        return estudianteCursoRepository
            .findAll()
            .stream()
            .map(estudianteCursoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstudianteCursoDTO> findOne(Long id) {
        log.debug("Request to get EstudianteCurso : {}", id);
        return estudianteCursoRepository.findById(id).map(estudianteCursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstudianteCurso : {}", id);
        estudianteCursoRepository.deleteById(id);
    }
}
