package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOCliente;

@Named(ListaClienteBean.BEAN_NAME)
@ViewScoped
public class ListaClienteBean extends AbstractBaseListMBean implements IBaseListMBean<TOCliente> {

    public static final String BEAN_NAME = "listaClienteBean";

    private static final long serialVersionUID = 313141180992233348L;

    private static final String INATIVATED_SUCCESSFULLY = "Cliente inativado com sucesso!";

    @Inject
    private ClienteDao clienteDao;
    private List<TOCliente> toClientes;
    private List<TOCliente> toClientesFiltrados;

    @PostConstruct
    private void init() {
        this.toClientes = new ArrayList<>();
        this.toClientesFiltrados = new ArrayList<>();
        initClientes();
    }
    
    public void onAlterar() {
        carregar(Integer.parseInt(getRequestParameter("idCliente")),
                 getRequestParameter("sessionKey"),
                 getRequestParameter("redirectPage"));
    }

    @Override
    public void inativar(TOCliente to) {
        try {
            to.setAtivo(false);
            this.clienteDao.salvar(to);
            initClientes();
            getContext().addMessage(null, new FacesMessage(INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initClientes() {
        try {
            this.toClientes = this.clienteDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOCliente> getToClientes() {
        return toClientes;
    }

    public void setToClientes(List<TOCliente> toClientes) {
        this.toClientes = toClientes;
    }

    public List<TOCliente> getToClientesFiltrados() {
        return toClientesFiltrados;
    }

    public void setToClientesFiltrados(List<TOCliente> toClientesFiltrados) {
        this.toClientesFiltrados = toClientesFiltrados;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
