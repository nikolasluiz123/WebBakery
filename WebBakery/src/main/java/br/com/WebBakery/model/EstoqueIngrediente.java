package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = EstoqueIngrediente.TABLE_NAME)
public class EstoqueIngrediente extends AbstractBaseModel {

    static final String TABLE_NAME = "estoque_ingrediente";

    private static final String QUANTIDADE_ESTOQUE_INGREDIENTE = "quantidade" + "_" + EstoqueIngrediente.TABLE_NAME;
    private static final String FK_INGREDIENTE_ESTOQUE_INGREDIENTE = Ingrediente.FK_NAME + "_" + EstoqueIngrediente.TABLE_NAME;
    
    @OneToOne
    @JoinColumn(name = FK_INGREDIENTE_ESTOQUE_INGREDIENTE)
    private Ingrediente ingrediente;
    
    @Column(name = QUANTIDADE_ESTOQUE_INGREDIENTE, precision = 2)
    private Double quantidade;

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
