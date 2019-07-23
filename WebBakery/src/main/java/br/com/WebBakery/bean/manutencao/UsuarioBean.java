package br.com.WebBakery.bean.manutencao;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.validator.UsuarioValidator;

@Named
@ViewScoped
public class UsuarioBean extends AbstractBaseRegisterMBean<Usuario> {

    private static final long serialVersionUID = 2840219448696216244L;

    private static final String USUARIO_UPDATED_SUCCESSFULLY = "Usuário atualizado com sucesso!";

    private static final String USUARIO_REGISTERED_SUCCESSFULLY = "Usuário cadastrado com sucesso!";

    private UsuarioDao usuarioDao;
    private Usuario usuario;
    private TipoUsuario tipoUsuario;
    private UsuarioValidator validator;

    private String senha;

    @Override
    public void init() {
        this.usuarioDao = new UsuarioDao();
        this.usuario = new Usuario();
        verificaUsuarioSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new UsuarioValidator(this.usuario, this.senha);
        this.usuario.setTipo(tipoUsuario);

        if (this.usuario.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (validator.isValid()) {
            this.usuario.setAtivo(true);
            this.usuarioDao.cadastrar(this.usuario);
            getContext().addMessage(null, new FacesMessage(USUARIO_REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.usuarioDao.atualizar(this.usuario);
            getContext().addMessage(null, new FacesMessage(USUARIO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.usuario = new Usuario();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaUsuarioSessao() {
        this.usuario = getObjetoSessao("UsuarioID", usuarioDao, usuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoUsuario[] getTiposUsuarios() {
        return TipoUsuario.values();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
