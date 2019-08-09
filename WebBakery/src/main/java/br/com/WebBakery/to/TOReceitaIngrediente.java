package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.enums.UnidadeMedida;

public class TOReceitaIngrediente extends AbstractBaseTO {

    private TOReceita toReceita;
    private TOIngrediente toIngrediente;
    private Double quantidadeIngrediente;
    private UnidadeMedida unidadeMedida;

    public TOReceita getToReceita() {
        return toReceita;
    }

    public void setToReceita(TOReceita toReceita) {
        this.toReceita = toReceita;
    }

    public TOIngrediente getToIngrediente() {
        return toIngrediente;
    }

    public void setToIngrediente(TOIngrediente toIngrediente) {
        this.toIngrediente = toIngrediente;
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
