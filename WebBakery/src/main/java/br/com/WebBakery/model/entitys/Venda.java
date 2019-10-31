package br.com.WebBakery.model.entitys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Venda.TABLE_NAME)
public class Venda extends AbstractBaseModel {
    
    public static final String TABLE_NAME = "venda";
    public static final String FK_NAME = "id_venda";

    private static final String FK_FUNCIONARIO_VENDA = Funcionario.FK_NAME + "_" + Venda.TABLE_NAME;
    private static final String FK_CLIENTE_VENDA = Cliente.FK_NAME + "_" + Venda.TABLE_NAME;
    private static final String DATA_VENDA = "data" + "_" + Venda.TABLE_NAME;

    @Column(name = DATA_VENDA)
    private Date data;
    
    @OneToOne
    @JoinColumn(name = FK_CLIENTE_VENDA)
    private Cliente cliente;
    
    @OneToOne
    @JoinColumn(name = FK_FUNCIONARIO_VENDA)
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
