package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;

import br.com.WebBakery.dao.ProdutoVendaDao;
import br.com.WebBakery.model.graphics.ProdutoVendaGraphicValues;
import br.com.WebBakery.util.String_Util;

@Named
@ViewScoped
public class GraficoProdutoVendaBean implements Serializable {

    private static final long serialVersionUID = -4632166949324098113L;

    private BarChartModel graficoProdutosVenda;

    @Inject
    private ProdutoVendaDao dao;

    private List<ProdutoVendaGraphicValues> maisVendidos;

    @PostConstruct
    private void init() {
        maisVendidos = dao.getCincoProdutosMaisVendidos();
        graficoProdutosVenda = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Produtos mais Vendidos");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        maisVendidos.forEach(i -> {
            values.add(i.getQuantidadeTotalVendidaProduto());
            labels.add(i.getNomeProduto());
        });

        barDataSet.setData(values);
        data.setLabels(labels);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgb(2, 205, 178, 0.2)");
        bgColor.add("rgb(0, 139, 84, 0.2)");
        bgColor.add("rgb(100, 4, 84, 0.2)");
        bgColor.add("rgb(251, 4, 134, 0.2)");
        bgColor.add("rgb(100, 166, 0, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(2, 205, 178)");
        borderColor.add("rgb(0, 139, 84)");
        borderColor.add("rgb(100, 4, 84)");
        borderColor.add("rgb(251, 4, 134)");
        borderColor.add("rgb(100, 166, 0)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        // Data
        graficoProdutosVenda.setData(data);

        // Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");

        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
        
        graficoProdutosVenda.setOptions(options);
    }

    public BarChartModel getGraficoProdutosVenda() {
        return graficoProdutosVenda;
    }

    public void itemSelect(ItemSelectEvent event) {
        BigDecimal preco = maisVendidos.get(event.getItemIndex()).getPrecoTotalVendidoProduto();
        String precoFormatado = String_Util.formatToMonetaryValue(preco);
        
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Lucro Bruto: " + precoFormatado,
                                            "Lucro Bruto: " + precoFormatado);

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
