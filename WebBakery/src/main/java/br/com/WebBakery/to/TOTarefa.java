package br.com.WebBakery.to;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOTarefa extends AbstractBaseTO {

    private Date dataInicio;
    private Date dataFim;
    private TOProduto toProduto;
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

}
