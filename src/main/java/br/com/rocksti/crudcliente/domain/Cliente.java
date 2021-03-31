package br.com.rocksti.crudcliente.domain;

import br.com.rocksti.crudcliente.domain.enumeration.Sexo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Size(max = 30)
    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 60)
    @Column(name = "sobrenome", length = 60, nullable = false)
    private String sobrenome;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Size(max = 20)
    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;

    @NotNull
    @Size(max = 100)
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estado", "cliente" }, allowSetters = true)
    private Set<EndEndereco> enderecos = new HashSet<>();

    @Transient
    private Integer idade;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente id(Long id) {
        this.id = id;
        return this;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Cliente cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public Cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public Cliente sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Cliente dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Cliente telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexo getSexo() {
        return this.sexo;
    }

    public Cliente sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Set<EndEndereco> getEnderecos() {
        return this.enderecos;
    }

    public Cliente enderecos(Set<EndEndereco> endEnderecos) {
        this.setEnderecos(endEnderecos);
        return this;
    }

    public Cliente addEndereco(EndEndereco endEndereco) {
        this.enderecos.add(endEndereco);
        endEndereco.setCliente(this);
        return this;
    }

    public Cliente removeEndereco(EndEndereco endEndereco) {
        this.enderecos.remove(endEndereco);
        endEndereco.setCliente(null);
        return this;
    }

    public void setEnderecos(Set<EndEndereco> endEnderecos) {
        if (this.enderecos != null) {
            this.enderecos.forEach(i -> i.setCliente(null));
        }
        if (endEnderecos != null) {
            endEnderecos.forEach(i -> i.setCliente(this));
        }
        this.enderecos = endEnderecos;
    }

    public Integer getIdade() {
        return Optional
            .ofNullable(dataNascimento)
            .map(dataNascimento -> Period.between(dataNascimento, LocalDate.now()).getYears())
            .orElseGet(() -> 0);
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", cpf='" + getCpf() + "'" +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", idade=" + getIdade() +
            "}";
    }
}
