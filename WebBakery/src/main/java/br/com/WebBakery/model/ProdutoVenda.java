package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = ProdutoVenda.TABLE_NAME)
public class ProdutoVenda extends AbstractBaseModel {

    static final String TABLE_NAME = "produto_venda";
    
    private static final String QUANTIDADE_PRODUTO_VENDA = "quantidade" + "_" + ProdutoVenda.TABLE_NAME;
    private static final String FK_VENDA_PRODUTO_VENDA = Venda.FK_NAME + "_" + ProdutoVenda.TABLE_NAME;
    private static final String FK_PRODUTO_PRODUTO_VENDA = Produto.FK_NAME + "_" + ProdutoVenda.TABLE_NAME;

    @OneToOne
    @Column(name = FK_PRODUTO_PRODUTO_VENDA)
    private Produto produto;
    
    @OneToOne
    @Column(name = FK_VENDA_PRODUTO_VENDA)
    private Venda venda;

    @Column(name = QUANTIDADE_PRODUTO_VENDA)
    private Integer quantidade;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
