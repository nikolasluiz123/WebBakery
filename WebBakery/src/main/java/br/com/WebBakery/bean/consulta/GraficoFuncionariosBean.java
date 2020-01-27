package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.model.graphics.FuncionarioGraphicValues;

@Named
@ViewScoped
public class GraficoFuncionariosBean implements Serializable {

    private static final long serialVersionUID = -969025153573931483L;

    private static final String TICK_FORMAT = "%i";
    private static final int MAX_VALUE_AXYS_X = 200;
    private static final int MIN_VALUE_AXYS_X = 0;
    private static final String TITLE = "Caixar qua mais Realizaram Vendas";

    private HorizontalBarChartModel horizontalBarModel;

    @Inject
    private FuncionarioDao dao;

    private List<FuncionarioGraphicValues> funcionariosRealizaramMaisVendas;

    @PostConstruct
    public void init() {
        this.horizontalBarModel = new HorizontalBarChartModel();
        createHorizontalBarModel();
    }

    private void createHorizontalBarModel() {
        ChartSeries grafico = new ChartSeries();

        Calendar dataInicial = Calendar.getInstance(new Locale("pt", "BR"));
        dataInicial.set(2019, 9, 26);

        Calendar dataFinal = Calendar.getInstance(new Locale("pt", "BR"));

        this.funcionariosRealizaramMaisVendas = dao.getCincoFuncionariosQueMaisVendem(dataInicial, dataFinal);

        this.funcionariosRealizaramMaisVendas.forEach(p -> {
            grafico.set(p.getNomeFuncionario(), p.getQuantidadeVendasRealizadas());
        });

        this.horizontalBarModel.addSeries(grafico);
        this.horizontalBarModel.setTitle(TITLE);
        this.horizontalBarModel.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setTickFormat(TICK_FORMAT);
        xAxis.setMin(MIN_VALUE_AXYS_X);
        xAxis.setMax(MAX_VALUE_AXYS_X);
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

}
