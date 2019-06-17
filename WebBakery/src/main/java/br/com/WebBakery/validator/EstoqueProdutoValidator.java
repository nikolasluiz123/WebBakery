package br.com.WebBakery.validator;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.EstoqueProduto;
import br.com.WebBakery.model.Produto;

public class EstoqueProdutoValidator extends AbstractValidator {

    private EstoqueProduto estoqueProduto;

    public EstoqueProdutoValidator(EstoqueProduto estoqueProduto) {
        this.estoqueProduto = estoqueProduto;
    }

    @Override
    public void chamarValidacoes() {
    }

    public EstoqueProduto existe(List<EstoqueProduto> estoqueProdutos) {
        for (EstoqueProduto estoqueProduto : estoqueProdutos) {

            Produto produtoSendoCadastradoNoEstoque = this.estoqueProduto.getProduto();
            Produto produtoSendoPercorridoNoEstoque = estoqueProduto.getProduto();

            if (produtoSendoCadastradoNoEstoque.equals(produtoSendoPercorridoNoEstoque)) {
                return estoqueProduto;
            }
        }
        return new EstoqueProduto();
    }
}
