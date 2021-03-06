package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOLogradouro extends AbstractBaseTO {

    @TOEntity(fieldName = "cidade")
    private TOCidade toCidade;
    @TOEntity(fieldName = "bairro")
    private String bairro;
    @TOEntity(fieldName = "cep")
    private String cep;
    @TOEntity(fieldName = "rua")
    private String rua;
    @TOEntity(fieldName = "complemento")
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

    public TOCidade getToCidade() {
        return toCidade;
    }

    public void setToCidade(TOCidade toCidade) {
        this.toCidade = toCidade;
    }

}
