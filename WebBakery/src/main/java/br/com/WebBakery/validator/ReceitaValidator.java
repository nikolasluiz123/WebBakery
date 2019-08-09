package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOReceita;

public class ReceitaValidator extends AbstractValidator {

    private TOReceita toReceita;

    public ReceitaValidator(TOReceita toReceita) {
        this.toReceita = toReceita;
    }

    @Override
    public void chamarValidacoes() {

    }

}
