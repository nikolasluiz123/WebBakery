package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.FotoProdutoDao;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.to.TOFotoProduto;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.util.File_Util;
import br.com.WebBakery.validator.ProdutoValidator;

@Named(ProdutoBean.BEAN_NAME)
@ViewScoped
public class ProdutoBean extends AbstractBaseRegisterMBean<TOProduto> {

    public static final String BEAN_NAME = "produtoBean";

    private static final long serialVersionUID = 8861448133925257777L;

    private static final String UPDATED_SUCCESSFULLY = "TOProduto atualizado com sucesso!";

    private static final String REGISTERED_SUCCESSFULLY = "TOProduto cadastrado com sucesso!";

    private TOProduto toProduto;
    @Inject
    private ProdutoDao produtoDao;
    private TOReceita toReceitaSelecionada;
    private List<TOReceita> toReceitas;
    private List<TOReceita> toReceitasFiltradas;
    @Inject
    private ReceitaDao receitaDao;
    @Inject
    private FotoProdutoDao fotoProdutoDao;
    private List<TOFotoProduto> toFotosSelecionadas;
    private ProdutoValidator validator;

    @PostConstruct
    private void init() {
        this.toProduto = new TOProduto();
        this.toFotosSelecionadas = new ArrayList<>();
        this.toReceitaSelecionada = new TOReceita();
        this.toReceitas = new ArrayList<>();
        initReceitas();
    }

    @Transactional
    public void cadastrar() {
        try {
            this.toProduto.setToFotos(toFotosSelecionadas);
            this.validator = new ProdutoValidator(this.toProduto);
            if (this.toProduto.getId() == null) {
                efetuarCadastro();
            } else {
                efetuarAtualizacao();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void efetuarCadastro() throws Exception {
        if (this.validator.isValid()) {
            this.toProduto.setAtivo(true);
            this.produtoDao.cadastrar(this.toProduto);
            cadastrarFotos();
            getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void cadastrarFotos() throws Exception {
        for (TOFotoProduto to : toFotosSelecionadas) {
            this.fotoProdutoDao.cadastrar(to);
        }
        toFotosSelecionadas.clear();
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) throws Exception {
        UploadedFile file = event.getFile();

        TOFotoProduto toFoto = new TOFotoProduto();
        toFoto.setAtivo(true);
        toFoto.setBytes(file.getContents());
        toFoto.setExtensao(File_Util.getExtensao(file.getFileName()));
        toFoto.setNome(file.getFileName());
        toFoto.setToProduto(toProduto);
        toFoto.setTamanho(file.getSize());

        toFotosSelecionadas.add(toFoto);
    }

    private void efetuarAtualizacao() throws Exception {
        if (this.validator.isValid()) {
            this.produtoDao.atualizar(this.toProduto);
            atualizarFotos();
            getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    public boolean getBotaoDesabilitado() {
        return toFotosSelecionadas.isEmpty();
    }

    private void atualizarFotos() throws Exception {
        inativarFotos();
        for (TOFotoProduto to : toFotosSelecionadas) {
            this.fotoProdutoDao.cadastrar(to);
        }
        toFotosSelecionadas.clear();
    }

    private void inativarFotos() {
        this.fotoProdutoDao.inativarFotos(this.toProduto.getId());
    }

    private void atualizarTela() {
        this.toProduto = new TOProduto();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void initReceitas() {
        try {
            this.toReceitas = this.receitaDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setarReceita() {
        this.toProduto.setToReceita(this.toReceitaSelecionada);
    }

    public ProdutoValidator getValidator() {
        return validator;
    }

    public void setValidator(ProdutoValidator validator) {
        this.validator = validator;
    }

    public TOProduto getToProduto() {
        return toProduto;
    }

    public void setToProduto(TOProduto toProduto) {
        this.toProduto = toProduto;
    }

    public TOReceita getToReceitaSelecionada() {
        return toReceitaSelecionada;
    }

    public void setToReceitaSelecionada(TOReceita toReceitaSelecionada) {
        this.toReceitaSelecionada = toReceitaSelecionada;
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

    public List<TOFotoProduto> getToFotosSelecionadas() {
        return toFotosSelecionadas;
    }

    public void setToFotosSelecionadas(List<TOFotoProduto> toFotosSelecionadas) {
        this.toFotosSelecionadas = toFotosSelecionadas;
    }

}
