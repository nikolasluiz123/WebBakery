package br.com.WebBakery.to;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOVenda extends AbstractBaseTO {

    private Date data;
    private TOCliente toCliente;
    private TOFuncionario toFuncionario;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TOCliente getToCliente() {
        return toCliente;
    }

    public void setToCliente(TOCliente toCliente) {
        this.toCliente = toCliente;
    }

    public TOFuncionario getToFuncionario() {
        return toFuncionario;
    }

    public void setToFuncionario(TOFuncionario toFuncionario) {
        this.toFuncionario = toFuncionario;
    }

}
