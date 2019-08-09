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

    public void logar() throws IOException {
        try {
            this.toUsuario = usuarioDao.usuarioExiste(this.toUsuario.getEmail());
            this.validator = new LoginValidator(this.toUsuario,
                                                this.senha,
                                                this.usuarioDao,
                                                this.funcionarioDao,
                                                this.clienteDao);
            if (validator.isValid()) {
                getContext().getExternalContext().getSessionMap().put("usuarioLogado",
                                                                      this.toUsuario);
                getContext().getExternalContext().redirect("cadastroFotoPerfilUsuario.xhtml");
            } else {
                validator.showMessages();
                this.toUsuario = new TOUsuario();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public TOUsuario getToUsuario() {
        return toUsuario;
    }

    public void setToUsuario(TOUsuario toUsuario) {
        this.toUsuario = toUsuario;
    }

    public void popularBanco() {
        // this.populaBancoDao.popularBanco();
    }

}
