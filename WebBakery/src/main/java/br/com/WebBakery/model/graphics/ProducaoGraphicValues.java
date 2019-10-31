package br.com.WebBakery.model.graphics;

import java.math.BigInteger;

public class ProducaoGraphicValues {

    private String nomeFuncionario;
    private BigInteger quantidadeTarefasConcluidas;
    private BigInteger quantidadeTarefasPendentes;

    public ProducaoGraphicValues(String nomeFuncionario,
                                 BigInteger quantidadeTarefasConcluidas,
                                 BigInteger quantidadeTarefasPendentes) {
        this.nomeFuncionario = nomeFuncionario;
        this.quantidadeTarefasConcluidas = quantidadeTarefasConcluidas;
        this.quantidadeTarefasPendentes = quantidadeTarefasPendentes;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public BigInteger getQuantidadeTarefasConcluidas() {
        return quantidadeTarefasConcluidas;
    }

    public BigInteger getQuantidadeTarefasPendentes() {
        return quantidadeTarefasPendentes;
    }

}
