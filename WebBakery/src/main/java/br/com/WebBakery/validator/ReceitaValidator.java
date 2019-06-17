package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Receita;

public class ReceitaValidator extends AbstractValidator {

    private static final String FIELD_QUANTIDADE_NOT_VALID = "Quantidade inv�lida!";
    private static final String FIELD_DESCRICAO_LIMIT_EXCEEDED = "Descri��o com exced�ncia de caract�res!";
    private static final String FIELD_DESCRICAO_REQUIRES = "Descri��o � obrigat�ria!";

    private Receita receita;

    public ReceitaValidator(Receita receita) {
        this.receita = receita;
    }

    @Override
    public void chamarValidacoes() {
        validaDescricao();
        validaQuantidade();
    }

    private void validaDescricao() {
        String descricao = this.receita.getDescricao().trim().replace("\r\n", ", ").replace(".",
                                                                                            "");

        if (descricao == null || descricao.isEmpty()) {
            messages.add(FIELD_DESCRICAO_REQUIRES);
        } else if (descricao.length() > 255) {
            messages.add(FIELD_DESCRICAO_LIMIT_EXCEEDED);
        }

        this.receita.setDescricao(descricao);
    }

    private void validaQuantidade() {
        Integer quantidade = this.receita.getQuantidade();

        if (quantidade == null || quantidade <= 0) {
            messages.add(FIELD_QUANTIDADE_NOT_VALID);
        }
    }
}
