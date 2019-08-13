package br.com.WebBakery.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractArquivo;

@Entity(name = FotoPerfil.TABLE_NAME)
public class FotoPerfil extends AbstractArquivo {

    static final String TABLE_NAME = "foto_perfil";

    private static final String FK_USUARIO_FOTO_PERFIL = Usuario.FK_NAME + "_" + FotoPerfil.TABLE_NAME;
    
    @OneToOne
    @JoinColumn(name = FK_USUARIO_FOTO_PERFIL)
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
