package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.FotoPerfilUsuarioDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.to.TOFotoPerfil;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOPerfil;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.FileUtil;
import br.com.WebBakery.validator.EnderecoValidator;
import br.com.WebBakery.validator.FotoValidator;
import br.com.WebBakery.validator.UsuarioValidator;

@Named(PerfilBean.BEAN_NAME)
@SessionScoped
public class PerfilBean extends AbstractBaseRegisterMBean<TOPerfil> {

    static final String BEAN_NAME = "perfilBean";

    private static final String PATH_IMG_DEFAULT = "img/icon_user.png";

    private static final long serialVersionUID = 9124846392202100854L;

    @Inject
    private FotoPerfilUsuarioDao fotoPerfilDao;
    @Inject
    private FuncionarioDao funcionarioDao;
    @Inject
    private UsuarioDao usuarioDao;
    @Inject
    private EnderecoDao enderecoDao;
    @Inject
    private LogradouroDao logradouroDao;

    private String pathFoto;
    private TOFotoPerfil toFotoPerfil;
    private TOFuncionario toFuncionario;

    @PostConstruct
    private void init() {
        try {
            initializeAttributes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getPathFotoPastaTemporaria();
    }

    private void initializeAttributes() throws Exception {
        if (getTo() == null) {
            resetTo();
        }

        TOUsuario toUsuario = getUserSession();
        this.toFuncionario = this.funcionarioDao.buscarPorIdUsuario(toUsuario.getId());
        // TOFotoPerfil toFotoPerfil =
        // this.fotoPerfilDao.getToFotoPerfil(toUsuario.getId());
    }

    @Transactional
    public void salvar() {
        try {
            getTo().setToFotoPerfil(this.toFotoPerfil);
            addValidators();
            if (isValid()) {
                this.usuarioDao.salvar(getTo().getToFuncionario().getToUsuario());
                this.logradouroDao
                        .salvar(getTo().getToFuncionario().getToEndereco().getToLogradouro());
                this.enderecoDao.salvar(getTo().getToFuncionario().getToEndereco());
                this.funcionarioDao.salvar(getTo().getToFuncionario());
                this.fotoPerfilDao.salvar(getTo().getToFotoPerfil());

                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) throws Exception {
        UploadedFile file = event.getFile();
        // boolean havePhoto = getTo().getToFotoPerfil() != null;

        // if (!havePhoto) {
        createPhoto(file);
        // } else {
        // updatePhoto(file);
        // }
    }

    // private void updatePhoto(UploadedFile file) {
    // getTo().getToFotoPerfil().setBytes(file.getContents());
    // getTo().getToFotoPerfil().setExtensao(FileUtil.getExtensao(file.getFileName()));
    // getTo().getToFotoPerfil().setNome(file.getFileName());
    // getTo().getToFotoPerfil().setTamanho(file.getSize());
    // }

    private void createPhoto(UploadedFile file) {
        TOFotoPerfil toFoto = new TOFotoPerfil();
        toFoto.setAtivo(true);
        toFoto.setBytes(file.getContents());
        toFoto.setToUsuario(this.toFuncionario.getToUsuario());
        toFoto.setExtensao(FileUtil.getExtensao(file.getFileName()));
        toFoto.setNome(file.getFileName());
        toFoto.setTamanho(file.getSize());
        
        setToFotoPerfil(toFoto);
    }

    private void addValidators() {
        FotoValidator fotoValidator = new FotoValidator(getTo().getToFotoPerfil());
        UsuarioValidator usuarioValidator = new UsuarioValidator(getTo().getToFuncionario()
                .getToUsuario(), usuarioDao);
        EnderecoValidator enderecoValidator = new EnderecoValidator(getTo().getToFuncionario()
                .getToEndereco());

        addValidators(fotoValidator, usuarioValidator, enderecoValidator);
    }

    public void getPathFotoPastaTemporaria() {
        try {
            if (getTo().getToFotoPerfil() != null) {
                String pathCompleto = FileUtil.criarFotoPastaTemporaria(getTo().getToFotoPerfil());
                String nomeArquivo = FileUtil.getNomeArquivo(pathCompleto);
                String path = FileUtil.getPath(nomeArquivo);
                setPathFoto(path);
            } else {
                setPathFoto(PATH_IMG_DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AbstractBaseDao<TOPerfil> getDao() {
        return null;
    }

    @Override
    protected TOPerfil getNewInstaceTO() {
        return new TOPerfil();
    }

    public TOFotoPerfil getToFotoPerfil() {
        return toFotoPerfil;
    }

    public void setToFotoPerfil(TOFotoPerfil toFotoPerfil) {
        this.toFotoPerfil = toFotoPerfil;
    }

    public String getPathFoto() {
        return this.pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

    public TOFuncionario getToFuncionario() {
        return toFuncionario;
    }

    public void setToFuncionario(TOFuncionario toFuncionario) {
        this.toFuncionario = toFuncionario;
    }

}
