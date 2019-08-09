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
import br.com.WebBakery.bean.manutencao.CidadeBean;
import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.util.Faces_Util;

@Named(ListaCidadeBean.BEAN_NAME)
@ViewScoped
public class ListaCidadeBean extends AbstractBaseListMBean implements IBaseListMBean<TOCidade> {

    public static final String BEAN_NAME = "listaCidadeBean";

    private static final String CIDADE_INATIVATED_SUCCESSFULLY = "Cidade inativada com sucesso!";

    private static final long serialVersionUID = 5495188526333831332L;

    @Inject
    private CidadeDao cidadeDao;
    private List<TOCidade> toCidades;
    private List<TOCidade> toCidadesFiltradas;

    @PostConstruct
    private void init() {
        this.toCidades = new ArrayList<>();
        initListCidades();
    }

    @Override
    public void carregar(Integer cidadeID) throws Exception {
        String keyAtribute = "CidadeID";
        String pageRedirect = "cadastroCidade.xhtml";
        setObjetoSessao(cidadeID, keyAtribute, pageRedirect);
        CidadeBean registerBean = getRegisterBean();
        registerBean.setToCidade(registerBean.getObjetoSessao(keyAtribute, cidadeDao));
    }

    @Transactional
    @Override
    public void inativar(TOCidade to) {
        try {
            to.setAtivo(false);
            this.cidadeDao.atualizar(to);
            initListCidades();
            getContext().addMessage(null, new FacesMessage(CIDADE_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListCidades() {
        try {
            this.toCidades = this.cidadeDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CidadeBean getRegisterBean() {
        return ((CidadeBean) Faces_Util.getBean(CidadeBean.BEAN_NAME));
    }

    public List<TOCidade> getToCidades() {
        return toCidades;
    }

    public void setToCidades(List<TOCidade> toCidades) {
        this.toCidades = toCidades;
    }

    public List<TOCidade> getToCidadesFiltradas() {
        return toCidadesFiltradas;
    }

    public void setToCidadesFiltradas(List<TOCidade> toCidadesFiltradas) {
        this.toCidadesFiltradas = toCidadesFiltradas;
    }

}
