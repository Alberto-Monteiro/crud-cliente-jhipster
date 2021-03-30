package br.com.rocksti.crudcliente.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.rocksti.crudcliente.domain.EndEstado} entity.
 */
public class EndEstadoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 2)
    private String uf;

    @NotNull
    @Size(max = 20)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EndEstadoDTO)) {
            return false;
        }

        EndEstadoDTO endEstadoDTO = (EndEstadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, endEstadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EndEstadoDTO{" +
            "id=" + getId() +
            ", uf='" + getUf() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
