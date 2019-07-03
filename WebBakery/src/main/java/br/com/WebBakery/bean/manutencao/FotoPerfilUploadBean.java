package br.com.WebBakery.bean.manutencao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.WebBakery.dao.FotoPerfilUsuarioDao;
import br.com.WebBakery.model.Foto;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.Faces_Util;

@Named
@ViewScoped
public class FotoPerfilUploadBean implements Serializable {

    private static final long serialVersionUID = 9124846392202100854L;

    private Foto foto;
    private FotoPerfilUsuarioDao dao;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void init() {
        this.foto = new Foto();
        this.dao = new FotoPerfilUsuarioDao(this.em);
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) {
        Usuario u = (Usuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");
        Foto fotoDoBanco = this.dao.getFotoUsuario(u.getId());

        UploadedFile file = event.getFile();
        this.foto.setBytes(file.getContents());
        this.foto.setUsuario(u);

        if (fotoDoBanco == null) {
            this.dao.cadastrar(this.foto);
        } else {
            this.dao.atualizar(this.foto);
        }
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

}
