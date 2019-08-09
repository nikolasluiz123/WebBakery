package br.com.WebBakery.core.tests;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOExample extends AbstractBaseTO {
    
    @TOEntity(fieldName="nome")
    private String nome;
    
    @TOEntity(fieldName="nota")
    private Double nota;
    
    @TOEntity(fieldName="nestedModel")
    private NestedTO nestedTO;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public NestedTO getNestedTO() {
        return nestedTO;
    }

    public void setNestedTO(NestedTO nestedTO) {
        this.nestedTO = nestedTO;
    }
}
