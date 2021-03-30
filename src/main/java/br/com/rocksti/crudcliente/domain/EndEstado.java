package br.com.rocksti.crudcliente.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EndEstado.
 */
@Entity
@Table(name = "end_estado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EndEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 2)
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @NotNull
    @Size(max = 20)
    @Column(name = "descricao", length = 20, nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estado", "cliente" }, allowSetters = true)
    private Set<EndEndereco> enderecos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EndEstado id(Long id) {
        this.id = id;
        return this;
    }

    public String getUf() {
        return this.uf;
    }

    public EndEstado uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public EndEstado descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<EndEndereco> getEnderecos() {
        return this.enderecos;
    }

    public EndEstado enderecos(Set<EndEndereco> endEnderecos) {
        this.setEnderecos(endEnderecos);
        return this;
    }

    public EndEstado addEndereco(EndEndereco endEndereco) {
        this.enderecos.add(endEndereco);
        endEndereco.setEstado(this);
        return this;
    }

    public EndEstado removeEndereco(EndEndereco endEndereco) {
        this.enderecos.remove(endEndereco);
        endEndereco.setEstado(null);
        return this;
    }

    public void setEnderecos(Set<EndEndereco> endEnderecos) {
        if (this.enderecos != null) {
            this.enderecos.forEach(i -> i.setEstado(null));
        }
        if (endEnderecos != null) {
            endEnderecos.forEach(i -> i.setEstado(this));
        }
        this.enderecos = endEnderecos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EndEstado)) {
            return false;
        }
        return id != null && id.equals(((EndEstado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EndEstado{" +
            "id=" + getId() +
            ", uf='" + getUf() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
