package br.com.WebBakery.bean.manutencao;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.model.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 7192496569257226719L;

    @Inject
    private FacesContext context;
    @PersistenceContext
    private EntityManager em;

    private Usuario usuario;
    private UsuarioDao dao;

    public Usuario getUsuario() {
        return usuario;
    }

    @PostConstruct
    private void init() {
        this.usuario = new Usuario();
        this.dao = new UsuarioDao(this.em);
    }

    public void logar() throws IOException {
        boolean existe = dao.usuarioExiste(this.usuario);
        if (existe) {
            context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
            context.getExternalContext().redirect("cadastroFuncionario.xhtml");
        } else {
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("Usuário não encontrado!"));
            context.getExternalContext().redirect("login.xhtml");
        }
    }

    public void deslogar() throws IOException {
        context.getExternalContext().getSessionMap().remove("usuarioLogado");
        context.getExternalContext().redirect("login.xhtml");
    }
}
