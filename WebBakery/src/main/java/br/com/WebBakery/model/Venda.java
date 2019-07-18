package br.com.WebBakery.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity
public class Venda extends AbstractBaseModel {

    private Date data;
    @OneToOne
    private Cliente cliente;
    @OneToOne
    private Funcionario funcionario;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
