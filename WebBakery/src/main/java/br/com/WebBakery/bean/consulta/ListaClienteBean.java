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
import br.com.WebBakery.bean.manutencao.ClienteBean;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOCliente;
import br.com.WebBakery.util.Faces_Util;

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

    @Override
    public void carregar(Integer clienteID) throws Exception {
        String keyAtribute = "ClienteID";
        String pageRedirect = "cadastroCliente.xhtml";
        setObjetoSessao(clienteID, keyAtribute, pageRedirect);
        getRegisterBean().getObjetoSessao(keyAtribute, clienteDao);
    }

    @Transactional
    @Override
    public void inativar(TOCliente to) {
        try {
            to.setAtivo(false);
            this.clienteDao.atualizar(to);
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

    private ClienteBean getRegisterBean() {
        return ((ClienteBean) Faces_Util.getBean(ClienteBean.BEAN_NAME));
    }

    public List<TOCliente> getClientes() {
        return toClientes;
    }

    public void setClientes(List<TOCliente> clientes) {
        this.toClientes = clientes;
    }

    public List<TOCliente> getClientesFiltrados() {
        return toClientesFiltrados;
    }

    public void setClientesFiltrados(List<TOCliente> clientesFiltrados) {
        this.toClientesFiltrados = clientesFiltrados;
    }

}
