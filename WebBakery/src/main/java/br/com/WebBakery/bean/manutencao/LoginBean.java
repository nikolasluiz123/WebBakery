package br.com.WebBakery.bean.manutencao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.PopulaBancoDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.model.Funcionario;
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
    private UsuarioDao usuarioDao;
    private PopulaBancoDao populaBancoDao;
    private List<String> messages;

    private String senha;

    public Usuario getUsuario() {
        return usuario;
    }

    @PostConstruct
    private void init() {
        this.usuario = new Usuario();
        this.usuarioDao = new UsuarioDao(this.em);
        this.populaBancoDao = new PopulaBancoDao(this.em);
        this.messages = new ArrayList<>();
    }

    public void logar() throws IOException {
        this.usuario.setSenha(12345678);
        this.usuario = usuarioDao.usuarioExiste(this.usuario);
        if (loginIsValid()) {
            context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
            context.getExternalContext().redirect("listaVenda.xhtml");
        } else {
            showMessages();
        }
    }

    private boolean loginIsValid() {
        if (this.usuario == null) {
            this.messages.add("Usuário não encontrado!");
        }
            
        // else if (!existeVinculoComUsuario()) {
        // this.messages.add("Funcionário não foi cadastrado!");
        // }

        if (!this.messages.isEmpty()) {
            return false;
        }
        return true;
    }

    private void showMessages() throws IOException {
        context.getExternalContext().getFlash().setKeepMessages(true);
        this.getMessages().forEach(message -> {
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        });
        context.getExternalContext().redirect("login.xhtml");
    }

    private Boolean existeVinculoComUsuario() {
        FuncionarioDao fDao = new FuncionarioDao(this.em);
        ClienteDao cDao = new ClienteDao(this.em);
        Usuario u = this.usuarioDao.usuarioExiste(this.usuario);
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

    public void deslogar() throws IOException {
        context.getExternalContext().getSessionMap().remove("usuarioLogado");
        context.getExternalContext().redirect("login.xhtml");
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Transactional
    public void popularBanco() {
        this.populaBancoDao.popularBanco();
    }

}
