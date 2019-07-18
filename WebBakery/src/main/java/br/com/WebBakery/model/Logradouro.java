package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity
public class Logradouro extends AbstractBaseModel {

    @ManyToOne
    private Cidade cidade;

    @Column(length = 40)
    private String bairro;

    @Column(length = 12)
    private String cep;

    @Column(length = 30)
    private String rua;

    @Column(length = 20)
    private String complemento;

    private boolean ativo;

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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
