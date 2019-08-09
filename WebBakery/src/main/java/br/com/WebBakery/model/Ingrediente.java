package br.com.WebBakery.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import br.com.WebBakery.abstractClass.AbstractBaseModel;
import br.com.WebBakery.enums.UnidadeMedida;

@Entity
public class Ingrediente extends AbstractBaseModel {

    @Column(length = 32)
    private String nome;
    private UnidadeMedida unidadeMedida;
    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "ingrediente",
               targetEntity = FotoIngrediente.class,
               orphanRemoval = false)
    private List<FotoIngrediente> fotos;

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

    public List<FotoIngrediente> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoIngrediente> fotos) {
        this.fotos = fotos;
    }

}
