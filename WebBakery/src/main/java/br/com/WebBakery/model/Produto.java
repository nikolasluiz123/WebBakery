package br.com.WebBakery.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Produto.TABLE_NAME)
public class Produto extends AbstractBaseModel {
    
    static final String TABLE_NAME = "produtos";
    public static final String FK_NAME = "fk_produto";

    private static final String FK_RECEITA_PRODUTO = Receita.FK_NAME + "_" + Produto.TABLE_NAME;
    private static final String PRECO_PRODUTO = "preco" + "_" + Produto.TABLE_NAME;
    private static final String TEMPO_VALIDO_PRODUTO = "tempo_valido" + "_" + Produto.TABLE_NAME;
    private static final String DESCRICAO_PRODUTO = "descricao" + "_" + Produto.TABLE_NAME;

    @Column(length = STRING_LENGTH_32C, name = DESCRICAO_PRODUTO)
    private String descricao;

    @Column(name = TEMPO_VALIDO_PRODUTO)
    private Integer tempoValido;

    @Column(name = PRECO_PRODUTO, precision = 2)
    private Double preco;

    @OneToOne
    @JoinColumn(name = FK_RECEITA_PRODUTO)
    private Receita receita;

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "produto",
               targetEntity = FotoProduto.class,
               orphanRemoval = false)
    private List<FotoProduto> fotos;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getTempoValido() {
        return tempoValido;
    }

    public void setTempoValido(Integer tempoValido) {
        this.tempoValido = tempoValido;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public List<FotoProduto> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoProduto> fotos) {
        this.fotos = fotos;
    }

    @Override
    public boolean equals(Object obj) {
        Produto p = (Produto) obj;
        return p.getDescricao().equals(this.descricao);
    }
}
