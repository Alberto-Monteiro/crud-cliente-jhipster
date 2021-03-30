package br.com.rocksti.crudcliente.repository;

import br.com.rocksti.crudcliente.domain.EndEstado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EndEstado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndEstadoRepository extends JpaRepository<EndEstado, Long> {}
