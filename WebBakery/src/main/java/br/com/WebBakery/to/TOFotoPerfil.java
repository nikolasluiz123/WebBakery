package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractArquivoTO;

public class TOFotoPerfil extends AbstractArquivoTO {

    private TOUsuario toUsuario;

    public TOUsuario getToUsuario() {
        return toUsuario;
    }

    public void setToUsuario(TOUsuario toUsuario) {
        this.toUsuario = toUsuario;
    }

}
