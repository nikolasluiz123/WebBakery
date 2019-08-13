package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.validator.EstadoValidator;

@Named(EstadoBean.BEAN_NAME)
@ViewScoped
public class EstadoBean extends AbstractBaseRegisterMBean<TOEstado> {

    public static final String BEAN_NAME = "estadoBean";

    private static final long serialVersionUID = -4701039486320007318L;

    @Inject
    private EstadoDao estadoDao;
    @Inject
    private PaisDao paisDao;
    private TOPais toPaisSelecionado;
    private List<TOPais> toPaises;
    private List<TOPais> toPaisesFiltrados;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        this.toPaisSelecionado = new TOPais();
        this.toPaises = new ArrayList<>();

        initListPaises();

    }

    @Transactional
    public void cadastrar() {
        try {
            addValidators();
            if (isValid()) {
                this.getTo().setAtivo(true);
                this.estadoDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        EstadoValidator estadoValidator = new EstadoValidator(getTo());
        addValidator(estadoValidator);
    }

    public void setarPais() {
        this.getTo().setToPais(this.toPaisSelecionado);
    }

    private void initListPaises() {
        try {
            this.toPaises = this.paisDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    protected AbstractBaseDao<TOEstado> getDao() {
        return estadoDao;
    }

    @Override
    protected TOEstado getNewInstaceTO() {
        return new TOEstado();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
