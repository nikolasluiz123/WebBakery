package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Estado;

public class CidadeValidator extends AbstractValidator {

    private static final String FIELD_ESTADO_REQUIRED = "Estado é obrigatório!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com excedência de caractéres!";
    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";

    private Cidade cidade;

    public CidadeValidator(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaEstado();
    }

    private void validaNome() {
        String nome = this.cidade.getNome().trim();

        if (nome.isEmpty() || nome == null) {
            messages.add(FIELD_NOME_REQUIRED);
        }
        if (nome.length() > 30) {
            messages.add(FIELD_NOME_LIMIT_EXCEDDED);
        }
    }

    private void validaEstado() {
        Estado estado = this.cidade.getEstado();

        if (estado == null) {
            messages.add(FIELD_ESTADO_REQUIRED);
        }
    }

}
