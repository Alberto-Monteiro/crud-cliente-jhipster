package br.com.rocksti.crudcliente.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EndEndereco.
 */
@Entity
@Table(name = "end_endereco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EndEndereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nome_para_o_endereco", length = 100, nullable = false)
    private String nomeParaOEndereco;

    @NotNull
    @Size(min = 8, max = 8)
    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @NotNull
    @Size(max = 100)
    @Column(name = "cidade", length = 100, nullable = false)
    private String cidade;

    @Size(max = 100)
    @Column(name = "bairro", length = 100)
    private String bairro;

    @NotNull
    @Size(max = 256)
    @Column(name = "logradouro", length = 256, nullable = false)
    private String logradouro;

    @Size(max = 30)
    @Column(name = "numero", length = 30)
    private String numero;

    @Size(max = 100)
    @Column(name = "complemento", length = 100)
    private String complemento;

    @Size(max = 100)
    @Column(name = "referencia", length = 100)
    private String referencia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enderecos" }, allowSetters = true)
    private EndEstado estado;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enderecos" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EndEndereco id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeParaOEndereco() {
        return this.nomeParaOEndereco;
    }

    public EndEndereco nomeParaOEndereco(String nomeParaOEndereco) {
        this.nomeParaOEndereco = nomeParaOEndereco;
        return this;
    }

    public void setNomeParaOEndereco(String nomeParaOEndereco) {
        this.nomeParaOEndereco = nomeParaOEndereco;
    }

    public String getCep() {
        return this.cep;
    }

    public EndEndereco cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return this.cidade;
    }

    public EndEndereco cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return this.bairro;
    }

    public EndEndereco bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public EndEndereco logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public EndEndereco numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public EndEndereco complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public EndEndereco referencia(String referencia) {
        this.referencia = referencia;
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public EndEstado getEstado() {
        return this.estado;
    }

    public EndEndereco estado(EndEstado endEstado) {
        this.setEstado(endEstado);
        return this;
    }

    public void setEstado(EndEstado endEstado) {
        this.estado = endEstado;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public EndEndereco cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EndEndereco)) {
            return false;
        }
        return id != null && id.equals(((EndEndereco) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EndEndereco{" +
            "id=" + getId() +
            ", nomeParaOEndereco='" + getNomeParaOEndereco() + "'" +
            ", cep='" + getCep() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", referencia='" + getReferencia() + "'" +
            "}";
    }
}
