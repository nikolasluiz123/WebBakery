package br.com.WebBakery.model.graphics;

import java.math.BigInteger;

public class FuncionarioGraphicValues {

    public FuncionarioGraphicValues(String nomeFuncionario, BigInteger quantidadeVendasRealizadas) {
        this.nomeFuncionario = nomeFuncionario;
        this.quantidadeVendasRealizadas = quantidadeVendasRealizadas;
    }

    private String nomeFuncionario;
    private BigInteger quantidadeVendasRealizadas;

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public BigInteger getQuantidadeVendasRealizadas() {
        return quantidadeVendasRealizadas;
    }

}
