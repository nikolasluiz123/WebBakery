package br.com.WebBakery.bean;

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

import br.com.WebBakery.dao.PopulaBancoDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.validator.UsuarioValidator;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    transient private EntityManager em;
    private UsuarioDao usuarioDao;
    private PopulaBancoDao populaBancoDao;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private TipoUsuario tipoUsuario;
    @Inject
    transient private FacesContext context;
    private UsuarioValidator validator;

    @PostConstruct
    private void init() {
        this.usuarioDao = new UsuarioDao(this.em);
        this.populaBancoDao = new PopulaBancoDao(this.em);
        this.usuario = new Usuario();
        this.usuarios = new ArrayList<>();
        validator = new UsuarioValidator(this.usuario);
    }

    public UsuarioBean() {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        this.usuarios = usuarioDao.listarTodos(true);
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Transactional
    public void cadastrar() {
        this.usuario.setTipo(tipoUsuario);

        if (this.usuario.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    @Transactional
    public void carregar(Usuario usuario) {
        this.validator = new UsuarioValidator(usuario);
        this.usuario = usuario;
    }

    @Transactional
    public void inativar(Usuario usuario) {
        usuario.setAtivo(false);
        this.usuarioDao.atualizar(usuario);
        this.usuarios = this.usuarioDao.listarTodos(true);
    }

    @Transactional
    public void popularBanco() {
        this.populaBancoDao.popularBanco();
    }

    private void efetuarAtualizacao() {
        if (this.validator.ehValido()) {
            this.usuarioDao.atualizar(this.usuario);
            context.addMessage("formCadastroUsuario:messages",
                               new FacesMessage("Usuário atualizado com sucesso!"));
        }
    }

    private void efetuarCadastro() {
        List<Usuario> todosOsUsuarios = this.usuarioDao.listarTodos();
        if (!validator.existe(todosOsUsuarios) && validator.ehValido()) {
            this.usuario.setAtivo(true);
            this.usuarioDao.cadastrar(this.usuario);
            context.addMessage("formCadastroUsuario:messages",
                               new FacesMessage("Usuário cadastrado com sucesso!"));
        }
    }

    private void atualizarTela() {
        this.usuario = new Usuario();
        this.usuarios = getUsuarios();
        this.validator.mostrarMensagens();
        this.validator.clearMessages();
    }
}
