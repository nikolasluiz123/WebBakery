package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.util.StringUtil;

public class PaisValidator extends AbstractValidator {

    private static final String FIELD_SIGLA_LIMIT_EXCEDDED = "Sigla com excedência de caractéres!";
    private static final String FIELD_SIGLA_REQUIRED = "Sigla é obrigatória!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com excedência de caracteres!";
    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";
   
    private TOPais toPais;

    public PaisValidator(TOPais toPais) {
        this.toPais = toPais;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaSigla();
    }

    private void validaNome() {
        String nome = this.toPais.getNome().trim();

        if (StringUtil.isNullOrEmpty(nome)) {
            messages.add(FIELD_NOME_REQUIRED);
        }
        if (nome.length() > 32) {
            messages.add(FIELD_NOME_LIMIT_EXCEDDED);
        }
    }

    private void validaSigla() {
        String sigla = this.toPais.getSigla().trim();

        if (StringUtil.isNullOrEmpty(sigla)) {
            messages.add(FIELD_SIGLA_REQUIRED);
        }
        if (sigla.length() > 4) {
            messages.add(FIELD_SIGLA_LIMIT_EXCEDDED);
        }
        this.toPais.setSigla(sigla.toUpperCase());
    }
}
