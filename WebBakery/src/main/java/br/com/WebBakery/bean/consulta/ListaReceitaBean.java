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
    private List<TOReceita> toReceitas;
    private List<TOReceita> toReceitasFiltradas;

    @PostConstruct
    private void init() {
        this.toReceitas = new ArrayList<>();
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
            this.toReceitas = this.receitaDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOReceita> getToReceitas() {
        return toReceitas;
    }

    public void setToReceitas(List<TOReceita> toReceitas) {
        this.toReceitas = toReceitas;
    }

    public List<TOReceita> getToReceitasFiltradas() {
        return toReceitasFiltradas;
    }

    public void setToReceitasFiltradas(List<TOReceita> toReceitasFiltradas) {
        this.toReceitasFiltradas = toReceitasFiltradas;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
