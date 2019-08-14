package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.to.TOCliente;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.String_Util;

public class LoginValidator extends AbstractValidator {

    private static final String USER_NOT_EXISTS = "Usuário não encontrado!";

    private static final String FIELD_SENHA_REQUIRED = "Senha é obrigatória!";

    private static final String FIELD_SENHA_OR_EMAIL_NOT_VALID = "E-mail ou senha inválido(s)!";

    private static final String NO_RELATION_EMPLOYEE = "Nenhum funcionário cadastrado com este usuário foi encontrado!";

    private TOUsuario toUsuario;
    private String senha;
    private UsuarioDao usuarioDao;
    private FuncionarioDao funcionarioDao;
    private ClienteDao clienteDao;

    public LoginValidator(TOUsuario toUsuario,
                          String senha,
                          UsuarioDao usuarioDao,
                          FuncionarioDao funcionarioDao,
                          ClienteDao clienteDao) {
        this.toUsuario = toUsuario;
        this.senha = senha;
        this.usuarioDao = usuarioDao;
        this.funcionarioDao = funcionarioDao;
        this.clienteDao = clienteDao;
    }

    @Override
    public void chamarValidacoes() {
        boolean existe = verificaUsuarioExiste();
        if (existe) {
            validaSenha();
            verificaUsuarioVinculadoFuncionario();
        }
    }

    private void validaSenha() {
        if (String_Util.isNullOrEmpty(this.senha)) {
            messages.add(FIELD_SENHA_REQUIRED);
        } else if (this.toUsuario.getSenha() != this.senha.hashCode()) {
            messages.add(FIELD_SENHA_OR_EMAIL_NOT_VALID);
        }
    }

    private void verificaUsuarioVinculadoFuncionario() {
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
        TOUsuario u = new TOUsuario();
        TOCliente c = new TOCliente();
        TOFuncionario f = new TOFuncionario();

        try {
            u = this.usuarioDao.usuarioExiste(this.toUsuario.getEmail());

            if (this.toUsuario.getTipo() == TipoUsuario.CLIENTE) {
                c = clienteDao.buscarPorIdUsuario(u.getId());
            } else {
                f = funcionarioDao.buscarPorIdUsuario(u.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (c.getId() != null || f.getId() != null) {
            return true;
        }
        return false;
    }

}
