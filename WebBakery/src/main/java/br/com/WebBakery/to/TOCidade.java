package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOCidade extends AbstractBaseTO {

    private String nome;
    private TOEstado toEstado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TOEstado getToEstado() {
        return toEstado;
    }

    public void setToEstado(TOEstado toEstado) {
        this.toEstado = toEstado;
    }

}
