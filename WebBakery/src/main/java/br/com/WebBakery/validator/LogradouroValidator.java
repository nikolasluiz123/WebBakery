package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.entitys.Logradouro;
import br.com.WebBakery.util.CepUtil;
import br.com.WebBakery.util.StringUtil;

public class LogradouroValidator extends AbstractValidator {

    private static final String FIELD_COMPLEMENTO_LIMIT_EXCEDDED = "Complemento com excedência de caractéres!";
    private static final String FIELD_COMPLEMENTO_REQUIRED = "Complemento é obrigatório!";
    private static final String FIELD_RUA_LIMIT_EXCEDDED = "Rua com excedência de caractéres!";
    private static final String FIELD_RUA_REQUIRED = "Rua é obrigatória!";
    private static final String FIELD_CEP_REQUIRED = "Cep é obrigatório!";
    private static final String FIELD_BAIRRO_LIMIT_EXCEDDED = "Bairro com excedência de caractéres!";
    private static final String FIELD_BAIRRO_REQUIRED = "Bairro é obrigatório!";
    private static final String FIELD_CEP_NOT_VALID = "Cep inválido!";

    private Logradouro logradouro;

    public LogradouroValidator(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public void chamarValidacoes() {
        validaBairro();
        validaCep();
        validaRua();
        validaComplemento();
    }

    private void validaBairro() {
        String bairro = this.logradouro.getBairro().trim();

        if (StringUtil.isNullOrEmpty(bairro)) {
            messages.add(FIELD_BAIRRO_REQUIRED);
        }
        if (bairro.length() > 40) {
            messages.add(FIELD_BAIRRO_LIMIT_EXCEDDED);
        }
    }

    private void validaCep() {
        String cep = this.logradouro.getCep().trim();

        if (StringUtil.isNullOrEmpty(cep)) {
            messages.add(FIELD_CEP_REQUIRED);
        }
        if (!CepUtil.EhValido(cep)) {
            messages.add(FIELD_CEP_NOT_VALID);
        }
    }

    private void validaRua() {
        String rua = this.logradouro.getRua().trim();

        if (StringUtil.isNullOrEmpty(rua)) {
            messages.add(FIELD_RUA_REQUIRED);
        }
        if (rua.length() > 30) {
            messages.add(FIELD_RUA_LIMIT_EXCEDDED);
        }
    }

    private void validaComplemento() {
        String complemento = this.logradouro.getComplemento().trim();

        if (StringUtil.isNullOrEmpty(complemento)) {
            messages.add(FIELD_COMPLEMENTO_REQUIRED);
        }
        if (complemento.length() > 20) {
            messages.add(FIELD_COMPLEMENTO_LIMIT_EXCEDDED);
        }
    }
}
