package br.com.rocksti.crudcliente.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rocksti.crudcliente.IntegrationTest;
import br.com.rocksti.crudcliente.domain.EndEndereco;
import br.com.rocksti.crudcliente.repository.EndEnderecoRepository;
import br.com.rocksti.crudcliente.service.dto.EndEnderecoDTO;
import br.com.rocksti.crudcliente.service.mapper.EndEnderecoMapper;
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
 * Integration tests for the {@link EndEnderecoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EndEnderecoResourceIT {

    private static final String DEFAULT_NOME_PARA_O_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PARA_O_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/end-enderecos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EndEnderecoRepository endEnderecoRepository;

    @Autowired
    private EndEnderecoMapper endEnderecoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEndEnderecoMockMvc;

    private EndEndereco endEndereco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndEndereco createEntity(EntityManager em) {
        EndEndereco endEndereco = new EndEndereco()
            .nomeParaOEndereco(DEFAULT_NOME_PARA_O_ENDERECO)
            .cep(DEFAULT_CEP)
            .cidade(DEFAULT_CIDADE)
            .bairro(DEFAULT_BAIRRO)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .referencia(DEFAULT_REFERENCIA);
        return endEndereco;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndEndereco createUpdatedEntity(EntityManager em) {
        EndEndereco endEndereco = new EndEndereco()
            .nomeParaOEndereco(UPDATED_NOME_PARA_O_ENDERECO)
            .cep(UPDATED_CEP)
            .cidade(UPDATED_CIDADE)
            .bairro(UPDATED_BAIRRO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .referencia(UPDATED_REFERENCIA);
        return endEndereco;
    }

    @BeforeEach
    public void initTest() {
        endEndereco = createEntity(em);
    }

    @Test
    @Transactional
    void createEndEndereco() throws Exception {
        int databaseSizeBeforeCreate = endEnderecoRepository.findAll().size();
        // Create the EndEndereco
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);
        restEndEnderecoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeCreate + 1);
        EndEndereco testEndEndereco = endEnderecoList.get(endEnderecoList.size() - 1);
        assertThat(testEndEndereco.getNomeParaOEndereco()).isEqualTo(DEFAULT_NOME_PARA_O_ENDERECO);
        assertThat(testEndEndereco.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEndEndereco.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEndEndereco.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEndEndereco.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testEndEndereco.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEndEndereco.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEndEndereco.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
    }

    @Test
    @Transactional
    void createEndEnderecoWithExistingId() throws Exception {
        // Create the EndEndereco with an existing ID
        endEndereco.setId(1L);
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        int databaseSizeBeforeCreate = endEnderecoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndEnderecoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeParaOEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = endEnderecoRepository.findAll().size();
        // set the field null
        endEndereco.setNomeParaOEndereco(null);

        // Create the EndEndereco, which fails.
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        restEndEnderecoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = endEnderecoRepository.findAll().size();
        // set the field null
        endEndereco.setCep(null);

        // Create the EndEndereco, which fails.
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        restEndEnderecoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = endEnderecoRepository.findAll().size();
        // set the field null
        endEndereco.setCidade(null);

        // Create the EndEndereco, which fails.
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        restEndEnderecoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = endEnderecoRepository.findAll().size();
        // set the field null
        endEndereco.setLogradouro(null);

        // Create the EndEndereco, which fails.
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        restEndEnderecoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEndEnderecos() throws Exception {
        // Initialize the database
        endEnderecoRepository.saveAndFlush(endEndereco);

        // Get all the endEnderecoList
        restEndEnderecoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endEndereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeParaOEndereco").value(hasItem(DEFAULT_NOME_PARA_O_ENDERECO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)));
    }

    @Test
    @Transactional
    void getEndEndereco() throws Exception {
        // Initialize the database
        endEnderecoRepository.saveAndFlush(endEndereco);

        // Get the endEndereco
        restEndEnderecoMockMvc
            .perform(get(ENTITY_API_URL_ID, endEndereco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endEndereco.getId().intValue()))
            .andExpect(jsonPath("$.nomeParaOEndereco").value(DEFAULT_NOME_PARA_O_ENDERECO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA));
    }

    @Test
    @Transactional
    void getNonExistingEndEndereco() throws Exception {
        // Get the endEndereco
        restEndEnderecoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEndEndereco() throws Exception {
        // Initialize the database
        endEnderecoRepository.saveAndFlush(endEndereco);

        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();

        // Update the endEndereco
        EndEndereco updatedEndEndereco = endEnderecoRepository.findById(endEndereco.getId()).get();
        // Disconnect from session so that the updates on updatedEndEndereco are not directly saved in db
        em.detach(updatedEndEndereco);
        updatedEndEndereco
            .nomeParaOEndereco(UPDATED_NOME_PARA_O_ENDERECO)
            .cep(UPDATED_CEP)
            .cidade(UPDATED_CIDADE)
            .bairro(UPDATED_BAIRRO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .referencia(UPDATED_REFERENCIA);
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(updatedEndEndereco);

        restEndEnderecoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, endEnderecoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
        EndEndereco testEndEndereco = endEnderecoList.get(endEnderecoList.size() - 1);
        assertThat(testEndEndereco.getNomeParaOEndereco()).isEqualTo(UPDATED_NOME_PARA_O_ENDERECO);
        assertThat(testEndEndereco.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEndEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndEndereco.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEndEndereco.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEndEndereco.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEndEndereco.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEndEndereco.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    void putNonExistingEndEndereco() throws Exception {
        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();
        endEndereco.setId(count.incrementAndGet());

        // Create the EndEndereco
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndEnderecoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, endEnderecoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEndEndereco() throws Exception {
        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();
        endEndereco.setId(count.incrementAndGet());

        // Create the EndEndereco
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEnderecoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEndEndereco() throws Exception {
        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();
        endEndereco.setId(count.incrementAndGet());

        // Create the EndEndereco
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEnderecoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEndEnderecoWithPatch() throws Exception {
        // Initialize the database
        endEnderecoRepository.saveAndFlush(endEndereco);

        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();

        // Update the endEndereco using partial update
        EndEndereco partialUpdatedEndEndereco = new EndEndereco();
        partialUpdatedEndEndereco.setId(endEndereco.getId());

        partialUpdatedEndEndereco
            .nomeParaOEndereco(UPDATED_NOME_PARA_O_ENDERECO)
            .cidade(UPDATED_CIDADE)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .referencia(UPDATED_REFERENCIA);

        restEndEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEndEndereco.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEndEndereco))
            )
            .andExpect(status().isOk());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
        EndEndereco testEndEndereco = endEnderecoList.get(endEnderecoList.size() - 1);
        assertThat(testEndEndereco.getNomeParaOEndereco()).isEqualTo(UPDATED_NOME_PARA_O_ENDERECO);
        assertThat(testEndEndereco.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEndEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndEndereco.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEndEndereco.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEndEndereco.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEndEndereco.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEndEndereco.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    void fullUpdateEndEnderecoWithPatch() throws Exception {
        // Initialize the database
        endEnderecoRepository.saveAndFlush(endEndereco);

        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();

        // Update the endEndereco using partial update
        EndEndereco partialUpdatedEndEndereco = new EndEndereco();
        partialUpdatedEndEndereco.setId(endEndereco.getId());

        partialUpdatedEndEndereco
            .nomeParaOEndereco(UPDATED_NOME_PARA_O_ENDERECO)
            .cep(UPDATED_CEP)
            .cidade(UPDATED_CIDADE)
            .bairro(UPDATED_BAIRRO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .referencia(UPDATED_REFERENCIA);

        restEndEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEndEndereco.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEndEndereco))
            )
            .andExpect(status().isOk());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
        EndEndereco testEndEndereco = endEnderecoList.get(endEnderecoList.size() - 1);
        assertThat(testEndEndereco.getNomeParaOEndereco()).isEqualTo(UPDATED_NOME_PARA_O_ENDERECO);
        assertThat(testEndEndereco.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEndEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndEndereco.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEndEndereco.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEndEndereco.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEndEndereco.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEndEndereco.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    void patchNonExistingEndEndereco() throws Exception {
        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();
        endEndereco.setId(count.incrementAndGet());

        // Create the EndEndereco
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, endEnderecoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEndEndereco() throws Exception {
        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();
        endEndereco.setId(count.incrementAndGet());

        // Create the EndEndereco
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEndEndereco() throws Exception {
        int databaseSizeBeforeUpdate = endEnderecoRepository.findAll().size();
        endEndereco.setId(count.incrementAndGet());

        // Create the EndEndereco
        EndEnderecoDTO endEnderecoDTO = endEnderecoMapper.toDto(endEndereco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEndEnderecoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(endEnderecoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EndEndereco in the database
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEndEndereco() throws Exception {
        // Initialize the database
        endEnderecoRepository.saveAndFlush(endEndereco);

        int databaseSizeBeforeDelete = endEnderecoRepository.findAll().size();

        // Delete the endEndereco
        restEndEnderecoMockMvc
            .perform(delete(ENTITY_API_URL_ID, endEndereco.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EndEndereco> endEnderecoList = endEnderecoRepository.findAll();
        assertThat(endEnderecoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
