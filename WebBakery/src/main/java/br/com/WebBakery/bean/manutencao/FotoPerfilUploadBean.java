package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.FotoPerfilUsuarioDao;
import br.com.WebBakery.model.FotoPerfil;
import br.com.WebBakery.to.TOFotoPerfil;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.Faces_Util;
import br.com.WebBakery.util.File_Util;
import br.com.WebBakery.validator.FotoValidator;

@Named(FotoPerfilUploadBean.BEAN_NAME)
@SessionScoped
public class FotoPerfilUploadBean extends AbstractBaseRegisterMBean<FotoPerfil> {

    static final String BEAN_NAME = "fotoPerfilUploadBean";

    private static final String PATH_IMG_DEFAULT = "img/anonimo.png";

    private static final long serialVersionUID = 9124846392202100854L;

    private TOFotoPerfil toFoto;

    @Inject
    private FotoPerfilUsuarioDao dao;
    private String pathFoto;
    private FotoValidator fotoValidator;

    // @PersistenceContext
    // private EntityManager em;

    @PostConstruct
    private void init() {
        this.toFoto = new TOFotoPerfil();
        getPathFotoPastaTemporaria();
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) throws Exception {
        TOUsuario u = (TOUsuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");
        TOFotoPerfil fotoDoBanco = this.dao.getFotoUsuario(u.getId());

        UploadedFile file = event.getFile();
        this.toFoto.setAtivo(true);
        this.toFoto.setBytes(file.getContents());
        this.toFoto.setToUsuario(u);
        this.toFoto.setExtensao(File_Util.getExtensao(file.getFileName()));
        this.toFoto.setNome(file.getFileName());
        this.toFoto.setTamanho(file.getSize());
        this.fotoValidator = new FotoValidator(this.toFoto);

        Boolean isValid = this.fotoValidator.isValid();
        if (fotoDoBanco == null && isValid) {
            this.dao.cadastrar(this.toFoto);
        } else if (isValid) {
            fotoDoBanco.setBytes(file.getContents());
            this.dao.atualizar(fotoDoBanco);
        }
    }

    public void getPathFotoPastaTemporaria() {
        try {
            TOUsuario u = (TOUsuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");
            TOFotoPerfil f = this.dao.getFotoUsuario(u.getId());
            if (f != null) {
                String pathCompleto = File_Util.criarFotoPastaTemporaria(f);
                String nomeArquivo = File_Util.getNomeArquivo(pathCompleto);
                String path = File_Util.getPath(nomeArquivo);
                setPathFoto(path);
            } else {
                setPathFoto(PATH_IMG_DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOFotoPerfil getToFoto() {
        return toFoto;
    }

    public void setToFoto(TOFotoPerfil toFoto) {
        this.toFoto = toFoto;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public FotoValidator getFotoValidator() {
        return fotoValidator;
    }

    public void setFotoValidator(FotoValidator fotoValidator) {
        this.fotoValidator = fotoValidator;
    }

}
