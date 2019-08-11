package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractArquivoTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOFotoIngrediente extends AbstractArquivoTO {

    @TOEntity(fieldName = "ingrediente")
    private TOIngrediente toIngrediente;

    public TOIngrediente getToIngrediente() {
        return toIngrediente;
    }

    public void setToIngrediente(TOIngrediente toIngrediente) {
        this.toIngrediente = toIngrediente;
    }

}
