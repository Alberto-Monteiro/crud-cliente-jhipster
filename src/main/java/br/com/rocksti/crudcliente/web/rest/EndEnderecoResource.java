package br.com.rocksti.crudcliente.web.rest;

import br.com.rocksti.crudcliente.repository.EndEnderecoRepository;
import br.com.rocksti.crudcliente.service.EndEnderecoService;
import br.com.rocksti.crudcliente.service.dto.EndEnderecoDTO;
import br.com.rocksti.crudcliente.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.rocksti.crudcliente.domain.EndEndereco}.
 */
@RestController
@RequestMapping("/api")
public class EndEnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EndEnderecoResource.class);

    private static final String ENTITY_NAME = "endEndereco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EndEnderecoService endEnderecoService;

    private final EndEnderecoRepository endEnderecoRepository;

    public EndEnderecoResource(EndEnderecoService endEnderecoService, EndEnderecoRepository endEnderecoRepository) {
        this.endEnderecoService = endEnderecoService;
        this.endEnderecoRepository = endEnderecoRepository;
    }

    /**
     * {@code POST  /end-enderecos} : Create a new endEndereco.
     *
     * @param endEnderecoDTO the endEnderecoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endEnderecoDTO, or with status {@code 400 (Bad Request)} if the endEndereco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/end-enderecos")
    public ResponseEntity<EndEnderecoDTO> createEndEndereco(@Valid @RequestBody EndEnderecoDTO endEnderecoDTO) throws URISyntaxException {
        log.debug("REST request to save EndEndereco : {}", endEnderecoDTO);
        if (endEnderecoDTO.getId() != null) {
            throw new BadRequestAlertException("A new endEndereco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EndEnderecoDTO result = endEnderecoService.save(endEnderecoDTO);
        return ResponseEntity
            .created(new URI("/api/end-enderecos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /end-enderecos/:id} : Updates an existing endEndereco.
     *
     * @param id the id of the endEnderecoDTO to save.
     * @param endEnderecoDTO the endEnderecoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endEnderecoDTO,
     * or with status {@code 400 (Bad Request)} if the endEnderecoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endEnderecoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/end-enderecos/{id}")
    public ResponseEntity<EndEnderecoDTO> updateEndEndereco(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EndEnderecoDTO endEnderecoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EndEndereco : {}, {}", id, endEnderecoDTO);
        if (endEnderecoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, endEnderecoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!endEnderecoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EndEnderecoDTO result = endEnderecoService.save(endEnderecoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endEnderecoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /end-enderecos/:id} : Partial updates given fields of an existing endEndereco, field will ignore if it is null
     *
     * @param id the id of the endEnderecoDTO to save.
     * @param endEnderecoDTO the endEnderecoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endEnderecoDTO,
     * or with status {@code 400 (Bad Request)} if the endEnderecoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the endEnderecoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the endEnderecoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/end-enderecos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EndEnderecoDTO> partialUpdateEndEndereco(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EndEnderecoDTO endEnderecoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EndEndereco partially : {}, {}", id, endEnderecoDTO);
        if (endEnderecoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, endEnderecoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!endEnderecoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EndEnderecoDTO> result = endEnderecoService.partialUpdate(endEnderecoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endEnderecoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /end-enderecos} : get all the endEnderecos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of endEnderecos in body.
     */
    @GetMapping("/end-enderecos")
    public ResponseEntity<List<EndEnderecoDTO>> getAllEndEnderecos(Pageable pageable) {
        log.debug("REST request to get a page of EndEnderecos");
        Page<EndEnderecoDTO> page = endEnderecoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /end-enderecos/:id} : get the "id" endEndereco.
     *
     * @param id the id of the endEnderecoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endEnderecoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/end-enderecos/{id}")
    public ResponseEntity<EndEnderecoDTO> getEndEndereco(@PathVariable Long id) {
        log.debug("REST request to get EndEndereco : {}", id);
        Optional<EndEnderecoDTO> endEnderecoDTO = endEnderecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(endEnderecoDTO);
    }

    /**
     * {@code DELETE  /end-enderecos/:id} : delete the "id" endEndereco.
     *
     * @param id the id of the endEnderecoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/end-enderecos/{id}")
    public ResponseEntity<Void> deleteEndEndereco(@PathVariable Long id) {
        log.debug("REST request to delete EndEndereco : {}", id);
        endEnderecoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
