package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOFuncionario;

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

    @Override
    public void inativar(TOFuncionario to) {
        try {
            to.setAtivo(false);
            to.getToEndereco().setAtivo(false);
            to.getToEndereco().getToLogradouro().setAtivo(false);
            this.funcionarioDao.salvar(to);
            this.enderecoDao.salvar(to.getToEndereco());
            this.logradouroDao.salvar(to.getToEndereco().getToLogradouro());
            initListFuncionarios();
            getContext().addMessage(null, new FacesMessage(FUNCIONARIO_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListFuncionarios() {
        try {
            this.toFuncionarios = this.funcionarioDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
