package br.com.WebBakery.model.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = EstoqueProduto.TABLE_NAME)
public class EstoqueProduto extends AbstractBaseModel {

    static final String TABLE_NAME = "estoque_produto";

    private static final String QUANTIDADE_ESTOQUE_PRODUTO = "quantidade" + "_" + EstoqueProduto.TABLE_NAME;
    private static final String FK_PRODUTO_ESTOQUE_PRODUTO = Produto.FK_NAME + "_" + EstoqueProduto.TABLE_NAME;

    @OneToOne
    @JoinColumn(name = FK_PRODUTO_ESTOQUE_PRODUTO)
    private Produto produto;

    @Column(name = QUANTIDADE_ESTOQUE_PRODUTO, precision = 2)
    private Integer quantidade;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
