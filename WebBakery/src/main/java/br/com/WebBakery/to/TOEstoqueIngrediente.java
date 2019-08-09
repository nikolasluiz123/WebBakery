package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOEstoqueIngrediente extends AbstractBaseTO {

    private TOIngrediente toIngrediente;
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
