package br.com.WebBakery.to;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOTarefa extends AbstractBaseTO {

    @TOEntity(fieldName = "dataInicio")
    private Date dataInicio;
    @TOEntity(fieldName = "dataFim")
    private Date dataFim;
    @TOEntity(fieldName = "produto")
    private TOProduto toProduto;
    @TOEntity(fieldName = "quantidade")
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
