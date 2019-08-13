package br.com.WebBakery.bean.manutencao;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.FuncionarioDao;
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

    @Inject
    private UsuarioDao usuarioDao;
    // @Inject
    // private PopulaBancoDao populaBancoDao;
    @Inject
    private FuncionarioDao funcionarioDao;
    @Inject
    private ClienteDao clienteDao;
    private LoginValidator validator;
    private String senha;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

    }

    public void login() {
        try {
            setTo(returnUserIfExists());
            this.validator = new LoginValidator(this
                    .getTo(), this.senha, this.usuarioDao, this.funcionarioDao, this.clienteDao);
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
        return usuarioDao.usuarioExiste(this.getTo().getEmail());
    }

    private void setUserSession() {
        getContext().getExternalContext().getSessionMap().put(USER_IDENTIFIER_SESSION_KEY,
                                                              this.getTo());
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

    public void popularBanco() {
        // this.populaBancoDao.popularBanco();
    }

    @Override
    protected AbstractBaseDao<TOUsuario> getDao() {
        return usuarioDao;
    }

    @Override
    public AbstractValidator getValidator() {
        return validator;
    }

    @Override
    protected TOUsuario getNewInstaceTO() {
        return new TOUsuario();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
