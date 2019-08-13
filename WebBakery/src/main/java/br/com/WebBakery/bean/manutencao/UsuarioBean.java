package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.validator.UsuarioValidator;

@Named(UsuarioBean.BEAN_NAME)
@ViewScoped
public class UsuarioBean extends AbstractBaseRegisterMBean<TOUsuario> {

    public static final String BEAN_NAME = "usuarioBean";

    private static final long serialVersionUID = 2840219448696216244L;

    @Inject
    private UsuarioDao usuarioDao;
    private TipoUsuario tipoUsuario;
    private UsuarioValidator validator;

    private String senha;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }
    }

    @Transactional
    public void cadastrar() {
        try {
            this.validator = new UsuarioValidator(this.getTo(), this.senha, this.usuarioDao);
            this.getTo().setTipo(tipoUsuario);

            if (getValidator().isValid()) {
                this.getTo().setAtivo(true);
                this.usuarioDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    protected String getBeanName() {
        // TODO Auto-generated method stub
        return null;
    }

}
