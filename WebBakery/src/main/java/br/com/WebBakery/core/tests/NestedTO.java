package br.com.WebBakery.core.tests;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class NestedTO extends AbstractBaseTO {

    @TOEntity(fieldName="nome")
    private String nome;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
