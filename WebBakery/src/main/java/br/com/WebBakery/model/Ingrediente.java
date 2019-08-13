package br.com.WebBakery.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import br.com.WebBakery.abstractClass.AbstractBaseModel;
import br.com.WebBakery.enums.UnidadeMedida;

@Entity(name = Ingrediente.TABLE_NAME)
public class Ingrediente extends AbstractBaseModel {

    static final String TABLE_NAME = "ingredientes";
    public static final String FK_NAME = "fk_ingrediente";
    
    private static final String FK_FOTO_INGREDIENTE_INGREDIENTE = FotoIngrediente.FK_NAME + "_" + Ingrediente.TABLE_NAME;
    private static final String UNIDADE_MEDIDA_INGREDIENTE = "unidade_medida" + "_" + Ingrediente.TABLE_NAME;
    private static final String NOME_INGREDIENTE = "nome" + "_" + Ingrediente.TABLE_NAME;


    @Column(length = STRING_LENGTH_32C, name = NOME_INGREDIENTE)
    private String nome;

    @Column(name = UNIDADE_MEDIDA_INGREDIENTE)
    private UnidadeMedida unidadeMedida;

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "ingrediente",
               targetEntity = FotoIngrediente.class,
               orphanRemoval = false)
    @JoinColumn(name = FK_FOTO_INGREDIENTE_INGREDIENTE)
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
