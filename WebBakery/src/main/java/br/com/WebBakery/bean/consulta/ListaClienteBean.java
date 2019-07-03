package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.util.Faces_Util;

@Named
@ViewScoped
public class ListaClienteBean implements Serializable {

    private static final long serialVersionUID = 313141180992233348L;

    private static final String INATIVATED_SUCCESSFULLY = "Cliente inativado com sucesso!";

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private ClienteDao clienteDao;
    private List<Cliente> clientes;
    private List<Cliente> clientesFiltrados;

    @PostConstruct
    private void init() {
        this.clienteDao = new ClienteDao(this.em);
        this.clientes = new ArrayList<>();
        this.clientesFiltrados = new ArrayList<>();
        initClientes();
    }

    public void carregar(Integer clienteID) throws Exception {
        HttpSession session = Faces_Util.getHTTPSession();
        session.setAttribute("ClienteID", clienteID);
        context.getExternalContext().redirect("cadastroCliente.xhtml");
    }

    @Transactional
    public void inativar(Cliente cliente) {
        cliente.setAtivo(false);
        this.clienteDao.atualizar(cliente);
        initClientes();
        context.addMessage(null, new FacesMessage(INATIVATED_SUCCESSFULLY));
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
