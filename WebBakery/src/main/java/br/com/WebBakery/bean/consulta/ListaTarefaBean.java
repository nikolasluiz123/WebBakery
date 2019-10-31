package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOEstoqueProduto;
import br.com.WebBakery.to.TOTarefa;
import br.com.WebBakery.to.TOUsuario;

@Named(ListaTarefaBean.BEAN_NAME)
@ViewScoped
public class ListaTarefaBean extends AbstractBaseListMBean implements IBaseListMBean<TOTarefa> {

    private static final String USER_NOT_AUTORIZED = "Você não tem autorização para completar tarefas.";

    public static final String BEAN_NAME = "listaTarefaBean";

    private static final long serialVersionUID = 1800431506410605175L;

    private static String COMPLETE_SUCCESSFULLY = "TOTarefa concluída com sucessor!";

    @Inject
    private TarefaDao tarefaDao;
    private List<TOTarefa> tarefasPendentes;
    private List<TOTarefa> tarefasPendentesFiltradas;
    private List<TOTarefa> tarefasConcluidas;
    private List<TOTarefa> tarefasConcuidasFiltradas;

    @Inject
    private EstoqueProdutoDao estoqueProdutoDao;

    @PostConstruct
    private void init() {
        this.tarefasPendentes = new ArrayList<>();
        this.tarefasConcluidas = new ArrayList<>();
        initTarefasPendentes();
        initTarefasConcluidas();
    }

    @Override
    public void inativar(TOTarefa toTarefa) {
        try {
            TOUsuario user = getUserSession();
            if (isAutorized(user)) {
                cadastrarProdutoEstoque(toTarefa);
                toTarefa.setAtivo(false);
                this.tarefaDao.salvar(toTarefa);
                this.tarefasPendentes.remove(toTarefa);
                this.tarefasConcluidas.add(toTarefa);
                getContext().addMessage(null, new FacesMessage(COMPLETE_SUCCESSFULLY));
            } else {
                getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, USER_NOT_AUTORIZED, USER_NOT_AUTORIZED));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAutorized(TOUsuario user) {
        TipoUsuario tipo = user.getTipo();
        
        return tipo == TipoUsuario.PADEIRO || tipo == TipoUsuario.GERENTE;
    }

    private void cadastrarProdutoEstoque(TOTarefa tarefa) {
        try {
            TOEstoqueProduto toEstoqueProduto = new TOEstoqueProduto();
            toEstoqueProduto.setAtivo(true);
            toEstoqueProduto.setToProduto(tarefa.getToProduto());
            toEstoqueProduto.setQuantidade(tarefa.getQuantidade());
            this.estoqueProdutoDao.salvar(toEstoqueProduto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTarefasPendentes() {
        try {
            this.tarefasPendentes = this.tarefaDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTarefasConcluidas() {
        try {
            this.tarefasConcluidas = this.tarefaDao.listarTodos(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOTarefa> getTarefasPendentes() {
        return tarefasPendentes;
    }

    public void setTarefasPendentes(List<TOTarefa> tarefasPendentes) {
        this.tarefasPendentes = tarefasPendentes;
    }

    public List<TOTarefa> getTarefasPendentesFiltradas() {
        return tarefasPendentesFiltradas;
    }

    public void setTarefasPendentesFiltradas(List<TOTarefa> tarefasPendentesFiltradas) {
        this.tarefasPendentesFiltradas = tarefasPendentesFiltradas;
    }

    public List<TOTarefa> getTarefasConcluidas() {
        return tarefasConcluidas;
    }

    public void setTarefasConcluidas(List<TOTarefa> tarefasConcluidas) {
        this.tarefasConcluidas = tarefasConcluidas;
    }

    public List<TOTarefa> getTarefasConcuidasFiltradas() {
        return tarefasConcuidasFiltradas;
    }

    public void setTarefasConcuidasFiltradas(List<TOTarefa> tarefasConcuidasFiltradas) {
        this.tarefasConcuidasFiltradas = tarefasConcuidasFiltradas;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
