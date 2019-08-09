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
import br.com.WebBakery.bean.manutencao.ReceitaBean;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.util.Faces_Util;

@Named
@ViewScoped
public class ListaReceitaBean extends AbstractBaseListMBean implements IBaseListMBean<TOReceita> {

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
    public void carregar(Integer receitaID) throws Exception {
        String keyAtribute = "ReceitaID";
        String pageRedirect = "cadastroReceita.xhtml";
        setObjetoSessao(receitaID, keyAtribute, pageRedirect);
        ReceitaBean registerBean = getRegisterBean();
        registerBean.setToReceita(registerBean.getObjetoSessao(keyAtribute, receitaDao));
    }

    @Transactional
    @Override
    public void inativar(TOReceita receita) {
        receita.setAtivo(false);

        try {
            this.receitaDao.atualizar(receita);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initReceitas();
        getContext().addMessage(null, new FacesMessage(RECEITA_INATIVATED_SUCCESSFULLY));
    }

    private void initReceitas() {
        try {
            this.receitas = this.receitaDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private ReceitaBean getRegisterBean() {
        return ((ReceitaBean) Faces_Util.getBean(ReceitaBean.BEAN_NAME));
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

}
