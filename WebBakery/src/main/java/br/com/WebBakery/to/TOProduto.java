package br.com.WebBakery.to;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOProduto extends AbstractBaseTO {

    private String descricao;
    private Integer tempoValido;
    private Double preco;
    private TOReceita toReceita;
    private List<TOFotoProduto> toFotos;

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
}
