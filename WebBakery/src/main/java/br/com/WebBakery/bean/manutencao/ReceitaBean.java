package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.IngredienteDao;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.dao.ReceitaIngredienteDao;
import br.com.WebBakery.enums.UnidadeMedida;
import br.com.WebBakery.to.TOIngrediente;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.to.TOReceitaIngrediente;
import br.com.WebBakery.validator.ReceitaValidator;

@Named(ReceitaBean.BEAN_NAME)
@ViewScoped
public class ReceitaBean extends AbstractBaseRegisterMBean<TOReceita> {

    public static final String BEAN_NAME = "receitaBean";

    private static final long serialVersionUID = 4300601613343189689L;

    @Inject
    private ReceitaDao receitaDao;
    @Inject
    private IngredienteDao ingredienteDao;
    @Inject
    private ReceitaIngredienteDao receitaIngredienteDao;

    private List<TOIngrediente> toIngredientes;
    private List<TOIngrediente> toIngredientesSelecionados;
    private List<TOIngrediente> toIngredientesFiltrados;

    private List<TOReceitaIngrediente> toReceitaIngredientes;
    private List<TOReceitaIngrediente> toReceitaIngredientesFiltrados;

    private boolean listaIngredientesIsVisible;

    private boolean isCadastro;

    private Date teste;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();
        carregarReceitaIngrediente();

        if (getTo() == null) {
            resetTo();
        }

        this.toIngredientes = new ArrayList<>();
        initListIngredientes();
    }

    @Transactional
    public void salvar() {
        try {
            addValidators();
            if (isValid()) {
                getTo().setAtivo(true);
                this.isCadastro = getTo().getId() == null;
                this.receitaDao.salvar(getTo());
                this.listaIngredientesIsVisible = true;
            } else {
                showMessagesValidatorChain();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void finalizarReceita() {
        try {
            for (TOReceitaIngrediente to : toReceitaIngredientes) {
                this.receitaIngredienteDao.salvar(to);
            }

            if (isCadastro) {
                showMessage(RECORD_REGISTERED_SUCCESSFULLY);
            } else {
                showMessage(RECORD_UPDATED_SUCCESSFULLY);
            }

            this.listaIngredientesIsVisible = false;
            resetTo();
            this.toIngredientesSelecionados = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarReceitaIngrediente() {
        try {
            if (getTo() != null) {
                this.listaIngredientesIsVisible = true;
                this.toReceitaIngredientes = this.receitaIngredienteDao
                        .listarTodos(true, getTo().getId());
                this.toIngredientesSelecionados = new ArrayList<>();

                for (TOReceitaIngrediente to : toReceitaIngredientes) {
                    this.toIngredientesSelecionados.add(to.getToIngrediente());
                }
            } else {
                this.toReceitaIngredientes = new ArrayList<>();
                this.toIngredientesSelecionados = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void criarReceitaIngrediente() {
        if (toReceitaIngredientes.isEmpty()) {
            for (TOIngrediente to : toIngredientesSelecionados) {
                TOReceitaIngrediente toReceitaIngrediente = new TOReceitaIngrediente();
                toReceitaIngrediente.setAtivo(true);
                toReceitaIngrediente.setToIngrediente(to);
                toReceitaIngrediente.setToReceita(getTo());
                toReceitaIngredientes.add(toReceitaIngrediente);
            }
        }
    }

    private void addValidators() {
        ReceitaValidator validator = new ReceitaValidator(getTo());
        addValidator(validator);
    }

    private void initListIngredientes() {
        try {
            this.toIngredientes = this.ingredienteDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVisibility() {
        if (!listaIngredientesIsVisible) {
            return "collapse";
        } else {
            return "unset";
        }
    }

    public UnidadeMedida[] getUnidadeMedidas() {
        return UnidadeMedida.values();
    }

    public List<TOIngrediente> getToIngredientes() {
        return toIngredientes;
    }

    public void setToIngredientes(List<TOIngrediente> toIngredientes) {
        this.toIngredientes = toIngredientes;
    }

    public List<TOIngrediente> getToIngredientesSelecionados() {
        return toIngredientesSelecionados;
    }

    public void setToIngredientesSelecionados(List<TOIngrediente> toIngredientesSelecionados) {
        this.toIngredientesSelecionados = toIngredientesSelecionados;
    }

    public List<TOIngrediente> getToIngredientesFiltrados() {
        return toIngredientesFiltrados;
    }

    public void setToIngredientesFiltrados(List<TOIngrediente> toIngredientesFiltrados) {
        this.toIngredientesFiltrados = toIngredientesFiltrados;
    }

    public List<TOReceitaIngrediente> getToReceitaIngredientes() {
        return toReceitaIngredientes;
    }

    public void setToReceitaIngredientes(List<TOReceitaIngrediente> toReceitaIngredientes) {
        this.toReceitaIngredientes = toReceitaIngredientes;
    }

    public List<TOReceitaIngrediente> getToReceitaIngredientesFiltrados() {
        return toReceitaIngredientesFiltrados;
    }

    public void setToReceitaIngredientesFiltrados(List<TOReceitaIngrediente> toReceitaIngredientesFiltrados) {
        this.toReceitaIngredientesFiltrados = toReceitaIngredientesFiltrados;
    }

    public Date getTeste() {
        return teste;
    }

    public void setTeste(Date teste) {
        this.teste = teste;
    }

    @Override
    protected AbstractBaseDao<TOReceita> getDao() {
        return receitaDao;
    }

    @Override
    protected TOReceita getNewInstaceTO() {
        return new TOReceita();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
