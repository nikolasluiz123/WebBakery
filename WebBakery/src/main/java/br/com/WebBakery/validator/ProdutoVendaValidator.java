package br.com.WebBakery.validator;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.EstoqueProduto;

public class ProdutoVendaValidator extends AbstractValidator {

    private static final String FIELD_PRODUTO_REQUIRED = "Uma venda deve conter ao menos um produto!";
    
    private List<EstoqueProduto> estoqueProdutos;

    public ProdutoVendaValidator(List<EstoqueProduto> estoqueProdutos) {
        this.estoqueProdutos = estoqueProdutos;
    }

    @Override
    public void chamarValidacoes() {
        validarProdutos();
    }

    private void validarProdutos() {
        if (this.estoqueProdutos == null || this.estoqueProdutos.isEmpty()) {
            messages.add(FIELD_PRODUTO_REQUIRED);
        }
    }
}
