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
import br.com.WebBakery.bean.manutencao.FuncionarioBean;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.util.Faces_Util;

@Named(ListaFuncionarioBean.BEAN_NAME)
@ViewScoped
public class ListaFuncionarioBean extends AbstractBaseListMBean
        implements IBaseListMBean<TOFuncionario> {

    public static final String BEAN_NAME = "listaFuncionarioBean";

    private static final long serialVersionUID = 5087202394870258058L;

    private static final String FUNCIONARIO_INATIVATED_SUCCESSFULLY = "Funcionário atualizado com sucesso!";

    @Inject
    private FuncionarioDao funcionarioDao;
    private List<TOFuncionario> toFuncionarios;
    private List<TOFuncionario> toFuncionariosFiltrados;
    @Inject
    private EnderecoDao enderecoDao;
    @Inject
    private LogradouroDao logradouroDao;

    @PostConstruct
    private void init() {
        this.toFuncionarios = new ArrayList<>();
        initListFuncionarios();
    }

    @Transactional
    @Override
    public void inativar(TOFuncionario to) {
        try {
            to.setAtivo(false);
            to.getToEndereco().setAtivo(false);
            to.getToEndereco().getToLogradouro().setAtivo(false);
            this.funcionarioDao.atualizar(to);
            this.enderecoDao.atualizar(to.getToEndereco());
            this.logradouroDao.atualizar(to.getToEndereco().getToLogradouro());
            initListFuncionarios();
            getContext().addMessage(null, new FacesMessage(FUNCIONARIO_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void carregar(Integer funcionarioID) throws Exception {
        String keyAtribute = "FuncionarioID";
        String pageRedirect = "cadastroFuncionario.xhtml";
        setObjetoSessao(funcionarioID, keyAtribute, pageRedirect);
        FuncionarioBean registerBean = getRegisterBean();
        registerBean.setToFuncionario(registerBean.getObjetoSessao(keyAtribute, funcionarioDao));
    }

    private void initListFuncionarios() {
        try {
            this.toFuncionarios = this.funcionarioDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FuncionarioBean getRegisterBean() {
        return ((FuncionarioBean) Faces_Util.getBean(FuncionarioBean.BEAN_NAME));
    }

    public List<TOFuncionario> getToFuncionarios() {
        return toFuncionarios;
    }

    public void setToFuncionarios(List<TOFuncionario> toFuncionarios) {
        this.toFuncionarios = toFuncionarios;
    }

    public List<TOFuncionario> getToFuncionariosFiltrados() {
        return toFuncionariosFiltrados;
    }

    public void setToFuncionariosFiltrados(List<TOFuncionario> toFuncionariosFiltrados) {
        this.toFuncionariosFiltrados = toFuncionariosFiltrados;
    }

}
