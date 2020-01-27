package br.com.WebBakery.validator;

import java.math.BigDecimal;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.util.CpfUtil;
import br.com.WebBakery.util.StringUtil;

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
    private static final String USER_ALREADY_USED = "Este usuário já foi utilizado!";
    private static final String FIELD_SALARIO_REQUIRED = "Salário é Obrigatório!";

    private TOFuncionario toFuncionario;
    private FuncionarioDao funcionarioDao;

    public FuncionarioValidator(TOFuncionario toFuncionario, FuncionarioDao funcionarioDao) {
        this.toFuncionario = toFuncionario;
        this.funcionarioDao = funcionarioDao;
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
        String cpf = this.toFuncionario.getCpf().trim();

        if (StringUtil.isNullOrEmpty(cpf)) {
            this.messages.add(FIELD_CPF_REQUIRED);
        } else if (!CpfUtil.isValid(cpf)) {
            this.messages.add(FIELD_CPF_NOT_VALID);
        }
    }

    private void validaRg() {
        String rg = this.toFuncionario.getRg().trim().replace(".", "");

        if (StringUtil.isNullOrEmpty(rg)) {
            this.messages.add(FIELD_RG_REQUIRED);
        } else if (rg.length() != 7) {
            this.messages.add(FIELD_RG_NOT_VALID);
        }
    }

    private void validaTelefone() {
        String telefone = this.toFuncionario.getTelefone().replace("(", "").replace(")", "")
                .replace("-", "").replace(" ", "").trim();

        if (StringUtil.isNullOrEmpty(telefone)) {
            this.messages.add(FIELD_TELEFONE_REQUIRED);
        } else if (telefone.length() > 13) {
            this.messages.add(FIELD_TELEFONE_NOT_VALID);
        }
    }

    private void validaDataNascimento() {
        if (this.toFuncionario.getDataNascimento() == null) {
            this.messages.add(FIELD_DATA_NASCIMNETO_REQUIRED);
        }
    }

    private void validaSalario() {
        if (this.toFuncionario.getSalario() == null) {
            this.messages.add(FIELD_SALARIO_REQUIRED);
        } else if (this.toFuncionario.getSalario().equals(BigDecimal.ZERO)) {
            this.messages.add(FIELD_SALARIO_NOT_VALID);
        }
    }

    private void validaUsuario() {
        boolean existeVinculoComUsuario = existeVinculoComUsuario(this.toFuncionario.getId());

        if (this.toFuncionario.getToUsuario() == null) {
            this.messages.add(FIELD_USUARIO_REQUIRED);
        } else if (existeVinculoComUsuario) {
            this.messages.add(USER_ALREADY_USED);
        }
    }

    private Boolean existeVinculoComUsuario(Integer idFuncionario) {
        TOFuncionario f = null;

        try {
            f = funcionarioDao.buscarPorIdUsuario(this.toFuncionario.getToUsuario().getId(), idFuncionario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (f != null) {
            return true;
        }
        return false;
    }
}
