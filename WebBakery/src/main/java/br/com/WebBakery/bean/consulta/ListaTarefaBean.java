package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.bean.manutencao.EstoqueProdutoBean;
import br.com.WebBakery.bean.manutencao.TarefaBean;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOEstoqueProduto;
import br.com.WebBakery.to.TOTarefa;
import br.com.WebBakery.util.Faces_Util;

@Named(ListaTarefaBean.LIST_TAREFA_BEAN)
@ViewScoped
public class ListaTarefaBean extends AbstractBaseListMBean implements IBaseListMBean<TOTarefa> {

    public static final String LIST_TAREFA_BEAN = "listaTarefaBean";

    private static final long serialVersionUID = 1800431506410605175L;

    private static String COMPLETE_SUCCESSFULLY = "TOTarefa concluída com sucessor!";

    @Inject
    private TarefaDao tarefaDao;
    private List<TOTarefa> tarefasPendentes;
    private List<TOTarefa> tarefasPendentesFiltradas;
    private List<TOTarefa> tarefasConcluidas;
    private List<TOTarefa> tarefasConcuidasFiltradas;

    private EstoqueProdutoBean estoqueProdutoBean;

    @PostConstruct
    private void init() {
        this.tarefasPendentes = new ArrayList<>();
        this.tarefasConcluidas = new ArrayList<>();
        initTarefasPendentes();
        initTarefasConcluidas();
        this.estoqueProdutoBean = new EstoqueProdutoBean();
    }

    @Override
    public void carregar(Integer tarefaID) throws Exception {
        String keyAtribute = "TarefaID";
        String pageRedirect = "cadastroTarefa.xhtml";
        setObjetoSessao(tarefaID, keyAtribute, pageRedirect);
        TarefaBean registerBean = getRegisterBean();
        registerBean.setToTarefa(registerBean.getObjetoSessao(keyAtribute, tarefaDao));
    }

    @Transactional
    @Override
    public void inativar(TOTarefa tarefa) {
        try {
            cadastrarProdutoEstoque(tarefa);
            tarefa.setAtivo(false);
            this.tarefaDao.atualizar(tarefa);
            this.tarefasPendentes.remove(tarefa);
            this.tarefasConcluidas.add(tarefa);
            getContext().addMessage(null, new FacesMessage(COMPLETE_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cadastrarProdutoEstoque(TOTarefa tarefa) {
        TOEstoqueProduto estoqueProduto = new TOEstoqueProduto();
        estoqueProduto.setToProduto(tarefa.getToProduto());
        estoqueProduto.setQuantidade(tarefa.getQuantidade());
        this.estoqueProdutoBean.setToEstoqueProduto(estoqueProduto);
        this.estoqueProdutoBean.cadastrar();
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

    private TarefaBean getRegisterBean() {
        return ((TarefaBean) Faces_Util.getBean(TarefaBean.BEAN_NAME));
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

}
