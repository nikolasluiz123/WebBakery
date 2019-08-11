package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOEndereco extends AbstractBaseTO {

    @TOEntity(fieldName = "pais")
    private TOPais toPais;
    @TOEntity(fieldName = "estado")
    private TOEstado toEstado;
    @TOEntity(fieldName = "cidade")
    private TOCidade toCidade;
    @TOEntity(fieldName = "logradouro")
    private TOLogradouro toLogradouro;

    public TOPais getToPais() {
        return toPais;
    }

    public void setToPais(TOPais toPais) {
        this.toPais = toPais;
    }

    public TOEstado getToEstado() {
        return toEstado;
    }

    public void setToEstado(TOEstado toEstado) {
        this.toEstado = toEstado;
    }

    public TOCidade getToCidade() {
        return toCidade;
    }

    public void setToCidade(TOCidade toCidade) {
        this.toCidade = toCidade;
    }

    public TOLogradouro getToLogradouro() {
        return toLogradouro;
    }

    public void setToLogradouro(TOLogradouro toLogradouro) {
        this.toLogradouro = toLogradouro;
    }

}
