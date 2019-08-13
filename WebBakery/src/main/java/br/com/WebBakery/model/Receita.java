package br.com.WebBakery.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Receita.TABLE_NAME)
public class Receita extends AbstractBaseModel {
    
    static final String TABLE_NAME = "receita";
    public static final String FK_NAME = "id_receita";

    private static final String TEMPO_PREPARO_RECEITA = "tempo_preparo" + "_" + Receita.TABLE_NAME;
    private static final String QUANTIDADE_RECEITA = "quantidade" + "_" + Receita.TABLE_NAME;
    private static final String NOME_RECEITA = "nome" + "_" + Receita.TABLE_NAME;

    @Column(length = STRING_LENGTH_32C, name = NOME_RECEITA)
    private String nome;

    @Column(name = QUANTIDADE_RECEITA)
    private Integer quantidade;

    @Column(name = TEMPO_PREPARO_RECEITA)
    private Time tempoPreparo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(Time tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
