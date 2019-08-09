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
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOLogradouro;

@Named
@ViewScoped
public class ListaLogradouroBean extends AbstractBaseListMBean
        implements IBaseListMBean<TOLogradouro> {

    private static final long serialVersionUID = -5398966780316383519L;

    private static final String LOGRADOURO_INATIVATED_SUCCESSFULLY = "Logradouro inativado com sucesso!";

    @Inject
    private LogradouroDao logradouroDao;
    private TOLogradouro logradouro;
    private List<TOLogradouro> logradouros;
    private List<TOLogradouro> logradourosFiltrados;

    @PostConstruct
    private void init() {
        this.logradouro = new TOLogradouro();
        this.logradouros = new ArrayList<>();
        initListLogradouros();
    }

    @Transactional
    @Override
    public void inativar(TOLogradouro logradouro) {
        logradouro.setAtivo(false);

        try {
            this.logradouroDao.atualizar(logradouro);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initListLogradouros();
        getContext().addMessage(null, new FacesMessage(LOGRADOURO_INATIVATED_SUCCESSFULLY));
    }

    // Não tem formulário para cadastrar logradouros, só é cadastrado um
    // logradouro quando um funcionario ou cliente for cadastrado e os campos de
    // endereço forem preenchidos
    @Override
    public void carregar(Integer id) throws Exception {
        // TODO Auto-generated method stub
    }

    private void initListLogradouros() {
        try {
            this.logradouros = this.logradouroDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOLogradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(TOLogradouro logradouro) {
        this.logradouro = logradouro;
    }

    public List<TOLogradouro> getLogradouros() {
        return logradouros;
    }

    public void setLogradouros(List<TOLogradouro> logradouros) {
        this.logradouros = logradouros;
    }

    public List<TOLogradouro> getLogradourosFiltrados() {
        return logradourosFiltrados;
    }

    public void setLogradourosFiltrados(List<TOLogradouro> logradourosFiltrados) {
        this.logradourosFiltrados = logradourosFiltrados;
    }

}
