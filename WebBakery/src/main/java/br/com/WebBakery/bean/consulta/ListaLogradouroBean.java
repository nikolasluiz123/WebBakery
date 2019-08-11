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

@Named(ListaLogradouroBean.BEAN_NAME)
@ViewScoped
public class ListaLogradouroBean extends AbstractBaseListMBean
        implements IBaseListMBean<TOLogradouro> {

    static final String BEAN_NAME = "listaLogradouroBean";

    private static final long serialVersionUID = -5398966780316383519L;

    private static final String LOGRADOURO_INATIVATED_SUCCESSFULLY = "Logradouro inativado com sucesso!";

    @Inject
    private LogradouroDao logradouroDao;
    private List<TOLogradouro> toLogradouros;
    private List<TOLogradouro> toLogradourosFiltrados;

    @PostConstruct
    private void init() {
        this.toLogradouros = new ArrayList<>();
        initListLogradouros();
    }

    @Transactional
    @Override
    public void inativar(TOLogradouro logradouro) {
        try {
            logradouro.setAtivo(false);
            this.logradouroDao.atualizar(logradouro);
            initListLogradouros();
            getContext().addMessage(null, new FacesMessage(LOGRADOURO_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Não tem formulário para cadastrar logradouros, só é cadastrado um
    // toLogradouro quando um funcionario ou cliente for cadastrado e os campos
    // de
    // endereço forem preenchidos
    @Override
    public void carregar(Integer id) throws Exception {
        // TODO Auto-generated method stub
    }

    @Transactional
    private void initListLogradouros() {
        try {
            this.toLogradouros = this.logradouroDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOLogradouro> getToLogradouros() {
        return toLogradouros;
    }

    public void setToLogradouros(List<TOLogradouro> toLogradouros) {
        this.toLogradouros = toLogradouros;
    }

    public List<TOLogradouro> getToLogradourosFiltrados() {
        return toLogradourosFiltrados;
    }

    public void setToLogradourosFiltrados(List<TOLogradouro> toLogradourosFiltrados) {
        this.toLogradourosFiltrados = toLogradourosFiltrados;
    }

}
