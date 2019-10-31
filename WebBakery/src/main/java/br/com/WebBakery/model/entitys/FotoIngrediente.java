package br.com.WebBakery.model.entitys;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.WebBakery.abstractClass.AbstractArquivo;

@Entity(name = FotoIngrediente.TABLE_NAME)
public class FotoIngrediente extends AbstractArquivo {

    static final String TABLE_NAME = "foto_ingrediente";
    public static final String FK_NAME = "id_foto_ingrediente";

    private static final String FK_INGREDIENTE_FOTO_INGREDIENTE = Ingrediente.FK_NAME + "_" + FotoIngrediente.TABLE_NAME;

    @ManyToOne
    @JoinColumn(name = FK_INGREDIENTE_FOTO_INGREDIENTE)
    private Ingrediente ingrediente;

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

}
