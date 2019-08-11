package br.com.WebBakery.bean.manutencao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.to.TOEndereco;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOLogradouro;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.validator.EnderecoValidator;
import br.com.WebBakery.validator.FuncionarioValidator;

@Named(FuncionarioBean.BEAN_NAME)
@ViewScoped
public class FuncionarioBean extends AbstractBaseRegisterMBean<TOFuncionario> {

    public static final String BEAN_NAME = "funcionarioBean";

    private static final String FUNCIONARIO_UPDATED_SUCCESSFULLY = "Funcionário atualizado com sucesso!";

    private static final String FUNCIONARIO_REGISTRED_SUCCESSFULLY = "TOFuncionario cadastrado com sucesso!";

    private static final long serialVersionUID = -3087884190174464470L;

    @Inject
    private FuncionarioDao funcionarioDao;
    private TOFuncionario toFuncionario;
    private FuncionarioValidator funcionarioValidator;
    private EnderecoValidator enderecoValidator;
    @Inject
    private UsuarioDao usuarioDao;
    private TOUsuario toUsuarioSelecionado;
    private List<TOUsuario> toUsuarios;
    private List<TOUsuario> toUsuariosFiltrados;
    @Inject
    private PaisDao paisDao;
    private TOPais toPaisSelecionado;
    private List<TOPais> toPaises;
    private List<TOPais> toPaisesFiltrados;
    @Inject
    private EstadoDao estadoDao;
    private TOEstado toEstadoSelecionado;
    private List<TOEstado> toEstados;
    private List<TOEstado> toEstadosFiltrados;
    @Inject
    private CidadeDao cidadeDao;
    private TOCidade toCcidadeSelecionada;
    private List<TOCidade> toCidades;
    private List<TOCidade> toCidadesFiltradas;
    @Inject
    private LogradouroDao logradouroDao;
    @Inject
    private EnderecoDao enderecoDao;

    @PostConstruct
    private void init() {
        this.funcionarioDao = new FuncionarioDao();
        this.toFuncionario = new TOFuncionario();

        this.enderecoDao = new EnderecoDao();
        this.toFuncionario.setToEndereco(new TOEndereco());
        this.toFuncionario.getToEndereco().setToPais(new TOPais());
        this.toFuncionario.getToEndereco().setToEstado(new TOEstado());
        this.toFuncionario.getToEndereco().setToCidade(new TOCidade());
        this.toFuncionario.getToEndereco().setToLogradouro(new TOLogradouro());

        this.logradouroDao = new LogradouroDao();

        this.usuarioDao = new UsuarioDao();
        this.toUsuarioSelecionado = new TOUsuario();

        this.paisDao = new PaisDao();
        this.toPaisSelecionado = new TOPais();

        this.estadoDao = new EstadoDao();
        this.toEstadoSelecionado = new TOEstado();

        this.cidadeDao = new CidadeDao();
        this.toCcidadeSelecionada = new TOCidade();

        initListPaises();
        initListEstados();
        initListCidades();
        initListUsuarios();
    }

