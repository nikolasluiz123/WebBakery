package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOCidade extends AbstractBaseTO {

    @TOEntity(fieldName = "nome")
    private String nome;
    @TOEntity(fieldName = "estado")
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
