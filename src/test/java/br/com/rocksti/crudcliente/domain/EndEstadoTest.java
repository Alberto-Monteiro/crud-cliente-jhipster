package br.com.rocksti.crudcliente.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rocksti.crudcliente.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EndEstadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndEstado.class);
        EndEstado endEstado1 = new EndEstado();
        endEstado1.setId(1L);
        EndEstado endEstado2 = new EndEstado();
        endEstado2.setId(endEstado1.getId());
        assertThat(endEstado1).isEqualTo(endEstado2);
        endEstado2.setId(2L);
        assertThat(endEstado1).isNotEqualTo(endEstado2);
        endEstado1.setId(null);
        assertThat(endEstado1).isNotEqualTo(endEstado2);
    }
}