    @Transactional
    public void cadastrar() {
        try {
            this.enderecoValidator = new EnderecoValidator(this.toFuncionario.getToEndereco());
            this.funcionarioValidator = new FuncionarioValidator(this.toFuncionario);
            if (this.toFuncionario.getId() == null) {
                efetuarCadastro();
            } else {
                efetuarAtualizacao();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void efetuarCadastro() throws Exception {
        if (funcionarioValidator.isValid() && enderecoValidator.isValid()) {
            cadastrarLogradouroFuncionario();
            cadastrarEnderecoFuncionario();
            this.toFuncionario.setAtivo(true);
            this.funcionarioDao.cadastrar(this.toFuncionario);
            getContext().addMessage(null, new FacesMessage(FUNCIONARIO_REGISTRED_SUCCESSFULLY));
        }
    }

    private void cadastrarEnderecoFuncionario() throws Exception {
        this.toFuncionario.getToEndereco().setAtivo(true);
        this.enderecoDao.cadastrar(this.toFuncionario.getToEndereco());
    }

    private void cadastrarLogradouroFuncionario() throws Exception {
        this.toFuncionario.getToEndereco().getToLogradouro().setAtivo(true);
        this.toFuncionario.getToEndereco().getToLogradouro()
                .setToCidade(this.toFuncionario.getToEndereco().getToCidade());
        this.logradouroDao.cadastrar(this.toFuncionario.getToEndereco().getToLogradouro());
    }

    private void efetuarAtualizacao() throws Exception {
        if (funcionarioValidator.isValid() && enderecoValidator.isValid()) {
            this.funcionarioDao.atualizar(this.toFuncionario);
            this.enderecoDao.atualizar(this.toFuncionario.getToEndereco());
            this.logradouroDao.atualizar(this.toFuncionario.getToEndereco().getToLogradouro());
            getContext().addMessage(null, new FacesMessage(FUNCIONARIO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toFuncionario = new TOFuncionario();
        this.funcionarioValidator.showMessages();
        this.funcionarioValidator.clearMessages();
    }

    private void initListUsuarios() {
        try {
            this.toUsuarios = this.usuarioDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListCidades() {
        try {
            this.toCidades = this.cidadeDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListEstados() {
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListPaises() {
        try {
            this.toPaises = this.paisDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setarPais() {
        this.toFuncionario.getToEndereco().setToPais(this.toPaisSelecionado);
        carregarEstado(toPaisSelecionado.getId());
    }

    public void setarEstado() {
        this.toFuncionario.getToEndereco().setToEstado(this.toEstadoSelecionado);
        carregarCidade(toEstadoSelecionado.getId());
    }

    public void setarCidade() {
        this.toFuncionario.getToEndereco().setToCidade(this.toCcidadeSelecionada);
    }

    public void setarUsuario() {
        this.toFuncionario.setToUsuario(this.toUsuarioSelecionado);
    }

    private void carregarEstado(Integer paisId) {
        try {
            this.toEstados = this.estadoDao.listarTodos(true, paisId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarCidade(Integer estadoId) {
        try {
            this.toCidades = this.cidadeDao.listarTodos(true, estadoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOFuncionario getToFuncionario() {
        return toFuncionario;
    }

    public void setToFuncionario(TOFuncionario toFuncionario) {
        this.toFuncionario = toFuncionario;
    }

    public TOUsuario getToUsuarioSelecionado() {
        return toUsuarioSelecionado;
    }

    public void setToUsuarioSelecionado(TOUsuario toUsuarioSelecionado) {
        this.toUsuarioSelecionado = toUsuarioSelecionado;
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

    public TOPais getToPaisSelecionado() {
        return toPaisSelecionado;
    }

    public void setToPaisSelecionado(TOPais toPaisSelecionado) {
        this.toPaisSelecionado = toPaisSelecionado;
    }

    public List<TOPais> getToPaises() {
        return toPaises;
    }

    public void setToPaises(List<TOPais> toPaises) {
        this.toPaises = toPaises;
    }

    public List<TOPais> getToPaisesFiltrados() {
        return toPaisesFiltrados;
    }

    public void setToPaisesFiltrados(List<TOPais> toPaisesFiltrados) {
        this.toPaisesFiltrados = toPaisesFiltrados;
    }

    public TOEstado getToEstadoSelecionado() {
        return toEstadoSelecionado;
    }

    public void setToEstadoSelecionado(TOEstado toEstadoSelecionado) {
        this.toEstadoSelecionado = toEstadoSelecionado;
    }

    public List<TOEstado> getToEstados() {
        return toEstados;
    }

    public void setToEstados(List<TOEstado> toEstados) {
        this.toEstados = toEstados;
    }

    public List<TOEstado> getToEstadosFiltrados() {
        return toEstadosFiltrados;
    }

    public void setToEstadosFiltrados(List<TOEstado> toEstadosFiltrados) {
        this.toEstadosFiltrados = toEstadosFiltrados;
    }

    public TOCidade getToCcidadeSelecionada() {
        return toCcidadeSelecionada;
    }

    public void setToCcidadeSelecionada(TOCidade toCcidadeSelecionada) {
        this.toCcidadeSelecionada = toCcidadeSelecionada;
    }

    public List<TOCidade> getToCidades() {
        return toCidades;
    }

    public void setToCidades(List<TOCidade> toCidades) {
        this.toCidades = toCidades;
    }

    public List<TOCidade> getToCidadesFiltradas() {
        return toCidadesFiltradas;
    }

    public void setToCidadesFiltradas(List<TOCidade> toCidadesFiltradas) {
        this.toCidadesFiltradas = toCidadesFiltradas;
    }

}
