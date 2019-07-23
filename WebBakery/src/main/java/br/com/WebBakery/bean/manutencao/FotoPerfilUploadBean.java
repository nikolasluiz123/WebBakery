package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.FotoPerfilUsuarioDao;
import br.com.WebBakery.model.Foto;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.Faces_Util;
import br.com.WebBakery.util.File_Util;
import br.com.WebBakery.validator.FotoValidator;

@Named
@SessionScoped
public class FotoPerfilUploadBean extends AbstractBaseRegisterMBean<Foto> {

    private static final String PATH_IMG_DEFAULT = "img/anonimo.png";

    private static final long serialVersionUID = 9124846392202100854L;

    private Foto foto;
    private FotoPerfilUsuarioDao dao;
    private String pathFoto;
    private FotoValidator fotoValidator;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void init() {
        this.foto = new Foto();
        this.dao = new FotoPerfilUsuarioDao();
        getPathFotoPastaTemporaria();
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) throws Exception {
        Usuario u = (Usuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");
        Foto fotoDoBanco = this.dao.getFotoUsuario(u.getId());

        UploadedFile file = event.getFile();
        this.foto.setBytes(file.getContents());
        this.foto.setUsuario(u);
        this.foto.setExtensao(File_Util.getExtensao(file.getFileName()));
        this.foto.setNome(file.getFileName());
        this.foto.setTamanho(file.getSize());
        this.fotoValidator = new FotoValidator(this.foto);

        Boolean isValid = this.fotoValidator.isValid();
        if (fotoDoBanco == null && isValid) {
            this.dao.cadastrar(this.foto);
        } else if (isValid) {
            fotoDoBanco.setBytes(file.getContents());
            this.dao.atualizar(fotoDoBanco);
        }
    }

    public void getPathFotoPastaTemporaria() {
        Usuario u = (Usuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");
        Foto f = this.dao.getFotoUsuario(u.getId());
        if (f != null) {
            String pathCompleto = null;
            try {
                pathCompleto = File_Util.criarFotoPastaTemporaria(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String nomeArquivo = File_Util.getNomeArquivo(pathCompleto);
            String path = File_Util.getPath(nomeArquivo);
            setPathFoto(path);
        } else {
            setPathFoto(PATH_IMG_DEFAULT);
        }
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }
}
