package br.com.WebBakery.model.graphics;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ProdutoVendaGraphicValues {
    
    private String nomeProduto;
    private BigInteger quantidadeTotalVendidaProduto;
    private BigDecimal precoTotalVendidoProduto;

    public ProdutoVendaGraphicValues(String nomeProduto,
                                     BigInteger quantidadeTotalVendidaProduto,
                                     BigDecimal precoTotalVendidoProduto) {
        this.nomeProduto = nomeProduto;
        this.quantidadeTotalVendidaProduto = quantidadeTotalVendidaProduto;
        this.precoTotalVendidoProduto = precoTotalVendidoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public BigInteger getQuantidadeTotalVendidaProduto() {
        return quantidadeTotalVendidaProduto;
    }

    public BigDecimal getPrecoTotalVendidoProduto() {
        return precoTotalVendidoProduto;
    }

}
