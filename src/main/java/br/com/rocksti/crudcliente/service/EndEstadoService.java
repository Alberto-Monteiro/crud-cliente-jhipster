package br.com.rocksti.crudcliente.service;

import br.com.rocksti.crudcliente.domain.EndEstado;
import br.com.rocksti.crudcliente.repository.EndEstadoRepository;
import br.com.rocksti.crudcliente.service.dto.EndEstadoDTO;
import br.com.rocksti.crudcliente.service.mapper.EndEstadoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EndEstado}.
 */
@Service
@Transactional
public class EndEstadoService {

    private final Logger log = LoggerFactory.getLogger(EndEstadoService.class);

    private final EndEstadoRepository endEstadoRepository;

    private final EndEstadoMapper endEstadoMapper;

    public EndEstadoService(EndEstadoRepository endEstadoRepository, EndEstadoMapper endEstadoMapper) {
        this.endEstadoRepository = endEstadoRepository;
        this.endEstadoMapper = endEstadoMapper;
    }

    /**
     * Save a endEstado.
     *
     * @param endEstadoDTO the entity to save.
     * @return the persisted entity.
     */
    public EndEstadoDTO save(EndEstadoDTO endEstadoDTO) {
        log.debug("Request to save EndEstado : {}", endEstadoDTO);
        EndEstado endEstado = endEstadoMapper.toEntity(endEstadoDTO);
        endEstado = endEstadoRepository.save(endEstado);
        return endEstadoMapper.toDto(endEstado);
    }

    /**
     * Partially update a endEstado.
     *
     * @param endEstadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EndEstadoDTO> partialUpdate(EndEstadoDTO endEstadoDTO) {
        log.debug("Request to partially update EndEstado : {}", endEstadoDTO);

        return endEstadoRepository
            .findById(endEstadoDTO.getId())
            .map(
                existingEndEstado -> {
                    endEstadoMapper.partialUpdate(existingEndEstado, endEstadoDTO);
                    return existingEndEstado;
                }
            )
            .map(endEstadoRepository::save)
            .map(endEstadoMapper::toDto);
    }

    /**
     * Get all the endEstados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EndEstadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EndEstados");
        return endEstadoRepository.findAll(pageable).map(endEstadoMapper::toDto);
    }

    /**
     * Get one endEstado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EndEstadoDTO> findOne(Long id) {
        log.debug("Request to get EndEstado : {}", id);
        return endEstadoRepository.findById(id).map(endEstadoMapper::toDto);
    }

    /**
     * Delete the endEstado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EndEstado : {}", id);
        endEstadoRepository.deleteById(id);
    }
}
