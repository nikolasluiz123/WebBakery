package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.to.TOCliente;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.Hash_Util;
import br.com.WebBakery.util.String_Util;

public class LoginValidator extends AbstractValidator {

    private static final String USER_NOT_EXISTS = "Usu�rio n�o encontrado!";

    private static final String FIELD_SENHA_REQUIRED = "Senha � obrigat�ria!";

    private static final String FIELD_SENHA_OR_EMAIL_NOT_VALID = "E-mail ou senha inv�lido(s)!";

    private static final String NO_RELATION_EMPLOYEE = "Nenhum funcion�rio cadastrado com este usu�rio foi encontrado!";

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
            verificaUsuarioVinculadoAlguem();
        }
    }

    private void validaSenha() {
        String generateHash = Hash_Util.generateHashMaxSecurity(this.senha);
        String senhaUsuarioBanco = this.toUsuario.getSenha();

        if (String_Util.isNullOrEmpty(this.senha)) {
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
        TOCliente c = new TOCliente();
        TOFuncionario f = new TOFuncionario();

        try {
            if (this.toUsuario.getTipo() == TipoUsuario.CLIENTE) {
                c = clienteDao.buscarPorIdUsuario(toUsuario.getId());
            } else {
                f = funcionarioDao.buscarPorIdUsuario(toUsuario.getId());
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
