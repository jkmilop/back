package co.edu.usbcali.ingesoft.service.impl;

import co.edu.usbcali.ingesoft.domain.Desarrollo;
import co.edu.usbcali.ingesoft.repository.DesarrolloRepository;
import co.edu.usbcali.ingesoft.service.DesarrolloService;
import co.edu.usbcali.ingesoft.service.dto.DesarrolloDTO;
import co.edu.usbcali.ingesoft.service.mapper.DesarrolloMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Desarrollo}.
 */
@Service
@Transactional
public class DesarrolloServiceImpl implements DesarrolloService {

    private final Logger log = LoggerFactory.getLogger(DesarrolloServiceImpl.class);

    private final DesarrolloRepository desarrolloRepository;

    private final DesarrolloMapper desarrolloMapper;

    public DesarrolloServiceImpl(DesarrolloRepository desarrolloRepository, DesarrolloMapper desarrolloMapper) {
        this.desarrolloRepository = desarrolloRepository;
        this.desarrolloMapper = desarrolloMapper;
    }

    @Override
    public DesarrolloDTO save(DesarrolloDTO desarrolloDTO) {
        log.debug("Request to save Desarrollo : {}", desarrolloDTO);
        Desarrollo desarrollo = desarrolloMapper.toEntity(desarrolloDTO);
        desarrollo = desarrolloRepository.save(desarrollo);
        return desarrolloMapper.toDto(desarrollo);
    }

    @Override
    public DesarrolloDTO update(DesarrolloDTO desarrolloDTO) {
        log.debug("Request to update Desarrollo : {}", desarrolloDTO);
        Desarrollo desarrollo = desarrolloMapper.toEntity(desarrolloDTO);
        desarrollo = desarrolloRepository.save(desarrollo);
        return desarrolloMapper.toDto(desarrollo);
    }

    @Override
    public Optional<DesarrolloDTO> partialUpdate(DesarrolloDTO desarrolloDTO) {
        log.debug("Request to partially update Desarrollo : {}", desarrolloDTO);

        return desarrolloRepository
            .findById(desarrolloDTO.getId())
            .map(existingDesarrollo -> {
                desarrolloMapper.partialUpdate(existingDesarrollo, desarrolloDTO);

                return existingDesarrollo;
            })
            .map(desarrolloRepository::save)
            .map(desarrolloMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesarrolloDTO> findAll() {
        log.debug("Request to get all Desarrollos");
        return desarrolloRepository.findAll().stream().map(desarrolloMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DesarrolloDTO> findOne(Long id) {
        log.debug("Request to get Desarrollo : {}", id);
        return desarrolloRepository.findById(id).map(desarrolloMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Desarrollo : {}", id);
        desarrolloRepository.deleteById(id);
    }
}
