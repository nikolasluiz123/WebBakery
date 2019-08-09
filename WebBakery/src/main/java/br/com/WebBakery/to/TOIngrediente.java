package br.com.WebBakery.to;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.enums.UnidadeMedida;

public class TOIngrediente extends AbstractBaseTO {

    private String nome;
    private UnidadeMedida unidadeMedida;
    private List<TOFotoIngrediente> toFotos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public List<TOFotoIngrediente> getToFotos() {
        return toFotos;
    }

    public void setToFotos(List<TOFotoIngrediente> toFotos) {
        this.toFotos = toFotos;
    }

}
