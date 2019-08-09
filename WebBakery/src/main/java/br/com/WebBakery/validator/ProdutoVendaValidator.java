package br.com.WebBakery.validator;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOEstoqueProduto;

public class ProdutoVendaValidator extends AbstractValidator {

    private static final String FIELD_PRODUTO_REQUIRED = "Uma venda deve conter ao menos um produto!";
    
    private List<TOEstoqueProduto> toEstoqueProdutos;

    public ProdutoVendaValidator(List<TOEstoqueProduto> toEstoqueProdutosSelecionados) {
        this.toEstoqueProdutos = toEstoqueProdutosSelecionados;
    }

    @Override
    public void chamarValidacoes() {
        validarProdutos();
    }

    private void validarProdutos() {
        if (this.toEstoqueProdutos == null || this.toEstoqueProdutos.isEmpty()) {
            messages.add(FIELD_PRODUTO_REQUIRED);
        }
    }
}
