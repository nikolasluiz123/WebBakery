package br.com.WebBakery.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Endereco.TABLE_NAME)
public class Endereco extends AbstractBaseModel {

    private static final String FK_LOGRADOURO_ENDERECO = Logradouro.FK_NAME + "_" + Endereco.TABLE_NAME;
    private static final String FK_CIDADE_ENDERECO = Cidade.FK_NAME + "_" + Endereco.TABLE_NAME;
    private static final String FK_ESTADO_ENDERECO = Estado.FK_NAME + "_" + Endereco.TABLE_NAME;
    private static final String FK_PAIS_ENDERECO = Pais.FK_NAME + "_" + Endereco.TABLE_NAME;

    public static final String TABLE_NAME = "enderecos";
    public static final String FK_NAME = "fk_endereco";

    @OneToOne
    @JoinColumn(name = FK_PAIS_ENDERECO)
    private Pais pais;

    @OneToOne
    @JoinColumn(name = FK_ESTADO_ENDERECO)
    private Estado estado;

    @OneToOne
    @JoinColumn(name = FK_CIDADE_ENDERECO)
    private Cidade cidade;

    @OneToOne
    @JoinColumn(name = FK_LOGRADOURO_ENDERECO)
    private Logradouro logradouro;

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

}
