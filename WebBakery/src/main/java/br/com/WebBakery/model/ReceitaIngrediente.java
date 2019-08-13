package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;
import br.com.WebBakery.enums.UnidadeMedida;

@Entity(name = ReceitaIngrediente.TABLE_NAME)
public class ReceitaIngrediente extends AbstractBaseModel {

    public static final String TABLE_NAME = "receita_ingrediente";

    private static final String UNIDADE_MEDIDA_RECEITA_INGREDIENTE = "unidade_medida" + "_" + ReceitaIngrediente.TABLE_NAME;
    private static final String QUANTIDADE_INGREDIENTE_RECEITA_INGREDIENTE = "quantidade_ingrediente" + "_" + ReceitaIngrediente.TABLE_NAME;
    private static final String FK_INGREDIENTE_RECEITA_INGREDIENTE = Ingrediente.FK_NAME + "_" + ReceitaIngrediente.TABLE_NAME;
    private static final String FK_RECEITA_RECEITA_INGREDIENTE = Receita.FK_NAME + "_" + ReceitaIngrediente.TABLE_NAME;

    @OneToOne
    @JoinColumn(name = FK_RECEITA_RECEITA_INGREDIENTE)
    private Receita receita;

    @OneToOne
    @JoinColumn(name = FK_INGREDIENTE_RECEITA_INGREDIENTE)
    private Ingrediente ingrediente;

    @Column(name = QUANTIDADE_INGREDIENTE_RECEITA_INGREDIENTE)
    private Double quantidadeIngrediente;

    @Column(name = UNIDADE_MEDIDA_RECEITA_INGREDIENTE)
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
