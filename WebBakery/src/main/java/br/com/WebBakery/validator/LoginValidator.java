package br.com.WebBakery.validator;

import javax.inject.Inject;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.model.Funcionario;
import br.com.WebBakery.model.Usuario;

public class LoginValidator extends AbstractValidator {

    private static final String FIELD_SENHA_REQUIRED = "Senha é obrigatória!";

    private static final String FIELD_SENHA_OR_EMAIL_NOT_VALID = "E-mail ou senha inválido(s)!";

    private static final String NO_RELATION_EMPLOYEE = "Nenhum funcionário cadastrado com este usuário foi encontrado!";

    private Usuario usuario;
    private String senha;
    @Inject
    private UsuarioDao usuarioDao;

    public LoginValidator(Usuario usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
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
        if (this.senha == null || this.senha.isEmpty()) {
            messages.add(FIELD_SENHA_REQUIRED);
        } else if (this.usuario.getSenha() != this.senha.hashCode()) {
            messages.add(FIELD_SENHA_OR_EMAIL_NOT_VALID);
        }
    }

    private void verificaUsuarioVinculadoFuncionario() {
        if (!existeVinculoComUsuario()) {
            messages.add(NO_RELATION_EMPLOYEE);
        }
    }

    private boolean verificaUsuarioExiste() {
        if (this.usuario == null) {
            messages.add("Usuário não encontrado!");

            return false;
        }
        return true;
    }

    private Boolean existeVinculoComUsuario() {
        FuncionarioDao fDao = new FuncionarioDao();
        ClienteDao cDao = new ClienteDao();
        Usuario u = this.usuarioDao.usuarioExiste(this.usuario.getEmail());
        Cliente c = null;
        Funcionario f = null;

        if (this.usuario.getTipo() == TipoUsuario.CLIENTE) {
            c = cDao.buscarPorIdUsuario(u.getId());
        } else {
            f = fDao.buscarPorIdUsuario(u.getId());
        }

        if (c != null || f != null) {
            return true;
        }
        return false;
    }

}
