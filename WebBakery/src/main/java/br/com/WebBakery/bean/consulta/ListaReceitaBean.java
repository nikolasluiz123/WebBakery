package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.model.Receita;

@Named
@ViewScoped
public class ListaReceitaBean extends AbstractBaseListMBean<Receita> {

    private static final long serialVersionUID = -187265901878598052L;

    private static final String RECEITA_INATIVATED_SUCCESSFULLY = "Receita atualizada com sucesso!";

    @Inject
    private ReceitaDao receitaDao;
    private List<Receita> receitas;
    private List<Receita> receitasFiltradas;

    public void init() {
        this.receitas = new ArrayList<>();
        initReceitas();
    }

    public void carregar(Integer receitaID) throws IOException {
        setObjetoSessao(receitaID, "ReceitaID", "cadastroReceita.xhtml");
    }

    @Transactional
    public void inativar(Receita receita) {
        receita.setAtivo(false);
        this.receitaDao.atualizar(receita);
        initReceitas();
        getContext().addMessage(null, new FacesMessage(RECEITA_INATIVATED_SUCCESSFULLY));
    }

    private void initReceitas() {
        this.receitas = this.receitaDao.listarTodos(true);
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public List<Receita> getReceitasFiltradas() {
        return receitasFiltradas;
    }

    public void setReceitasFiltradas(List<Receita> receitasFiltradas) {
        this.receitasFiltradas = receitasFiltradas;
    }

}
