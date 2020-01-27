package br.com.WebBakery.validator;

import java.text.ParseException;
import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.util.DateUtil;
import br.com.WebBakery.util.StringUtil;

public class ReceitaValidator extends AbstractValidator {

    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";
    private static final String FIELD_NOME_LIMIT_EXCEEDED = "Nome com excedência de caractéres!";
    private static final String FIELD_TEMPO_PREPARO_REQUIRED = "Tempo de preparo é obrigatório!";
    private static final String FIELD_TEMPO_PREPARO_NOT_VALID = "Tempo de preparo inválido!";

    private TOReceita toReceita;

    public ReceitaValidator(TOReceita toReceita) {
        this.toReceita = toReceita;
    }

    @Override
    public void chamarValidacoes() {
        validarNome();
        validarTempo();
    }

    private void validarNome() {
        String nome = this.toReceita.getNome();

        if (StringUtil.isNullOrEmpty(nome)) {
            this.messages.add(FIELD_NOME_REQUIRED);
        } else if (nome.length() > 32) {
            this.messages.add(FIELD_NOME_LIMIT_EXCEEDED);
        }
    }

    private void validarTempo() {
        Date tempoPreparo = this.toReceita.getTempoPreparo();
        Date tempoFormatado = null;
        try {
            tempoFormatado = DateUtil.formatDateToTime("HH:mm", tempoPreparo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (tempoPreparo == null) {
            this.messages.add(FIELD_TEMPO_PREPARO_REQUIRED);
        } else if (tempoFormatado.getTime() == 0) {
            this.messages.add(FIELD_TEMPO_PREPARO_NOT_VALID);
        }
    }
}
