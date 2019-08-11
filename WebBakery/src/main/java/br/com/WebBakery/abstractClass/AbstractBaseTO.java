package br.com.WebBakery.abstractClass;

import br.com.WebBakery.core.annotations.TOEntity;

public class AbstractBaseTO {

    @TOEntity(fieldName = "id")
    private Integer id;

    @TOEntity(fieldName = "ativo")
    private boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
