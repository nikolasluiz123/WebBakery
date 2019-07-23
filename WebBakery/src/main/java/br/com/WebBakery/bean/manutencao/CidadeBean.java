package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.validator.CidadeValidator;

@Named
@ViewScoped
public class CidadeBean extends AbstractBaseRegisterMBean<Cidade> {

    private static final long serialVersionUID = -1552364059113279585L;

    private static final String CIDADE_UPDATED_SUCCESSFULLY = "Cidade atualizada com sucesso!";

    private static final String CIDADE_REGISTRED_SUCCESSFULLY = "Cidade cadastrada com sucesso!";

    private CidadeDao cidadeDao;
    private Cidade cidade;

    private EstadoDao estadoDao;
    private Estado estadoSelecionado;
    private List<Estado> estados;
    private List<Estado> estadosFiltrados;

    private CidadeValidator validator;

    @Override
    public void init() {
        this.cidadeDao = new CidadeDao(this.em);
        this.cidade = new Cidade();

        this.estadoDao = new EstadoDao(this.em);
        this.estadoSelecionado = new Estado();
        this.estados = new ArrayList<>();
        initListEstados();
        verificaCidadeSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new CidadeValidator(this.cidade);
        if (this.cidade.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.cidade.setAtivo(true);
            this.cidadeDao.cadastrar(this.cidade);
            context.addMessage(null, new FacesMessage(CIDADE_REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.cidadeDao.atualizar(this.cidade);
            context.addMessage(null, new FacesMessage(CIDADE_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.cidade = new Cidade();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaCidadeSessao() {
        this.cidade = getObjetoSessao("CidadeID", cidadeDao, cidade);
    }

    public void setarEstado() {
        this.cidade.setEstado(this.estadoSelecionado);
    }

    private void initListEstados() {
        this.estados = this.estadoDao.listarTodos(true);
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Estado getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Estado estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Estado> getEstadosFiltrados() {
        return estadosFiltrados;
    }

    public void setEstadosFiltrados(List<Estado> estadosFiltrados) {
        this.estadosFiltrados = estadosFiltrados;
    }

}
