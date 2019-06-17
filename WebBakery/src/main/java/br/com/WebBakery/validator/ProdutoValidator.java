package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.model.Receita;

public class ProdutoValidator extends AbstractValidator {

    private static final String FIELD_PRECO_NOT_VALID = "Pre�o inv�lido!";
    private static final String FIELD_TEMPO_VALIDO_NOT_VALID = "Tempo de validade inv�lido!";
    private static final String FIELD_TEMPO_VALIDO_REQUIRED = "Tempo de validade � obrigat�rio!";
    private static final String FIELD_DESCRICAO_LIMIT_EXCEEDED = "Descri��o com exced�ncia de caract�res!";
    private static final String FIELD_DESCRICAO_REQUIRED = "Descri��o � obrigat�ria!";
    private static final String FIELD_PRECO_REQUIRED = "Pre�o � obrigat�rio!";
    private static final String FIELD_RECEITA_REQUIRED = "Receita � obrigat�ria!";

    private Produto produto;

    public ProdutoValidator(Produto produto) {
        this.produto = produto;
    }

    @Override
    public void chamarValidacoes() {
        validaDescricao();
        validaTempoValidade();
        validaPreco();
        validaReceita();
    }

    private void validaDescricao() {
        String descricao = this.produto.getDescricao().trim();

        if (descricao == null || descricao.isEmpty()) {
            messages.add(FIELD_DESCRICAO_REQUIRED);
        } else if (descricao.length() > 50) {
            messages.add(FIELD_DESCRICAO_LIMIT_EXCEEDED);
        }
        this.produto.setDescricao(descricao);
    }

    private void validaTempoValidade() {
        Integer tempoValido = this.produto.getTempoValido();

        if (tempoValido == null) {
            messages.add(FIELD_TEMPO_VALIDO_REQUIRED);
        } else if (tempoValido == 0) {
            messages.add(FIELD_TEMPO_VALIDO_NOT_VALID);
        }
    }

    private void validaPreco() {
        Double preco = this.produto.getPreco();

        if (preco == null) {
            messages.add(FIELD_PRECO_REQUIRED);
        } else if (preco <= 0) {
            messages.add(FIELD_PRECO_NOT_VALID);
        }

    }

    private void validaReceita() {
        Receita receita = this.produto.getReceita();

        if (receita == null) {
            messages.add(FIELD_RECEITA_REQUIRED);
        }
    }
}
