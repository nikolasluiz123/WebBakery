package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOUsuario;

@Named(ListaUsuarioBean.BEAN_NAME)
@ViewScoped
public class ListaUsuarioBean extends AbstractBaseListMBean implements IBaseListMBean<TOUsuario> {

    static final String BEAN_NAME = "listaUsuarioBean";

    private static final long serialVersionUID = 4493498347316041129L;

    private static final String USUARIO_INATIVATED_SUCCESSFULLY = "Usuário inativado com sucesso!";

    @Inject
    private UsuarioDao usuarioDao;
    private List<TOUsuario> toUsuarios;
    private List<TOUsuario> toUsuariosFiltrados;

    @PostConstruct
    private void init() {
        this.toUsuarios = new ArrayList<>();
        initListUsuarios();
    }

    @Transactional
    @Override
    public void inativar(TOUsuario usuario) {
        try {
            usuario.setAtivo(false);
            this.usuarioDao.salvar(usuario);
            getContext().addMessage(null, new FacesMessage(USUARIO_INATIVATED_SUCCESSFULLY));
            initListUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListUsuarios() {
        try {
            this.toUsuarios = this.usuarioDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOUsuario> getToUsuarios() {
        return toUsuarios;
    }

    public void setToUsuarios(List<TOUsuario> toUsuarios) {
        this.toUsuarios = toUsuarios;
    }

    public List<TOUsuario> getToUsuariosFiltrados() {
        return toUsuariosFiltrados;
    }

    public void setToUsuariosFiltrados(List<TOUsuario> toUsuariosFiltrados) {
        this.toUsuariosFiltrados = toUsuariosFiltrados;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
