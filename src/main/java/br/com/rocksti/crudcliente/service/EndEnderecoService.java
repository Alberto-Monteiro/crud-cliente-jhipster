package br.com.rocksti.crudcliente.service;

import br.com.rocksti.crudcliente.domain.EndEndereco;
import br.com.rocksti.crudcliente.repository.EndEnderecoRepository;
import br.com.rocksti.crudcliente.service.dto.EndEnderecoDTO;
import br.com.rocksti.crudcliente.service.mapper.EndEnderecoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EndEndereco}.
 */
@Service
@Transactional
public class EndEnderecoService {

    private final Logger log = LoggerFactory.getLogger(EndEnderecoService.class);

    private final EndEnderecoRepository endEnderecoRepository;

    private final EndEnderecoMapper endEnderecoMapper;

    public EndEnderecoService(EndEnderecoRepository endEnderecoRepository, EndEnderecoMapper endEnderecoMapper) {
        this.endEnderecoRepository = endEnderecoRepository;
        this.endEnderecoMapper = endEnderecoMapper;
    }

    /**
     * Save a endEndereco.
     *
     * @param endEnderecoDTO the entity to save.
     * @return the persisted entity.
     */
    public EndEnderecoDTO save(EndEnderecoDTO endEnderecoDTO) {
        log.debug("Request to save EndEndereco : {}", endEnderecoDTO);
        EndEndereco endEndereco = endEnderecoMapper.toEntity(endEnderecoDTO);
        endEndereco = endEnderecoRepository.save(endEndereco);
        return endEnderecoMapper.toDto(endEndereco);
    }

    /**
     * Partially update a endEndereco.
     *
     * @param endEnderecoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EndEnderecoDTO> partialUpdate(EndEnderecoDTO endEnderecoDTO) {
        log.debug("Request to partially update EndEndereco : {}", endEnderecoDTO);

        return endEnderecoRepository
            .findById(endEnderecoDTO.getId())
            .map(
                existingEndEndereco -> {
                    endEnderecoMapper.partialUpdate(existingEndEndereco, endEnderecoDTO);
                    return existingEndEndereco;
                }
            )
            .map(endEnderecoRepository::save)
            .map(endEnderecoMapper::toDto);
    }

    /**
     * Get all the endEnderecos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EndEnderecoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EndEnderecos");
        return endEnderecoRepository.findAll(pageable).map(endEnderecoMapper::toDto);
    }

    /**
     * Get one endEndereco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EndEnderecoDTO> findOne(Long id) {
        log.debug("Request to get EndEndereco : {}", id);
        return endEnderecoRepository.findById(id).map(endEnderecoMapper::toDto);
    }

    /**
     * Delete the endEndereco by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EndEndereco : {}", id);
        endEnderecoRepository.deleteById(id);
    }
}
