package br.com.WebBakery.bean.consulta;

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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.Faces_Util;

@Named
@ViewScoped
public class ListaUsuarioBean implements Serializable {

    private static final long serialVersionUID = 4493498347316041129L;

    private static final String USUARIO_INATIVATED_SUCCESSFULLY = "Usuário inativado com sucesso!";

    @PersistenceContext
    transient private EntityManager em;
    @Inject
    transient private FacesContext context;

    private UsuarioDao usuarioDao;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;

    @PostConstruct
    private void init() {
        this.usuarioDao = new UsuarioDao(this.em);
        this.usuarios = new ArrayList<>();
        initListUsuarios();
    }

    @Transactional
    public void inativar(Usuario usuario) {
        usuario.setAtivo(false);
        this.usuarioDao.atualizar(usuario);
        context.addMessage(null, new FacesMessage(USUARIO_INATIVATED_SUCCESSFULLY));
        initListUsuarios();
    }

    private void initListUsuarios() {
        this.usuarios = this.usuarioDao.listarTodos(true);
    }

    public void carregar(Integer usuarioID) throws IOException {
        HttpSession session = Faces_Util.getHTTPSession();
        session.setAttribute("UsuarioID", usuarioID);
        context.getExternalContext().redirect("cadastroUsuario.xhtml");
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }
}
