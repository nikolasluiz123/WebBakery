package br.com.WebBakery.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.model.Endereco;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.validator.ClienteValidator;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = -7615567443762847019L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Cliente cliente;
    private ClienteDao clienteDao;

    private PaisDao paisDao;
    private List<Pais> paises;
    private Pais paisSelecionado;

    private EstadoDao estadoDao;
    private List<Estado> estados;
    private Estado estadoSelecionado;

    private CidadeDao cidadeDao;
    private List<Cidade> cidade;
    private Cidade cidadeSelecionada;

    private LogradouroDao logradouroDao;

    private EnderecoDao enderecoDao;
    private List<Endereco> todosOsEnderecos;

    private ClienteValidator validator;

    @PostConstruct
    private void init() {
        this.cliente = new Cliente();
        this.clienteDao = new ClienteDao(this.em);
        this.validator = new ClienteValidator(this.cliente);

        initPaises();
        initEstados();
        initCidades();
        // verificaClienteSessao();
    }

    private void initCidades() {
        // TODO Auto-generated method stub

    }

    private void initEstados() {
        // TODO Auto-generated method stub

    }

    private void initPaises() {
        // TODO Auto-generated method stub

    }

}
