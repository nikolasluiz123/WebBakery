package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.util.StringUtil;

public class EstadoValidator extends AbstractValidator {

    private static final String FIELD_PAIS_REQUIRED = "Pa�s � obrigat�rio!";
    private static final String FIELD_SIGLA_LIMIT_EXCEDDED = "Sigla com exced�ncia de caract�res!";
    private static final String FIELD_SIGLA_REQUIRED = "Sigla � obrigat�ria!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com exced�ncia de caract�res!";
    private static final String FIELD_NOME_REQUIRED = "Nome � obrigat�rio!";

    private TOEstado toEstado;

    public EstadoValidator(TOEstado toEstado) {
        this.toEstado = toEstado;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaSigla();
        validaEstado();
    }

    private void validaNome() {
        String nome = this.toEstado.getNome().trim();

        if (StringUtil.isNullOrEmpty(nome)) {
            messages.add(FIELD_NOME_REQUIRED);
        }
        if (nome.length() > 30) {
            messages.add(FIELD_NOME_LIMIT_EXCEDDED);
        }
    }

    private void validaSigla() {
        String sigla = this.toEstado.getSigla().trim();

        if (StringUtil.isNullOrEmpty(sigla)) {
            messages.add(FIELD_SIGLA_REQUIRED);
        }
        if (sigla.length() > 4) {
            messages.add(FIELD_SIGLA_LIMIT_EXCEDDED);
        }
        this.toEstado.setSigla(sigla.toUpperCase());
    }

    private void validaEstado() {
        TOPais toPais = this.toEstado.getToPais();

        if (toPais == null) {
            messages.add(FIELD_PAIS_REQUIRED);
        }
    }
}
