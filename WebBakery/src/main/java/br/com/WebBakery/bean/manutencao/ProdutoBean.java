package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.FotoProdutoDao;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.to.TOFotoProduto;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.util.FileUtil;
import br.com.WebBakery.validator.ProdutoValidator;

@Named(ProdutoBean.BEAN_NAME)
@ViewScoped
public class ProdutoBean extends AbstractBaseRegisterMBean<TOProduto> {

    public static final String BEAN_NAME = "produtoBean";

    private static final long serialVersionUID = 8861448133925257777L;

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

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        this.toFotosSelecionadas = new ArrayList<>();
        this.toReceitaSelecionada = new TOReceita();
        this.toReceitas = new ArrayList<>();
        initReceitas();
        buscaFotosParaAlterar();
    }

    @Transactional
    public void cadastrar() {
        try {
            getTo().setToFotos(toFotosSelecionadas);
            addValidators();
            if (isValid()) {
                this.getTo().setAtivo(true);
                this.produtoDao.salvar(this.getTo());
                cadastrarFotos();
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        ProdutoValidator produtoValidator = new ProdutoValidator(this.getTo());
        addValidator(produtoValidator);
    }

    private void cadastrarFotos() throws Exception {
        inativarFotos();
        for (TOFotoProduto to : toFotosSelecionadas) {
            to.setAtivo(true);
            this.fotoProdutoDao.salvar(to);
        }
        toFotosSelecionadas.clear();
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) throws Exception {
        UploadedFile file = event.getFile();

        TOFotoProduto toFoto = new TOFotoProduto();
        toFoto.setAtivo(true);
        toFoto.setBytes(file.getContents());
        toFoto.setExtensao(FileUtil.getExtensao(file.getFileName()));
        toFoto.setNome(file.getFileName());
        toFoto.setToProduto(getTo());
        toFoto.setTamanho(file.getSize());

        toFotosSelecionadas.add(toFoto);
    }

    public boolean getBotaoDesabilitado() {
        return toFotosSelecionadas.isEmpty();
    }

    private void inativarFotos() {
        this.fotoProdutoDao.inativarFotos(this.getTo().getId());
    }

    private void initReceitas() {
        try {
            this.toReceitas = this.receitaDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setarReceita() {
        this.getTo().setToReceita(this.toReceitaSelecionada);
    }

    private void buscaFotosParaAlterar() {
        try {
            if (getTo().getId() != null) {
                this.toFotosSelecionadas = this.fotoProdutoDao.listarFotosProduto(getTo().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    protected AbstractBaseDao<TOProduto> getDao() {
        return produtoDao;
    }

    @Override
    protected TOProduto getNewInstaceTO() {
        return new TOProduto();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
