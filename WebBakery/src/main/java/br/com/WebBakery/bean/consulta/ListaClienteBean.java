package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.model.Cliente;

@Named
@ViewScoped
public class ListaClienteBean extends AbstractBaseListMBean<Cliente> {

    private static final long serialVersionUID = 313141180992233348L;

    private static final String INATIVATED_SUCCESSFULLY = "Cliente inativado com sucesso!";

    @Inject
    private ClienteDao clienteDao;
    private List<Cliente> clientes;
    private List<Cliente> clientesFiltrados;

    public void init() {
        this.clientes = new ArrayList<>();
        this.clientesFiltrados = new ArrayList<>();
        initClientes();
    }

    public void carregar(Integer clienteID) throws Exception {
        setObjetoSessao(clienteID, "ClienteID", "cadastroCliente.xhtml");
    }

    @Transactional
    public void inativar(Cliente cliente) {
        cliente.setAtivo(false);
        this.clienteDao.atualizar(cliente);
        initClientes();
        getContext().addMessage(null, new FacesMessage(INATIVATED_SUCCESSFULLY));
    }

    private void initClientes() {
        this.clientes = this.clienteDao.listarTodos(true);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }

}
