package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.model.graphics.ProducaoGraphicValues;

@Named
@ViewScoped
public class GraficoProducaoBean implements Serializable {

    private static final long serialVersionUID = 3897043169800518984L;

    private static final String TICK_FORMAT = "%i";
    private static final String LABEL_PENDING = "Pendentes";
    private static final String LABEL_COMPLETED = "Concluídas";
    private static final int MAX_VALUE_AXYS_X = 200;
    private static final int MIN_VALUE_AXYS_X = 0;
    private static final String TITLE = "Padeiros mais Produtivos";

    private HorizontalBarChartModel horizontalBarModel;

    @Inject
    private TarefaDao dao;

    @PostConstruct
    public void init() {
        createHorizontalBarModel();
    }

    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
        List<ProducaoGraphicValues> maisProdutivos = dao.getCincoPadeirosMaisProdutivos();

        ChartSeries tarefasConcluidas = new ChartSeries();
        tarefasConcluidas.setLabel(LABEL_COMPLETED);
        ChartSeries tarefasPendentes = new ChartSeries();
        tarefasPendentes.setLabel(LABEL_PENDING);

        maisProdutivos.forEach(p -> {
            tarefasConcluidas.set(p.getNomeFuncionario(), p.getQuantidadeTarefasConcluidas());
            tarefasPendentes.set(p.getNomeFuncionario(), p.getQuantidadeTarefasPendentes());
        });

        horizontalBarModel.addSeries(tarefasConcluidas);
        horizontalBarModel.addSeries(tarefasPendentes);

        horizontalBarModel.setTitle(TITLE);
        horizontalBarModel.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setTickFormat(TICK_FORMAT);
        xAxis.setMin(MIN_VALUE_AXYS_X);
        xAxis.setMax(MAX_VALUE_AXYS_X);
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

}
