package br.com.rocksti.crudcliente.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rocksti.crudcliente.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EndEstadoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndEstadoDTO.class);
        EndEstadoDTO endEstadoDTO1 = new EndEstadoDTO();
        endEstadoDTO1.setId(1L);
        EndEstadoDTO endEstadoDTO2 = new EndEstadoDTO();
        assertThat(endEstadoDTO1).isNotEqualTo(endEstadoDTO2);
        endEstadoDTO2.setId(endEstadoDTO1.getId());
        assertThat(endEstadoDTO1).isEqualTo(endEstadoDTO2);
        endEstadoDTO2.setId(2L);
        assertThat(endEstadoDTO1).isNotEqualTo(endEstadoDTO2);
        endEstadoDTO1.setId(null);
        assertThat(endEstadoDTO1).isNotEqualTo(endEstadoDTO2);
    }
}
