package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOLogradouro extends AbstractBaseTO {

    private TOCidade toCidade;
    private String bairro;
    private String cep;
    private String rua;
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
