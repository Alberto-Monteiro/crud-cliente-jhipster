package br.com.rocksti.crudcliente.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rocksti.crudcliente.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EndEnderecoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndEndereco.class);
        EndEndereco endEndereco1 = new EndEndereco();
        endEndereco1.setId(1L);
        EndEndereco endEndereco2 = new EndEndereco();
        endEndereco2.setId(endEndereco1.getId());
        assertThat(endEndereco1).isEqualTo(endEndereco2);
        endEndereco2.setId(2L);
        assertThat(endEndereco1).isNotEqualTo(endEndereco2);
        endEndereco1.setId(null);
        assertThat(endEndereco1).isNotEqualTo(endEndereco2);
    }
}
