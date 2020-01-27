package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;
import br.com.WebBakery.util.StringUtil;

public class TOReceitaIngrediente extends AbstractBaseTO {

    @TOEntity(fieldName = "receita")
    private TOReceita toReceita;
    @TOEntity(fieldName = "ingrediente")
    private TOIngrediente toIngrediente;
    @TOEntity(fieldName = "quantidadeIngrediente")
    private Double quantidadeIngrediente;

    public String getQuantidadeIngredienteFormatada() {
        return StringUtil.formatTwoDecimalPlaces(quantidadeIngrediente);
    }

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

}
