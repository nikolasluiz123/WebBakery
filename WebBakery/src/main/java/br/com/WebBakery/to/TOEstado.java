package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOEstado extends AbstractBaseTO {

    @TOEntity(fieldName = "nome")
    private String nome;
    @TOEntity(fieldName = "sigla")
    private String sigla;
    @TOEntity(fieldName = "pais")
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
