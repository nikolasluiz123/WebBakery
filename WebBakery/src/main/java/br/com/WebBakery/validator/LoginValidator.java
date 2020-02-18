package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.HashUtil;
import br.com.WebBakery.util.StringUtil;

public class LoginValidator extends AbstractValidator {

    private static final String USER_NOT_EXISTS = "Usuário não encontrado!";

    private static final String FIELD_SENHA_REQUIRED = "Senha é obrigatória!";

    private static final String FIELD_SENHA_OR_EMAIL_NOT_VALID = "E-mail ou senha inválido(s)!";

    private static final String NO_RELATION_EMPLOYEE = "Nenhum funcionário cadastrado com este usuário foi encontrado!";

    private TOUsuario toUsuario;
    private String senha;
    private FuncionarioDao funcionarioDao;

    public LoginValidator(TOUsuario toUsuario,
                          String senha,
                          UsuarioDao usuarioDao,
                          FuncionarioDao funcionarioDao) {
        this.toUsuario = toUsuario;
        this.senha = senha;
        this.funcionarioDao = funcionarioDao;
    }

    @Override
    public void chamarValidacoes() {
        boolean existe = verificaUsuarioExiste();
        if (existe) {
            validaSenha();
            verificaUsuarioVinculadoAlguem();
        }
    }

    private void validaSenha() {
        String generateHash = HashUtil.generateHashMaxSecurity(this.senha);
        String senhaUsuarioBanco = this.toUsuario.getSenha();

        if (StringUtil.isNullOrEmpty(this.senha)) {
            messages.add(FIELD_SENHA_REQUIRED);
        } else if (!senhaUsuarioBanco.equals(generateHash)) {
            messages.add(FIELD_SENHA_OR_EMAIL_NOT_VALID);
        }

    }

    private void verificaUsuarioVinculadoAlguem() {
        if (!existeVinculoComUsuario()) {
            messages.add(NO_RELATION_EMPLOYEE);
        }
    }

    private boolean verificaUsuarioExiste() {
        if (this.toUsuario.getId() == null) {
            messages.add(USER_NOT_EXISTS);
            return false;
        }
        return true;
    }

    private Boolean existeVinculoComUsuario() {
        TOFuncionario f = new TOFuncionario();

        try {
            f = funcionarioDao.buscarPorIdUsuario(toUsuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (f.getId() != null) {
            return true;
        }
        return false;
    }

}
