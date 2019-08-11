package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOEstoqueIngrediente extends AbstractBaseTO {

    @TOEntity(fieldName = "ingrediente")
    private TOIngrediente toIngrediente;
    @TOEntity(fieldName = "quantidade")
    private Double quantidade;

    public TOIngrediente getToIngrediente() {
        return toIngrediente;
    }

    public void setToIngrediente(TOIngrediente toIngrediente) {
        this.toIngrediente = toIngrediente;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

}
