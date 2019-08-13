package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOEstoqueProduto;

public class EstoqueProdutoValidator extends AbstractValidator {

    @SuppressWarnings("unused")
    private TOEstoqueProduto estoqueProduto;

    public EstoqueProdutoValidator(TOEstoqueProduto estoqueProduto) {
        this.estoqueProduto = estoqueProduto;
    }

    @Override
    public void chamarValidacoes() {
    }
}
