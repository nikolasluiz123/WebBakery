package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
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

    @Inject
    private CidadeDao cidadeDao;
    @Inject
    private EstadoDao estadoDao;
    private TOEstado toEstadoSelecionado;
    private List<TOEstado> toEstados;
    private List<TOEstado> toEstadosFiltrados;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        this.toEstadoSelecionado = new TOEstado();
        this.toEstados = new ArrayList<>();
        initListEstados();
    }

    @Override
    public void salvar() {
        try {
            addValidators();
            if (isValid()) {
                getTo().setAtivo(true);
                this.cidadeDao.salvar(getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        CidadeValidator cidadeValidator = new CidadeValidator(getTo());
        addValidator(cidadeValidator);
    }

    public void setarEstado() {
        getTo().setToEstado(this.toEstadoSelecionado);
    }

    private void initListEstados() {
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AbstractBaseDao<TOCidade> getDao() {
        return this.cidadeDao;
    }

    @Override
    protected TOCidade getNewInstaceTO() {
        return new TOCidade();
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

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
