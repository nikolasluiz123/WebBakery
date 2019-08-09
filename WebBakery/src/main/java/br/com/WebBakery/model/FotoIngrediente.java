package br.com.WebBakery.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.WebBakery.abstractClass.AbstractArquivo;

@Entity
public class FotoIngrediente extends AbstractArquivo {

    @ManyToOne
    private Ingrediente ingrediente;

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

}
