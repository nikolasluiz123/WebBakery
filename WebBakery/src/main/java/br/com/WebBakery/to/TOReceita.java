package br.com.WebBakery.to;

import java.sql.Time;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOReceita extends AbstractBaseTO {

    @TOEntity(fieldName = "nome")
    private String nome;
    @TOEntity(fieldName = "quantidade")
    private Integer quantidade;
    @TOEntity(fieldName = "tempoPreparo")
    private Time tempoPreparo;

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

    public Time getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(Time tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

}
