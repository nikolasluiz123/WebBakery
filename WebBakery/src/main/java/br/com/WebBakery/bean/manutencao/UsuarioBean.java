package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.validator.UsuarioValidator;

@Named(UsuarioBean.BEAN_NAME)
@ViewScoped
public class UsuarioBean extends AbstractBaseRegisterMBean<TOUsuario> {

    public static final String BEAN_NAME = "usuarioBean";

    private static final long serialVersionUID = 2840219448696216244L;

    private static final String USUARIO_UPDATED_SUCCESSFULLY = "Usuário atualizado com sucesso!";

    private static final String USUARIO_REGISTERED_SUCCESSFULLY = "Usuário cadastrado com sucesso!";

    @Inject
    private UsuarioDao usuarioDao;
    private TOUsuario toUsuario;
    private TipoUsuario tipoUsuario;
    private UsuarioValidator validator;

    private String senha;

    @PostConstruct
    private void init() {
        this.toUsuario = new TOUsuario();
    }

    @Transactional
    public void cadastrar() {
        try {
            this.validator = new UsuarioValidator(this.toUsuario, this.senha, this.usuarioDao);
            this.toUsuario.setTipo(tipoUsuario);

            if (this.toUsuario.getId() == null) {
                efetuarCadastro();
            } else {
                efetuarAtualizacao();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void efetuarCadastro() throws Exception {
        if (validator.isValid()) {
            this.toUsuario.setAtivo(true);
            this.usuarioDao.cadastrar(this.toUsuario);
            getContext().addMessage(null, new FacesMessage(USUARIO_REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() throws Exception {
        if (this.validator.isValid()) {
            this.usuarioDao.atualizar(this.toUsuario);
            getContext().addMessage(null, new FacesMessage(USUARIO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toUsuario = new TOUsuario();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    public TOUsuario getToUsuario() {
        return toUsuario;
    }

    public void setToUsuario(TOUsuario toUsuario) {
        this.toUsuario = toUsuario;
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
