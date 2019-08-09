package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOEstado extends AbstractBaseTO {

    private String nome;
    private String sigla;
    private TOPais toPais;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TOPais getToPais() {
        return toPais;
    }

    public void setToPais(TOPais toPais) {
        this.toPais = toPais;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
