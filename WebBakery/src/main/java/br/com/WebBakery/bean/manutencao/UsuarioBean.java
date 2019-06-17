package br.com.WebBakery.bean.manutencao;

import java.io.Serializable;

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
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.UsuarioValidator;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 2840219448696216244L;

    private static final String USUARIO_UPDATED_SUCCESSFULLY = "Usuário atualizado com sucesso!";

    private static final String USUARIO_REGISTERED_SUCCESSFULLY = "Usuário cadastrado com sucesso!";

    @PersistenceContext
    transient private EntityManager em;
    private UsuarioDao usuarioDao;
    private PopulaBancoDao populaBancoDao;
    private Usuario usuario;
    private TipoUsuario tipoUsuario;
    @Inject
    transient private FacesContext context;
    private UsuarioValidator validator;

    @PostConstruct
    private void init() {
        this.usuarioDao = new UsuarioDao(this.em);
        this.populaBancoDao = new PopulaBancoDao(this.em);
        this.usuario = new Usuario();
        verificaUsuarioSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new UsuarioValidator(this.usuario, this.em);
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
            context.addMessage(null, new FacesMessage(USUARIO_REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.usuarioDao.atualizar(this.usuario);
            context.addMessage(null, new FacesMessage(USUARIO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.usuario = new Usuario();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    @Transactional
    public void popularBanco() {
        this.populaBancoDao.popularBanco();
    }

    private void verificaUsuarioSessao() {
        Integer usuarioId = (Integer) FacesUtil.getHTTPSession().getAttribute("UsuarioID");
        if (usuarioId != null) {
            this.usuario = usuarioDao.buscarPorId(usuarioId);
            FacesUtil.getHTTPSession().removeAttribute("UsuarioID");
        }
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
}
