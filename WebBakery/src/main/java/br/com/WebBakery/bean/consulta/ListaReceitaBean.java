package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOReceita;

@Named(ListaReceitaBean.BEAN_NAME)
@ViewScoped
public class ListaReceitaBean extends AbstractBaseListMBean implements IBaseListMBean<TOReceita> {

    public static final String BEAN_NAME = "listaReceitaBean";

    private static final long serialVersionUID = -187265901878598052L;

    private static final String RECEITA_INATIVATED_SUCCESSFULLY = "TOReceita atualizada com sucesso!";

    @Inject
    private ReceitaDao receitaDao;
    private List<TOReceita> receitas;
    private List<TOReceita> receitasFiltradas;

    @PostConstruct
    private void init() {
        this.receitas = new ArrayList<>();
        initReceitas();
    }

    @Override
    public void inativar(TOReceita receita) {
        try {
            receita.setAtivo(false);
            this.receitaDao.salvar(receita);
            initReceitas();
            getContext().addMessage(null, new FacesMessage(RECEITA_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initReceitas() {
        try {
            this.receitas = this.receitaDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<TOReceita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<TOReceita> receitas) {
        this.receitas = receitas;
    }

    public List<TOReceita> getReceitasFiltradas() {
        return receitasFiltradas;
    }

    public void setReceitasFiltradas(List<TOReceita> receitasFiltradas) {
        this.receitasFiltradas = receitasFiltradas;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
