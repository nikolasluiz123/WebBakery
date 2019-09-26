package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Logradouro.TABLE_NAME)
public class Logradouro extends AbstractBaseModel {

    static final String TABLE_NAME = "logradouro";
    public static final String FK_NAME = "id_logradouro";
    
    private static final String COMPLEMENTO_LOGRADOURO = "complemento" + "_" + Logradouro.TABLE_NAME;
    private static final String RUA_LOGRADOURO = "rua" + "_" + Logradouro.TABLE_NAME;
    private static final String CEP_LOGRADOURO = "cep" + "_" + Logradouro.TABLE_NAME;
    private static final String BAIRRO_LOGRADOURO = "bairro" + "_" + Logradouro.TABLE_NAME;
    private static final String FK_CIDADE_LOGRADOURO = Cidade.FK_NAME + "_" + Logradouro.TABLE_NAME;

    @ManyToOne
    @JoinColumn(name = FK_CIDADE_LOGRADOURO)
    private Cidade cidade;

    @Column(length = STRING_LENGTH_64C, name = BAIRRO_LOGRADOURO)
    private String bairro;

    @Column(length = STRING_LENGTH_16C, name = CEP_LOGRADOURO)
    private String cep;

    @Column(length = STRING_LENGTH_64C, name = RUA_LOGRADOURO)
    private String rua;

    @Column(length = STRING_LENGTH_32C, name = COMPLEMENTO_LOGRADOURO)
    private String complemento;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

}
