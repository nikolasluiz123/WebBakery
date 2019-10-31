package br.com.WebBakery.model.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.WebBakery.abstractClass.AbstractBaseModel;
import br.com.WebBakery.enums.UnidadeMedida;

@Entity(name = Ingrediente.TABLE_NAME)
public class Ingrediente extends AbstractBaseModel {

    static final String TABLE_NAME = "ingrediente";
    public static final String FK_NAME = "id_ingrediente";
    
    private static final String UNIDADE_MEDIDA_INGREDIENTE = "unidade_medida" + "_" + Ingrediente.TABLE_NAME;
    private static final String NOME_INGREDIENTE = "nome" + "_" + Ingrediente.TABLE_NAME;

    @Column(length = STRING_LENGTH_32C, name = NOME_INGREDIENTE)
    private String nome;

    @Column(name = UNIDADE_MEDIDA_INGREDIENTE)
    private UnidadeMedida unidadeMedida;

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

}
