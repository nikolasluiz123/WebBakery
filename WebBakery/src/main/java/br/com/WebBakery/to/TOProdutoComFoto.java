package br.com.WebBakery.to;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOProdutoComFoto extends AbstractBaseTO {

    @TOEntity(fieldName = "produto")
    private TOProduto toProduto;
    private List<TOFotoProduto> toFotos;

    public TOProduto getToProduto() {
        return toProduto;
    }

    public void setToProduto(TOProduto toProduto) {
        this.toProduto = toProduto;
    }

    public List<TOFotoProduto> getToFotos() {
        return toFotos;
    }

    public void setToFotos(List<TOFotoProduto> toFotos) {
        this.toFotos = toFotos;
    }

}
