package br.com.rocksti.crudcliente.repository;

import br.com.rocksti.crudcliente.domain.EndEndereco;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EndEndereco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndEnderecoRepository extends JpaRepository<EndEndereco, Long> {}
