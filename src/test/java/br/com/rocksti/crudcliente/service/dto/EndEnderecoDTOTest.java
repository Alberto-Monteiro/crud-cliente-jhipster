package br.com.rocksti.crudcliente.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rocksti.crudcliente.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EndEnderecoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndEnderecoDTO.class);
        EndEnderecoDTO endEnderecoDTO1 = new EndEnderecoDTO();
        endEnderecoDTO1.setId(1L);
        EndEnderecoDTO endEnderecoDTO2 = new EndEnderecoDTO();
        assertThat(endEnderecoDTO1).isNotEqualTo(endEnderecoDTO2);
        endEnderecoDTO2.setId(endEnderecoDTO1.getId());
        assertThat(endEnderecoDTO1).isEqualTo(endEnderecoDTO2);
        endEnderecoDTO2.setId(2L);
        assertThat(endEnderecoDTO1).isNotEqualTo(endEnderecoDTO2);
        endEnderecoDTO1.setId(null);
        assertThat(endEnderecoDTO1).isNotEqualTo(endEnderecoDTO2);
    }
}
