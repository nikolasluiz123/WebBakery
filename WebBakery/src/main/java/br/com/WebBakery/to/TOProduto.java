package br.com.WebBakery.to;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;
import br.com.WebBakery.util.String_Util;

public class TOProduto extends AbstractBaseTO {

    @TOEntity(fieldName = "descricao")
    private String descricao;
    @TOEntity(fieldName = "tempoValido")
    private Integer tempoValido;
    @TOEntity(fieldName = "preco")
    private Double preco;
    @TOEntity(fieldName = "receita")
    private TOReceita toReceita;
    private List<TOFotoProduto> toFotos;
    private String precoFormatado;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTempoValido() {
        return tempoValido;
    }

    public void setTempoValido(Integer tempoValido) {
        this.tempoValido = tempoValido;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public TOReceita getToReceita() {
        return toReceita;
    }

    public void setToReceita(TOReceita toReceita) {
        this.toReceita = toReceita;
    }

    public List<TOFotoProduto> getToFotos() {
        return toFotos;
    }

    public void setToFotos(List<TOFotoProduto> toFotos) {
        this.toFotos = toFotos;
    }

    @Override
    public boolean equals(Object obj) {
        TOProduto p = (TOProduto) obj;
        return p.getDescricao().equals(this.descricao);
    }

    public String getPrecoFormatado() {
        return String_Util.formatToMonetaryValue(preco);
    }

}
