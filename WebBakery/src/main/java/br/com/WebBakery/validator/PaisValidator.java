package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Pais;

public class PaisValidator extends AbstractValidator {

    private static final String FIELD_SIGLA_LIMIT_EXCEDDED = "Sigla com excedência de caractéres!";
    private static final String FIELD_SIGLA_REQUIRED = "Sigla é obrigatória!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com excedência de caracteres!";
    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";
   
    private Pais pais;

    public PaisValidator(Pais pais) {
        this.pais = pais;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaSigla();
    }

    private void validaNome() {
        String nome = this.pais.getNome().trim();

        if (nome.isEmpty() || nome == null) {
            messages.add(FIELD_NOME_REQUIRED);
        }
        if (nome.length() > 30) {
            messages.add(FIELD_NOME_LIMIT_EXCEDDED);
        }
    }

    private void validaSigla() {
        String sigla = this.pais.getSigla().trim();

        if (sigla.isEmpty() || sigla == null) {
            messages.add(FIELD_SIGLA_REQUIRED);
        }
        if (sigla.length() > 4) {
            messages.add(FIELD_SIGLA_LIMIT_EXCEDDED);
        }
        this.pais.setSigla(sigla.toUpperCase());
    }
}
