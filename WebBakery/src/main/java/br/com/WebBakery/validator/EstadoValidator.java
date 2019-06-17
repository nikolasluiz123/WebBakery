package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.model.Pais;

public class EstadoValidator extends AbstractValidator {

    private static final String FIELD_PAIS_REQUIRED = "País é obrigatório!";
    private static final String FIELD_SIGLA_LIMIT_EXCEDDED = "Sigla com excedência de caractéres!";
    private static final String FIELD_SIGLA_REQUIRED = "Sigla é obrigatória!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com excedência de caractéres!";
    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";

    private Estado estado;

    public EstadoValidator(Estado estado) {
        this.estado = estado;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaSigla();
        validaEstado();
    }

    private void validaNome() {
        String nome = this.estado.getNome().trim();

        if (nome.isEmpty() || nome == null) {
            messages.add(FIELD_NOME_REQUIRED);
        }
        if (nome.length() > 30) {
            messages.add(FIELD_NOME_LIMIT_EXCEDDED);
        }
    }

    private void validaSigla() {
        String sigla = this.estado.getSigla().trim();

        if (sigla.isEmpty() || sigla == null) {
            messages.add(FIELD_SIGLA_REQUIRED);
        }
        if (sigla.length() > 4) {
            messages.add(FIELD_SIGLA_LIMIT_EXCEDDED);
        }
        this.estado.setSigla(sigla.toUpperCase());
    }

    private void validaEstado() {
        Pais pais = this.estado.getPais();

        if (pais == null) {
            messages.add(FIELD_PAIS_REQUIRED);
        }
    }
}
