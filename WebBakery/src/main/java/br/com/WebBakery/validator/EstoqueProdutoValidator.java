package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.EstoqueProduto;

public class EstoqueProdutoValidator extends AbstractValidator {

    private EstoqueProduto estoqueProduto;

    public EstoqueProdutoValidator(EstoqueProduto estoqueProduto) {
        this.estoqueProduto = estoqueProduto;
    }

    @Override
    public void chamarValidacoes() {
    }
}
