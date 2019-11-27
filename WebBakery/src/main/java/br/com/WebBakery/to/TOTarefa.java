package br.com.WebBakery.to;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;
import br.com.WebBakery.util.Date_Util;

public class TOTarefa extends AbstractBaseTO {

    @TOEntity(fieldName = "dataInicio")
    private Date dataInicio;
    @TOEntity(fieldName = "dataFim")
    private Date dataFim;
    @TOEntity(fieldName = "produto")
    private TOProduto toProduto;
    @TOEntity(fieldName = "quantidade")
    private Integer quantidade;
    @TOEntity(fieldName = "padeiro")
    private TOFuncionario toPadeiro;

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

    public TOProduto getToProduto() {
        return toProduto;
    }

    public void setToProduto(TOProduto toProduto) {
        this.toProduto = toProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String dataFormatada(Date data) {
        return Date_Util.formatToString("dd/MM/yyyy", data);
    }

    public TOFuncionario getToPadeiro() {
        return toPadeiro;
    }

    public void setToPadeiro(TOFuncionario toPadeiro) {
        this.toPadeiro = toPadeiro;
    }

}
