package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractArquivoTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOFotoProduto extends AbstractArquivoTO {

    @TOEntity(fieldName = "produto")
    private TOProduto toProduto;

    public TOProduto getToProduto() {
        return toProduto;
    }

    public void setToProduto(TOProduto toProduto) {
        this.toProduto = toProduto;
    }

}
