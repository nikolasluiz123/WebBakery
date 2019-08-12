package br.com.WebBakery.bean.manutencao;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.PopulaBancoDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.validator.LoginValidator;

@Named(LoginBean.BEAN_NAME)
@ViewScoped
public class LoginBean extends AbstractBaseRegisterMBean<TOUsuario> {

    private static final String USER_IDENTIFIER_SESSION_KEY = "usuarioLogado";

    private static final String PAGE_WELCOME_REDIRECT = "cadastroPais.xhtml";

    public static final String BEAN_NAME = "loginBean";

    private static final long serialVersionUID = 7192496569257226719L;

    private TOUsuario toUsuario;
    @Inject
    private UsuarioDao usuarioDao;
    @Inject
    private PopulaBancoDao populaBancoDao;
    @Inject
    private FuncionarioDao funcionarioDao;
    @Inject
    private ClienteDao clienteDao;
    private LoginValidator validator;
    private String senha;

    @PostConstruct
    private void init() {
        this.toUsuario = new TOUsuario();
    }

    public void login() {
        try {
            this.toUsuario = returnUserIfExists();
            this.validator = new LoginValidator(this.toUsuario,
                                                this.senha,
                                                this.usuarioDao,
                                                this.funcionarioDao,
                                                this.clienteDao);
            redirectUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        try {
            removeUserSession();
            getContext().getExternalContext().redirect(PAGE_WELCOME_REDIRECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectUser() throws IOException {
        if (validator.isValid()) {
            setUserSession();
            getContext().getExternalContext().redirect(PAGE_WELCOME_REDIRECT);
        } else {
            validator.showMessages();
        }
    }

    private TOUsuario returnUserIfExists() throws Exception {
        return usuarioDao.usuarioExiste(this.toUsuario.getEmail());
    }

    private void setUserSession() {
        getContext().getExternalContext().getSessionMap().put(USER_IDENTIFIER_SESSION_KEY,
                                                              this.toUsuario);
    }

    private void removeUserSession() {
        getContext().getExternalContext().getSessionMap().remove(USER_IDENTIFIER_SESSION_KEY);
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TOUsuario getToUsuario() {
        return toUsuario;
    }

    public void setToUsuario(TOUsuario toUsuario) {
        this.toUsuario = toUsuario;
    }

    public void popularBanco() {
        this.populaBancoDao.popularBanco();
    }

}
