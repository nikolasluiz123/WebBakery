package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.interfaces.IBaseRegisterMBean;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.validator.CidadeValidator;

@Named(CidadeBean.BEAN_NAME)
@ViewScoped
public class CidadeBean extends AbstractBaseRegisterMBean<TOCidade> implements IBaseRegisterMBean {

    public static final String BEAN_NAME = "cidadeBean";

    private static final long serialVersionUID = -1552364059113279585L;

    private static final String CIDADE_UPDATED_SUCCESSFULLY = "Cidade atualizada com sucesso!";

    private static final String CIDADE_REGISTRED_SUCCESSFULLY = "Cidade cadastrada com sucesso!";

    @Inject
    private CidadeDao cidadeDao;
    private TOCidade toCidade;

    @Inject
    private EstadoDao estadoDao;
    private TOEstado toEstadoSelecionado;
    private List<TOEstado> toEstados;
    private List<TOEstado> toEstadosFiltrados;

    private CidadeValidator validator;

    @PostConstruct
    private void init() {
        this.toCidade = new TOCidade();
        this.toEstadoSelecionado = new TOEstado();
        this.toEstados = new ArrayList<>();
        initListEstados();
    }

    @Transactional
    @Override
    public void cadastrar() {
        try {
            this.validator = new CidadeValidator(this.toCidade);
            if (this.toCidade.getId() == null) {
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
        if (this.validator.isValid()) {
            this.toCidade.setAtivo(true);
            this.cidadeDao.cadastrar(this.toCidade);
            getContext().addMessage(null, new FacesMessage(CIDADE_REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() throws Exception {
        if (this.validator.isValid()) {
            this.cidadeDao.atualizar(this.toCidade);
            getContext().addMessage(null, new FacesMessage(CIDADE_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toCidade = new TOCidade();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    public void setarEstado() {
        this.toCidade.setToEstado(this.toEstadoSelecionado);
    }

    private void initListEstados() {
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOCidade getToCidade() {
        return toCidade;
    }

    public void setToCidade(TOCidade toCidade) {
        this.toCidade = toCidade;
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

}
