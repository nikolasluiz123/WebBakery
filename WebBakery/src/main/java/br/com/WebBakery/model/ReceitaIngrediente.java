package br.com.WebBakery.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;
import br.com.WebBakery.enums.UnidadeMedida;

@Entity
public class ReceitaIngrediente extends AbstractBaseModel {

    @OneToOne
    private Receita receita;

    @OneToOne
    private Ingrediente ingrediente;

    private Double quantidadeIngrediente;

    private UnidadeMedida unidadeMedida;

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Double getQuantidadeIngrediente() {
        return quantidadeIngrediente;
    }

    public void setQuantidadeIngrediente(Double quantidadeIngrediente) {
        this.quantidadeIngrediente = quantidadeIngrediente;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

}
