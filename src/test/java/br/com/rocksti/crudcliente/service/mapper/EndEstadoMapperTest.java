package br.com.rocksti.crudcliente.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndEstadoMapperTest {

    private EndEstadoMapper endEstadoMapper;

    @BeforeEach
    public void setUp() {
        endEstadoMapper = new EndEstadoMapperImpl();
    }
}
