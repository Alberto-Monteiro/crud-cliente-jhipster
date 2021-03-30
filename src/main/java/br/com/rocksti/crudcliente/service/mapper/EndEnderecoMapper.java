package br.com.rocksti.crudcliente.service.mapper;

import br.com.rocksti.crudcliente.domain.*;
import br.com.rocksti.crudcliente.service.dto.EndEnderecoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EndEndereco} and its DTO {@link EndEnderecoDTO}.
 */
@Mapper(componentModel = "spring", uses = { EndEstadoMapper.class, ClienteMapper.class })
public interface EndEnderecoMapper extends EntityMapper<EndEnderecoDTO, EndEndereco> {
    @Mapping(target = "estado", source = "estado", qualifiedByName = "uf")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "id")
    EndEnderecoDTO toDto(EndEndereco s);
}
