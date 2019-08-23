package br.com.WebBakery.to;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOReceita extends AbstractBaseTO {

    @TOEntity(fieldName = "nome")
    private String nome;
    @TOEntity(fieldName = "quantidade")
    private Integer quantidade;
    @TOEntity(fieldName = "tempoPreparo")
    private Date tempoPreparo;
    
    public TOReceita() {
        this.quantidade = 1;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(Date tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

}
