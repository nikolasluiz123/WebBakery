package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOEndereco;
import br.com.WebBakery.util.CepUtil;
import br.com.WebBakery.util.StringUtil;

public class EnderecoValidator extends AbstractValidator {

    private static final String FIELD_BAIRRO_NOT_VALID = "Bairro inválido!";
    private static final String FIELD_BAIRRO_REQUIRED = "Bairro é obrigatório!";
    private static final String FIELD_RUA_NOT_VALID = "Rua inválida!";
    private static final String FIELD_RUA_REQUIRED = "Rua é obrigatória!";
    private static final String FIELD_COMPLEMENTO_LIMIT_EXCEEDED = "Complemento inválido!";
    private static final String FIELD_COMPLEMENTO_REQUIRED = "Complemento é obrigatório!";
    private static final String FIELD_CEP_NOT_VALID = "Cep inválido!";
    private static final String FIELD_CEP_REQUIRED = "Cep é obrigatório!";
    private static final String FIELD_CIDADE_REQUIRED = "Cidade é obrigatória!";
    private static final String FIELD_ESTADO_REQUIRED = "Estado é obrigatório!";
    private static final String FIELD_PAIS_REQUIRED = "País é obrigatório!";

    private TOEndereco endereco;

    public EnderecoValidator(TOEndereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public void chamarValidacoes() {
        validaPais();
        validaEstado();
        validaCidade();
        validaLogradouro();
    }

    private void validaPais() {
        if (this.endereco.getToPais().getId() == null) {
            this.messages.add(FIELD_PAIS_REQUIRED);
        }
    }

    private void validaEstado() {
        if (this.endereco.getToEstado().getId() == null) {
            this.messages.add(FIELD_ESTADO_REQUIRED);
        }
    }

    private void validaCidade() {
        if (this.endereco.getToCidade().getId() == null) {
            this.messages.add(FIELD_CIDADE_REQUIRED);
        }
    }

    private void validaLogradouro() {
        validaBairro();
        validaRua();
        validaComplemento();
        validaCep();
    }

    private void validaCep() {
        String cep = this.endereco.getToLogradouro().getCep();

        if (StringUtil.isNullOrEmpty(cep)) {
            this.messages.add(FIELD_CEP_REQUIRED);
        } else if (!CepUtil.EhValido(cep)) {
            this.messages.add(FIELD_CEP_NOT_VALID);
        }
    }

    private void validaComplemento() {
        String complemento = this.endereco.getToLogradouro().getComplemento();
        
        if (StringUtil.isNullOrEmpty(complemento)) {
            this.messages.add(FIELD_COMPLEMENTO_REQUIRED);
        } else if (complemento.length() > 32) {
            this.messages.add(FIELD_COMPLEMENTO_LIMIT_EXCEEDED);
        }
    }

    private void validaRua() {
        String rua = this.endereco.getToLogradouro().getRua();
        
        if (StringUtil.isNullOrEmpty(rua)) {
            this.messages.add(FIELD_RUA_REQUIRED);
        } else if (rua.length() > 32) {
            this.messages.add(FIELD_RUA_NOT_VALID);
        }
    }

    private void validaBairro() {
        String bairro = this.endereco.getToLogradouro().getBairro();
        
        if (StringUtil.isNullOrEmpty(bairro)) {
            this.messages.add(FIELD_BAIRRO_REQUIRED);
        } else if (bairro.length() > 32) {
            this.messages.add(FIELD_BAIRRO_NOT_VALID);
        }
    }
}
