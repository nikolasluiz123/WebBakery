package br.com.WebBakery.to;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOIngredienteComFotos extends AbstractBaseTO {

    private TOIngrediente toIngrediente;
    private List<TOFotoIngrediente> toFotos;

    public TOIngrediente getToIngrediente() {
        return toIngrediente;
    }

    public void setToIngrediente(TOIngrediente toIngrediente) {
        this.toIngrediente = toIngrediente;
    }

    public List<TOFotoIngrediente> getToFotos() {
        return toFotos;
    }

    public void setToFotos(List<TOFotoIngrediente> toFotos) {
        this.toFotos = toFotos;
    }

}
