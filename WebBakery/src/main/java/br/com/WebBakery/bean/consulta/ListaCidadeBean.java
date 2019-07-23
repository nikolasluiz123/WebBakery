package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.model.Cidade;

@Named
@ViewScoped
public class ListaCidadeBean extends AbstractBaseListMBean<Cidade> {

    private static final String CIDADE_INATIVATED_SUCCESSFULLY = "Cidade inativada com sucesso!";

    private static final long serialVersionUID = 5495188526333831332L;

    private CidadeDao cidadeDao;
    private List<Cidade> cidades;
    private List<Cidade> cidadesFiltradas;

    public void init() {
        this.cidadeDao = new CidadeDao(this.em);
        this.cidades = new ArrayList<>();
        initListCidades();
    }

    public void carregar(Integer cidadeID) throws IOException {
        setObjetoSessao(cidadeID, "CidadeID", "cadastroCidade.xhtml");
    }

    @Transactional
    public void inativar(Cidade cidade) {
        cidade.setAtivo(false);
        this.cidadeDao.atualizar(cidade);
        initListCidades();
        context.addMessage(null, new FacesMessage(CIDADE_INATIVATED_SUCCESSFULLY));
    }

    private void initListCidades() {
        this.cidades = this.cidadeDao.listarTodos(true);
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public List<Cidade> getCidadesFiltradas() {
        return cidadesFiltradas;
    }

    public void setCidadesFiltradas(List<Cidade> cidadesFiltradas) {
        this.cidadesFiltradas = cidadesFiltradas;
    }

}
