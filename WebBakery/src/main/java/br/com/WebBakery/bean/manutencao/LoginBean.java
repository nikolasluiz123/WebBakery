package br.com.WebBakery.bean.manutencao;

import java.io.IOException;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.PopulaBancoDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.validator.LoginValidator;

@Named
@ViewScoped
public class LoginBean extends AbstractBaseRegisterMBean<Usuario> {

    private static final long serialVersionUID = 7192496569257226719L;

    private Usuario usuario;
    private UsuarioDao usuarioDao;
    private PopulaBancoDao populaBancoDao;
    private LoginValidator validator;
    private String senha;

    public void init() {
        this.usuario = new Usuario();
        this.usuarioDao = new UsuarioDao();
        this.populaBancoDao = new PopulaBancoDao();
    }

    public void logar() throws IOException {
        this.usuario = usuarioDao.usuarioExiste(this.usuario.getEmail());
        this.validator = new LoginValidator(this.usuario, this.senha);
        if (validator.isValid()) {
            getContext().getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
            getContext().getExternalContext().redirect("cadastroFotoPerfilUsuario.xhtml");
        } else {
            validator.showMessages();
        }
    }

    public void deslogar() throws IOException {
        getContext().getExternalContext().getSessionMap().remove("usuarioLogado");
        getContext().getExternalContext().redirect("login.xhtml");
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Transactional
    public void popularBanco() {
        this.populaBancoDao.popularBanco();
    }

}
