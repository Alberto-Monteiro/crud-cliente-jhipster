package br.com.rocksti.crudcliente.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndEnderecoMapperTest {

    private EndEnderecoMapper endEnderecoMapper;

    @BeforeEach
    public void setUp() {
        endEnderecoMapper = new EndEnderecoMapperImpl();
    }
}
