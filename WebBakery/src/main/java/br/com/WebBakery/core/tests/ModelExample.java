package br.com.WebBakery.core.tests;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

public class ModelExample extends AbstractBaseModel {
    
    private String nome;
    private Double nota;
    private NestedModel nestedModel;

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

    public NestedModel getNestedModel() {
        return nestedModel;
    }

    public void setNestedModel(NestedModel nestedModel) {
        this.nestedModel = nestedModel;
    }
}
