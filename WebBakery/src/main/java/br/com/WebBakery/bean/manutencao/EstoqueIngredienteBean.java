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
import br.com.WebBakery.dao.EstoqueIngredienteDao;
import br.com.WebBakery.dao.IngredienteDao;
import br.com.WebBakery.to.TOEstoqueIngrediente;
import br.com.WebBakery.to.TOIngrediente;
import br.com.WebBakery.validator.EstoqueIngredienteValidator;

@Named(EstoqueIngredienteBean.BEAN_NAME)
@ViewScoped
public class EstoqueIngredienteBean extends AbstractBaseRegisterMBean<TOEstoqueIngrediente> {

    private static final long serialVersionUID = -7367978926865906726L;

    public static final String BEAN_NAME = "estoqueIngredienteBean";

    @Inject
    private EstoqueIngredienteDao estoqueIngredienteDao;

    @Inject
    private IngredienteDao ingredienteDao;
    private TOIngrediente toIngredienteSelecionado;
    private List<TOIngrediente> toIngredientes;
    private List<TOIngrediente> toIngredientesFiltrados;

    @PostConstruct
    private void init() {
        if (getTo() == null) {
            resetTo();
        }

        this.toIngredienteSelecionado = new TOIngrediente();
        this.toIngredientes = new ArrayList<>();
        
        initIngredientes();
    }

    @Transactional
    public void cadastrar() {
        try {
            addValidators();
            if (isValid()) {
                this.getTo().setAtivo(true);
                this.estoqueIngredienteDao.salvar(getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        EstoqueIngredienteValidator estoqueIngredienteValidator = new EstoqueIngredienteValidator(getTo());
        addValidator(estoqueIngredienteValidator);
    }


    private void initIngredientes() {
        try {
            this.toIngredientes = this.ingredienteDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setarIngrediente() {
        getTo().setToIngrediente(toIngredienteSelecionado);
    }
    
    public TOIngrediente getToIngredienteSelecionado() {
        return toIngredienteSelecionado;
    }

    public void setToIngredienteSelecionado(TOIngrediente toIngredienteSelecionado) {
        this.toIngredienteSelecionado = toIngredienteSelecionado;
    }

    public List<TOIngrediente> getToIngredientes() {
        return toIngredientes;
    }

    public void setToIngredientes(List<TOIngrediente> toIngredientes) {
        this.toIngredientes = toIngredientes;
    }

    public List<TOIngrediente> getToIngredientesFiltrados() {
        return toIngredientesFiltrados;
    }

    public void setToIngredientesFiltrados(List<TOIngrediente> toIngredientesFiltrados) {
        this.toIngredientesFiltrados = toIngredientesFiltrados;
    }

    @Override
    protected AbstractBaseDao<TOEstoqueIngrediente> getDao() {
        return estoqueIngredienteDao;
    }

    @Override
    protected TOEstoqueIngrediente getNewInstaceTO() {
        return new TOEstoqueIngrediente();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }
}
