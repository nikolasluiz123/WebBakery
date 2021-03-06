package br.com.WebBakery.model.entitys;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.WebBakery.abstractClass.AbstractArquivo;

@Entity(name = FotoProduto.TABLE_NAME)
public class FotoProduto extends AbstractArquivo {

    static final String TABLE_NAME = "foto_produto";
    public static final String FK_NAME = "id_foto_produto";

    private static final String FK_PRODUTO_FOTO_PRODUTO = Produto.FK_NAME + FotoProduto.TABLE_NAME;

    @ManyToOne
    @JoinColumn(name = FK_PRODUTO_FOTO_PRODUTO)
    private Produto produto;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
