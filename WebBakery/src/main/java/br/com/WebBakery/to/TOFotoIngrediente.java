package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractArquivoTO;

public class TOFotoIngrediente extends AbstractArquivoTO {

    private TOIngrediente toIngrediente;

    public TOIngrediente getToIngrediente() {
        return toIngrediente;
    }

    public void setToIngrediente(TOIngrediente toIngrediente) {
        this.toIngrediente = toIngrediente;
    }

}
