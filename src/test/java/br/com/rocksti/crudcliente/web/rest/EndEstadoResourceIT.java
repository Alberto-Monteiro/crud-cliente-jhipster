package br.com.rocksti.crudcliente.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rocksti.crudcliente.IntegrationTest;
import br.com.rocksti.crudcliente.domain.EndEstado;
import br.com.rocksti.crudcliente.repository.EndEstadoRepository;
import br.com.rocksti.crudcliente.service.dto.EndEstadoDTO;
import br.com.rocksti.crudcliente.service.mapper.EndEstadoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EndEstadoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EndEstadoResourceIT {

    private static final String DEFAULT_UF = "AA";
    private static final String UPDATED_UF = "BB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/end-estados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EndEstadoRepository endEstadoRepository;

    @Autowired
    private EndEstadoMapper endEstadoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEndEstadoMockMvc;

    private EndEstado endEstado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndEstado createEntity(EntityManager em) {
        EndEstado endEstado = new EndEstado().uf(DEFAULT_UF).descricao(DEFAULT_DESCRICAO);
        return endEstado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndEstado createUpdatedEntity(EntityManager em) {
        EndEstado endEstado = new EndEstado().uf(UPDATED_UF).descricao(UPDATED_DESCRICAO);
        return endEstado;
    }

    @BeforeEach
    public void initTest() {
        endEstado = createEntity(em);
    }

    @Test
    @Transactional
    void createEndEstado() throws Exception {
        int databaseSizeBeforeCreate = endEstadoRepository.findAll().size();
        // Create the EndEstado
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);
        restEndEstadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEstadoDTO)))
            .andExpect(status().isCreated());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeCreate + 1);
        EndEstado testEndEstado = endEstadoList.get(endEstadoList.size() - 1);
        assertThat(testEndEstado.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testEndEstado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createEndEstadoWithExistingId() throws Exception {
        // Create the EndEstado with an existing ID
        endEstado.setId(1L);
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        int databaseSizeBeforeCreate = endEstadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndEstadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEstadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUfIsRequired() throws Exception {
        int databaseSizeBeforeTest = endEstadoRepository.findAll().size();
        // set the field null
        endEstado.setUf(null);

        // Create the EndEstado, which fails.
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        restEndEstadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEstadoDTO)))
            .andExpect(status().isBadRequest());

        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = endEstadoRepository.findAll().size();
        // set the field null
        endEstado.setDescricao(null);

        // Create the EndEstado, which fails.
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        restEndEstadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEstadoDTO)))
            .andExpect(status().isBadRequest());

        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEndEstados() throws Exception {
        // Initialize the database
        endEstadoRepository.saveAndFlush(endEstado);

        // Get all the endEstadoList
        restEndEstadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endEstado.getId().intValue())))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getEndEstado() throws Exception {
        // Initialize the database
        endEstadoRepository.saveAndFlush(endEstado);

        // Get the endEstado
        restEndEstadoMockMvc
            .perform(get(ENTITY_API_URL_ID, endEstado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endEstado.getId().intValue()))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingEndEstado() throws Exception {
        // Get the endEstado
        restEndEstadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEndEstado() throws Exception {
        // Initialize the database
        endEstadoRepository.saveAndFlush(endEstado);

        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();

        // Update the endEstado
        EndEstado updatedEndEstado = endEstadoRepository.findById(endEstado.getId()).get();
        // Disconnect from session so that the updates on updatedEndEstado are not directly saved in db
        em.detach(updatedEndEstado);
        updatedEndEstado.uf(UPDATED_UF).descricao(UPDATED_DESCRICAO);
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(updatedEndEstado);

        restEndEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, endEstadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(endEstadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
        EndEstado testEndEstado = endEstadoList.get(endEstadoList.size() - 1);
        assertThat(testEndEstado.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testEndEstado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingEndEstado() throws Exception {
        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();
        endEstado.setId(count.incrementAndGet());

        // Create the EndEstado
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, endEstadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(endEstadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEndEstado() throws Exception {
        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();
        endEstado.setId(count.incrementAndGet());

        // Create the EndEstado
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(endEstadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEndEstado() throws Exception {
        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();
        endEstado.setId(count.incrementAndGet());

        // Create the EndEstado
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEstadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEstadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEndEstadoWithPatch() throws Exception {
        // Initialize the database
        endEstadoRepository.saveAndFlush(endEstado);

        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();

        // Update the endEstado using partial update
        EndEstado partialUpdatedEndEstado = new EndEstado();
        partialUpdatedEndEstado.setId(endEstado.getId());

        restEndEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEndEstado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEndEstado))
            )
            .andExpect(status().isOk());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
        EndEstado testEndEstado = endEstadoList.get(endEstadoList.size() - 1);
        assertThat(testEndEstado.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testEndEstado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateEndEstadoWithPatch() throws Exception {
        // Initialize the database
        endEstadoRepository.saveAndFlush(endEstado);

        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();

        // Update the endEstado using partial update
        EndEstado partialUpdatedEndEstado = new EndEstado();
        partialUpdatedEndEstado.setId(endEstado.getId());

        partialUpdatedEndEstado.uf(UPDATED_UF).descricao(UPDATED_DESCRICAO);

        restEndEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEndEstado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEndEstado))
            )
            .andExpect(status().isOk());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
        EndEstado testEndEstado = endEstadoList.get(endEstadoList.size() - 1);
        assertThat(testEndEstado.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testEndEstado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingEndEstado() throws Exception {
        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();
        endEstado.setId(count.incrementAndGet());

        // Create the EndEstado
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, endEstadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(endEstadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEndEstado() throws Exception {
        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();
        endEstado.setId(count.incrementAndGet());

        // Create the EndEstado
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(endEstadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEndEstado() throws Exception {
        int databaseSizeBeforeUpdate = endEstadoRepository.findAll().size();
        endEstado.setId(count.incrementAndGet());

        // Create the EndEstado
        EndEstadoDTO endEstadoDTO = endEstadoMapper.toDto(endEstado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(endEstadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EndEstado in the database
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEndEstado() throws Exception {
        // Initialize the database
        endEstadoRepository.saveAndFlush(endEstado);

        int databaseSizeBeforeDelete = endEstadoRepository.findAll().size();

        // Delete the endEstado
        restEndEstadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, endEstado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EndEstado> endEstadoList = endEstadoRepository.findAll();
        assertThat(endEstadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
