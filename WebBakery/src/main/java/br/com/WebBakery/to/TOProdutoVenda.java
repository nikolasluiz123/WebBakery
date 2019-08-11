package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOProdutoVenda extends AbstractBaseTO {

    @TOEntity(fieldName = "produti")
    private TOProduto toProduto;
    @TOEntity(fieldName = "venda")
    private TOVenda toVenda;
    @TOEntity(fieldName = "quantidade")
    private Integer quantidade;

    public TOProduto getToProduto() {
        return toProduto;
    }

    public void setToProduto(TOProduto toProduto) {
        this.toProduto = toProduto;
    }

    public TOVenda getToVenda() {
        return toVenda;
    }

    public void setToVenda(TOVenda toVenda) {
        this.toVenda = toVenda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
