package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOEndereco extends AbstractBaseTO {

    private TOPais toPais;
    private TOEstado toEstado;
    private TOCidade toCidade;
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
