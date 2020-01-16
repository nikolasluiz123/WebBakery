package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOPerfil extends AbstractBaseTO {

    private TOFuncionario toFuncionario;
    private TOFotoPerfil toFotoPerfil;

    public TOFuncionario getToFuncionario() {
        return toFuncionario;
    }

    public void setToFuncionario(TOFuncionario toFuncionario) {
        this.toFuncionario = toFuncionario;
    }

    public TOFotoPerfil getToFotoPerfil() {
        return toFotoPerfil;
    }

    public void setToFotoPerfil(TOFotoPerfil toFotoPerfil) {
        this.toFotoPerfil = toFotoPerfil;
    }

}
