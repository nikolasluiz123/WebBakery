package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractArquivoTO;

public class TOFotoProduto extends AbstractArquivoTO {

    private TOProduto toProduto;

    public TOProduto getToProduto() {
        return toProduto;
    }

    public void setToProduto(TOProduto toProduto) {
        this.toProduto = toProduto;
    }

}
