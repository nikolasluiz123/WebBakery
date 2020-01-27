package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOIngrediente;
import br.com.WebBakery.util.StringUtil;

public class IngredienteValidator extends AbstractValidator {

    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";
    private static final String FIELD_NOME_LIMIT_EXCEEDED = "Nome com excedência de caractéres!";
    
    private TOIngrediente ingrediente;

    public IngredienteValidator(TOIngrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public void chamarValidacoes() {
        validarNome();
    }

    private void validarNome() {
        String nome = this.ingrediente.getNome();

        if (StringUtil.isNullOrEmpty(nome)) {
            messages.add(FIELD_NOME_REQUIRED);
        } else if (nome.length() > 32) {
            messages.add(FIELD_NOME_LIMIT_EXCEEDED);
        }
    }
}
