package br.com.rocksti.crudcliente.domain.enumeration;

/**
 * The Sexo enumeration.
 */
public enum Sexo {
    MASCULINO("M"),
    FEMININO("F");

    private final String value;

    Sexo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
