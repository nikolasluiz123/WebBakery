package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.EnumTipoUsuario;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.validator.UsuarioValidator;

@Named(UsuarioBean.BEAN_NAME)
@ViewScoped
public class UsuarioBean extends AbstractBaseRegisterMBean<TOUsuario> {

    public static final String BEAN_NAME = "usuarioBean";

    private static final long serialVersionUID = 2840219448696216244L;

    @Inject
    private UsuarioDao usuarioDao;
    private EnumTipoUsuario tipoUsuario;

    private String senha;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();
        
        if (getTo() == null) {
            resetTo();
        } else {
            this.tipoUsuario = getTo().getTipo();
        }
    }

    @Transactional
    public void salvar() {
        try {
            this.getTo().setTipo(tipoUsuario);
            addValidators();
            if (isValid()) {
                this.getTo().setAtivo(true);
                this.usuarioDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        UsuarioValidator usuarioValidator = new UsuarioValidator(this.getTo(),
                                                                 this.senha,
                                                                 this.usuarioDao);
        addValidator(usuarioValidator);
    }

    @Override
    protected AbstractBaseDao<TOUsuario> getDao() {
        return usuarioDao;
    }

    @Override
    protected TOUsuario getNewInstaceTO() {
        return new TOUsuario();
    }

    public EnumTipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(EnumTipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public EnumTipoUsuario[] getTiposUsuarios() {
        return EnumTipoUsuario.values();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
