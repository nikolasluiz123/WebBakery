package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.model.graphics.FuncionarioGraphicValues;

@Named
@ViewScoped
public class GraficoFuncionariosBean implements Serializable {

    private static final long serialVersionUID = -969025153573931483L;

    private static final String DEFAULT_TITTLE_GRAPHICS = "WebBakery Graphics";

    private BarChartModel graficoFuncionarios;

    @Inject
    private FuncionarioDao dao;

    private List<FuncionarioGraphicValues> maisVendidos;

    @PostConstruct
    private void init() {
        Calendar dataInicial = Calendar.getInstance(new Locale("pt", "BR"));
        dataInicial.set(2019, 9, 26);
        
        Calendar dataFinal = Calendar.getInstance(new Locale("pt", "BR"));
        
        maisVendidos = dao.getCincoFuncionariosQueMaisVendem(dataInicial, dataFinal);
        graficoFuncionarios = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Produtos mais Vendidos");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        maisVendidos.forEach(i -> {
            values.add(i.getQuantidadeVendasRealizadas());
            labels.add(i.getNomeFuncionario());
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
        graficoFuncionarios.setData(data);

        // Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText(DEFAULT_TITTLE_GRAPHICS);
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");

        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        graficoFuncionarios.setOptions(options);
    }

    public BarChartModel getGraficoFuncionarios() {
        return graficoFuncionarios;
    }

}
