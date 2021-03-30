package br.com.rocksti.crudcliente.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.rocksti.crudcliente.domain.EndEndereco} entity.
 */
public class EndEnderecoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nomeParaOEndereco;

    @NotNull
    @Size(min = 8, max = 8)
    private String cep;

    @NotNull
    @Size(max = 100)
    private String cidade;

    @Size(max = 100)
    private String bairro;

    @NotNull
    @Size(max = 256)
    private String logradouro;

    @Size(max = 30)
    private String numero;

    @Size(max = 100)
    private String complemento;

    @Size(max = 100)
    private String referencia;

    private EndEstadoDTO estado;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeParaOEndereco() {
        return nomeParaOEndereco;
    }

    public void setNomeParaOEndereco(String nomeParaOEndereco) {
        this.nomeParaOEndereco = nomeParaOEndereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public EndEstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(EndEstadoDTO estado) {
        this.estado = estado;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EndEnderecoDTO)) {
            return false;
        }

        EndEnderecoDTO endEnderecoDTO = (EndEnderecoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, endEnderecoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EndEnderecoDTO{" +
            "id=" + getId() +
            ", nomeParaOEndereco='" + getNomeParaOEndereco() + "'" +
            ", cep='" + getCep() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", referencia='" + getReferencia() + "'" +
            ", estado=" + getEstado() +
            ", cliente=" + getCliente() +
            "}";
    }
}
