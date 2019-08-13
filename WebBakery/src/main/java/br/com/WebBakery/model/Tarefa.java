package br.com.WebBakery.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Tarefa.TABLE_NAME)
public class Tarefa extends AbstractBaseModel {

    public static final String TABLE_NAME = "tarefa";

    private static final String QUANTIDADE_TAREFA = "quantidade" + "_" + Tarefa.TABLE_NAME;
    private static final String FK_PRODUTO_TAREFA = Produto.FK_NAME + "_" + Tarefa.TABLE_NAME;
    private static final String DATA_FIM_TAREFA = "data_fim" + "_" + Tarefa.TABLE_NAME;
    private static final String DATA_INICIO_TAREFA = "data_inicio" + "_" + Tarefa.TABLE_NAME;

    @Column(name = DATA_INICIO_TAREFA)
    private Date dataInicio;

    @Column(name = DATA_FIM_TAREFA)
    private Date dataFim;

    @OneToOne
    @JoinColumn(name = FK_PRODUTO_TAREFA)
    private Produto produto;

    @Column(name = QUANTIDADE_TAREFA)
    private Integer quantidade;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
