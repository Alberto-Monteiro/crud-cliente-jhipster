package br.com.rocksti.crudcliente.web.rest;

import br.com.rocksti.crudcliente.repository.EndEstadoRepository;
import br.com.rocksti.crudcliente.service.EndEstadoService;
import br.com.rocksti.crudcliente.service.dto.EndEstadoDTO;
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
 * REST controller for managing {@link br.com.rocksti.crudcliente.domain.EndEstado}.
 */
@RestController
@RequestMapping("/api")
public class EndEstadoResource {

    private final Logger log = LoggerFactory.getLogger(EndEstadoResource.class);

    private static final String ENTITY_NAME = "endEstado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EndEstadoService endEstadoService;

    private final EndEstadoRepository endEstadoRepository;

    public EndEstadoResource(EndEstadoService endEstadoService, EndEstadoRepository endEstadoRepository) {
        this.endEstadoService = endEstadoService;
        this.endEstadoRepository = endEstadoRepository;
    }

    /**
     * {@code POST  /end-estados} : Create a new endEstado.
     *
     * @param endEstadoDTO the endEstadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endEstadoDTO, or with status {@code 400 (Bad Request)} if the endEstado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/end-estados")
    public ResponseEntity<EndEstadoDTO> createEndEstado(@Valid @RequestBody EndEstadoDTO endEstadoDTO) throws URISyntaxException {
        log.debug("REST request to save EndEstado : {}", endEstadoDTO);
        if (endEstadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new endEstado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EndEstadoDTO result = endEstadoService.save(endEstadoDTO);
        return ResponseEntity
            .created(new URI("/api/end-estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /end-estados/:id} : Updates an existing endEstado.
     *
     * @param id the id of the endEstadoDTO to save.
     * @param endEstadoDTO the endEstadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endEstadoDTO,
     * or with status {@code 400 (Bad Request)} if the endEstadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endEstadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/end-estados/{id}")
    public ResponseEntity<EndEstadoDTO> updateEndEstado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EndEstadoDTO endEstadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EndEstado : {}, {}", id, endEstadoDTO);
        if (endEstadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, endEstadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!endEstadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EndEstadoDTO result = endEstadoService.save(endEstadoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endEstadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /end-estados/:id} : Partial updates given fields of an existing endEstado, field will ignore if it is null
     *
     * @param id the id of the endEstadoDTO to save.
     * @param endEstadoDTO the endEstadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endEstadoDTO,
     * or with status {@code 400 (Bad Request)} if the endEstadoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the endEstadoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the endEstadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/end-estados/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EndEstadoDTO> partialUpdateEndEstado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EndEstadoDTO endEstadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EndEstado partially : {}, {}", id, endEstadoDTO);
        if (endEstadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, endEstadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!endEstadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EndEstadoDTO> result = endEstadoService.partialUpdate(endEstadoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endEstadoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /end-estados} : get all the endEstados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of endEstados in body.
     */
    @GetMapping("/end-estados")
    public ResponseEntity<List<EndEstadoDTO>> getAllEndEstados(Pageable pageable) {
        log.debug("REST request to get a page of EndEstados");
        Page<EndEstadoDTO> page = endEstadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /end-estados/:id} : get the "id" endEstado.
     *
     * @param id the id of the endEstadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endEstadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/end-estados/{id}")
    public ResponseEntity<EndEstadoDTO> getEndEstado(@PathVariable Long id) {
        log.debug("REST request to get EndEstado : {}", id);
        Optional<EndEstadoDTO> endEstadoDTO = endEstadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(endEstadoDTO);
    }

    /**
     * {@code DELETE  /end-estados/:id} : delete the "id" endEstado.
     *
     * @param id the id of the endEstadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/end-estados/{id}")
    public ResponseEntity<Void> deleteEndEstado(@PathVariable Long id) {
        log.debug("REST request to delete EndEstado : {}", id);
        endEstadoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
