package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.util.String_Util;

public class CidadeValidator extends AbstractValidator {

    private static final String FIELD_ESTADO_REQUIRED = "Estado � obrigat�rio!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com exced�ncia de caract�res!";
    private static final String FIELD_NOME_REQUIRED = "Nome � obrigat�rio!";

    private TOCidade cidade;

    public CidadeValidator(TOCidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaEstado();
    }

    private void validaNome() {
        String nome = this.cidade.getNome().trim();

        if (String_Util.isNullOrEmpty(nome)) {
            messages.add(FIELD_NOME_REQUIRED);
        }
        if (nome.length() > 32) {
            messages.add(FIELD_NOME_LIMIT_EXCEDDED);
        }
    }

    private void validaEstado() {
        TOEstado estado = this.cidade.getToEstado();

        if (estado == null) {
            messages.add(FIELD_ESTADO_REQUIRED);
        }
    }

}
