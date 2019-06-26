package br.com.WebBakery.validator;

import java.math.BigDecimal;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Funcionario;
import br.com.WebBakery.util.Cpf_Util;

public class FuncionarioValidator extends AbstractValidator {

    private static final String FIELD_USUARIO_REQUIRED = "Usuário é obrigatório!";
    private static final String FIELD_SALARIO_NOT_VALID = "Salário inválido!";
    private static final String FIELD_DATA_NASCIMNETO_REQUIRED = "Data de Nascimento é obrigatória!";
    private static final String FIELD_TELEFONE_NOT_VALID = "Telefone inválido!";
    private static final String FIELD_TELEFONE_REQUIRED = "Telefone é obrigatório!";
    private static final String FIELD_RG_NOT_VALID = "RG inválido!";
    private static final String FIELD_RG_REQUIRED = "RG é obrigatório!";
    private static final String FIELD_CPF_NOT_VALID = "CPF inválido!";
    private static final String FIELD_CPF_REQUIRED = "CPF é obrigatório!";

    private Funcionario funcionario;

    public FuncionarioValidator(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void chamarValidacoes() {
        validaCpf();
        validaRg();
        validaTelefone();
        validaDataNascimento();
        validaSalario();
        validaUsuario();
    }

    private void validaCpf() {
        String cpf = this.funcionario.getCpf().trim();

        if (cpf.isEmpty() || cpf == null) {
            this.messages.add(FIELD_CPF_REQUIRED);
        }
        if (!Cpf_Util.isValid(cpf)) {
            this.messages.add(FIELD_CPF_NOT_VALID);
        }
    }

    private void validaRg() {
        String rg = this.funcionario.getRg().trim().replace(".", "");

        if (rg.isEmpty() || rg == null) {
            this.messages.add(FIELD_RG_REQUIRED);
        }
        if (rg.length() != 7) {
            this.messages.add(FIELD_RG_NOT_VALID);
        }
    }

    private void validaTelefone() {
        String telefone = this.funcionario.getTelefone().replace("(", "").replace(")", "")
                .replace("-", "").replace(" ", "").trim();

        if (telefone.isEmpty() || telefone == null) {
            this.messages.add(FIELD_TELEFONE_REQUIRED);
        }
        if (telefone.length() > 13) {
            this.messages.add(FIELD_TELEFONE_NOT_VALID);
        }
    }

    private void validaDataNascimento() {
        if (this.funcionario.getDataNascimento() == null) {
            this.messages.add(FIELD_DATA_NASCIMNETO_REQUIRED);
        }
    }

    private void validaSalario() {
        if (this.funcionario.getSalario().equals(BigDecimal.ZERO)) {
            this.messages.add(FIELD_SALARIO_NOT_VALID);
        }
    }

    private void validaUsuario() {
        if (this.funcionario.getUsuario() == null) {
            this.messages.add(FIELD_USUARIO_REQUIRED);
        }
    }
}
