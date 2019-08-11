package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractArquivoTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOFotoPerfil extends AbstractArquivoTO {

    @TOEntity(fieldName = "usuario")
    private TOUsuario toUsuario;

    public TOUsuario getToUsuario() {
        return toUsuario;
    }

    public void setToUsuario(TOUsuario toUsuario) {
        this.toUsuario = toUsuario;
    }

}
