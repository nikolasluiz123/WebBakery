package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Receita;

public class ReceitaValidator extends AbstractValidator {

    private static final String FIELD_QUANTIDADE_NOT_VALID = "Quantidade inválida!";
    private static final String FIELD_DESCRICAO_LIMIT_EXCEEDED = "Descrição com excedência de caractéres!";
    private static final String FIELD_DESCRICAO_REQUIRES = "Descrição é obrigatória!";

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
