package br.com.rocksti.crudcliente.service.mapper;

import br.com.rocksti.crudcliente.domain.*;
import br.com.rocksti.crudcliente.service.dto.EndEstadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EndEstado} and its DTO {@link EndEstadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EndEstadoMapper extends EntityMapper<EndEstadoDTO, EndEstado> {
    @Named("uf")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uf", source = "uf")
    EndEstadoDTO toDtoUf(EndEstado endEstado);
}
