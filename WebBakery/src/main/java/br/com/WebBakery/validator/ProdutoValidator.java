package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.util.String_Util;

public class ProdutoValidator extends AbstractValidator {

    private static final String FIELD_PRECO_NOT_VALID = "Pre�o inv�lido!";
    private static final String FIELD_TEMPO_VALIDO_NOT_VALID = "Tempo de validade inv�lido!";
    private static final String FIELD_TEMPO_VALIDO_REQUIRED = "Tempo de validade � obrigat�rio!";
    private static final String FIELD_DESCRICAO_LIMIT_EXCEEDED = "Descri��o com exced�ncia de caract�res!";
    private static final String FIELD_DESCRICAO_REQUIRED = "Descri��o � obrigat�ria!";
    private static final String FIELD_PRECO_REQUIRED = "Pre�o � obrigat�rio!";
    private static final String FIELD_RECEITA_REQUIRED = "TOReceita � obrigat�ria!";

    private TOProduto toProduto;

    public ProdutoValidator(TOProduto toProduto) {
        this.toProduto = toProduto;
    }

    @Override
    public void chamarValidacoes() {
        validaDescricao();
        validaTempoValidade();
        validaPreco();
        validaReceita();
    }

    private void validaDescricao() {
        String descricao = this.toProduto.getDescricao().trim();

        if (String_Util.isNullOrEmpty(descricao)) {
            messages.add(FIELD_DESCRICAO_REQUIRED);
        } else if (descricao.length() > 50) {
            messages.add(FIELD_DESCRICAO_LIMIT_EXCEEDED);
        }
        this.toProduto.setDescricao(descricao);
    }

    private void validaTempoValidade() {
        Integer tempoValido = this.toProduto.getTempoValido();

        if (tempoValido == null) {
            messages.add(FIELD_TEMPO_VALIDO_REQUIRED);
        } else if (tempoValido == 0) {
            messages.add(FIELD_TEMPO_VALIDO_NOT_VALID);
        }
    }

    private void validaPreco() {
        Double preco = this.toProduto.getPreco();

        if (preco == null) {
            messages.add(FIELD_PRECO_REQUIRED);
        } else if (preco <= 0) {
            messages.add(FIELD_PRECO_NOT_VALID);
        }

    }

    private void validaReceita() {
        TOReceita toReceita = this.toProduto.getToReceita();

        if (toReceita == null) {
            messages.add(FIELD_RECEITA_REQUIRED);
        }
    }
}
