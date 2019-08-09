package br.com.WebBakery.to;

import java.sql.Time;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOReceita extends AbstractBaseTO {

    private String nome;
    private Integer quantidade;
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
