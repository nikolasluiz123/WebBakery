package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.bean.manutencao.EstoqueProdutoBean;
import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.model.EstoqueProduto;
import br.com.WebBakery.model.Tarefa;

@Named
@ViewScoped
public class ListaTarefaBean extends AbstractBaseListMBean<Tarefa> {

    private static final long serialVersionUID = 1800431506410605175L;

    private static String COMPLETE_SUCCESSFULLY = "Tarefa concluída com sucessor!";

    private TarefaDao tarefaDao;
    private List<Tarefa> tarefasPendentes;
    private List<Tarefa> tarefasPendentesFiltradas;
    private List<Tarefa> tarefasConcluidas;
    private List<Tarefa> tarefasConcuidasFiltradas;

    private EstoqueProdutoBean estoqueProdutoBean;

    public void init() {
        this.tarefaDao = new TarefaDao();
        this.tarefasPendentes = new ArrayList<>();
        this.tarefasConcluidas = new ArrayList<>();
        initTarefasPendentes();
        initTarefasConcluidas();

        this.estoqueProdutoBean = new EstoqueProdutoBean();
    }

    public void carregar(Integer tarefaID) throws Exception {
        setObjetoSessao(tarefaID, "TarefaID", "cadastroTarefa.xhtml");
    }

    @Transactional
    public void concluir(Tarefa tarefa) {
        cadastrarProdutoEstoque(tarefa);
        tarefa.setPendente(false);
        this.tarefaDao.atualizar(tarefa);
        this.tarefasPendentes.remove(tarefa);
        this.tarefasConcluidas.add(tarefa);
        getContext().addMessage(null, new FacesMessage(COMPLETE_SUCCESSFULLY));
    }

    private void cadastrarProdutoEstoque(Tarefa tarefa) {
        EstoqueProduto estoqueProduto = new EstoqueProduto();
        estoqueProduto.setProduto(tarefa.getProduto());
        estoqueProduto.setQuantidade(tarefa.getQuantidade());
        this.estoqueProdutoBean.setEstoqueProduto(estoqueProduto);
        this.estoqueProdutoBean.setEstoqueProdutoDao(new EstoqueProdutoDao());
        this.estoqueProdutoBean.cadastrar();
    }

    private void initTarefasPendentes() {
        this.tarefasPendentes = this.tarefaDao.listarTodos(true);
    }

    private void initTarefasConcluidas() {
        this.tarefasConcluidas = this.tarefaDao.listarTodos(false);
    }

    public List<Tarefa> getTarefasPendentes() {
        return tarefasPendentes;
    }

    public void setTarefasPendentes(List<Tarefa> tarefasPendentes) {
        this.tarefasPendentes = tarefasPendentes;
    }

    public List<Tarefa> getTarefasConcluidas() {
        return tarefasConcluidas;
    }

    public void setTarefasConcluidas(List<Tarefa> tarefasConcuidas) {
        this.tarefasConcluidas = tarefasConcuidas;
    }

    public List<Tarefa> getTarefasPendentesFiltradas() {
        return tarefasPendentesFiltradas;
    }

    public void setTarefasPendentesFiltradas(List<Tarefa> tarefasPendentesFiltradas) {
        this.tarefasPendentesFiltradas = tarefasPendentesFiltradas;
    }

    public List<Tarefa> getTarefasConcuidasFiltradas() {
        return tarefasConcuidasFiltradas;
    }

    public void setTarefasConcuidasFiltradas(List<Tarefa> tarefasConcuidasFiltradas) {
        this.tarefasConcuidasFiltradas = tarefasConcuidasFiltradas;
    }

}
