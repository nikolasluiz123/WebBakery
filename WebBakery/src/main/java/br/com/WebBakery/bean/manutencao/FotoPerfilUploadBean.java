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
import br.com.WebBakery.dao.FotoPerfilUsuarioDao;
import br.com.WebBakery.to.TOFotoPerfil;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.Faces_Util;
import br.com.WebBakery.util.File_Util;
import br.com.WebBakery.validator.FotoValidator;

@Named(FotoPerfilUploadBean.BEAN_NAME)
@SessionScoped
public class FotoPerfilUploadBean extends AbstractBaseRegisterMBean<TOFotoPerfil> {

    static final String BEAN_NAME = "fotoPerfilUploadBean";

    private static final String PATH_IMG_DEFAULT = "img/icon_user.png";

    private static final long serialVersionUID = 9124846392202100854L;

    @Inject
    private FotoPerfilUsuarioDao dao;
    private String pathFoto;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        getPathFotoPastaTemporaria();
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) throws Exception {
        TOUsuario u = (TOUsuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");
        TOFotoPerfil fotoDoBanco = this.dao.getFotoUsuario(u.getId());

        UploadedFile file = event.getFile();
        getTo().setAtivo(true);
        getTo().setBytes(file.getContents());
        getTo().setToUsuario(u);
        getTo().setExtensao(File_Util.getExtensao(file.getFileName()));
        getTo().setNome(file.getFileName());
        getTo().setTamanho(file.getSize());

        addValidators();
        boolean isValid = isValid();

        if (fotoDoBanco == null && isValid) {
            this.dao.salvar(getTo());
        } else if (isValid) {
            fotoDoBanco.setBytes(file.getContents());
            this.dao.salvar(fotoDoBanco);
        }
    }

    private void addValidators() {
        FotoValidator fotoValidator = new FotoValidator(getTo());
        addValidator(fotoValidator);
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

    @Override
    protected AbstractBaseDao<TOFotoPerfil> getDao() {
        return dao;
    }

    @Override
    protected TOFotoPerfil getNewInstaceTO() {
        return new TOFotoPerfil();
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
