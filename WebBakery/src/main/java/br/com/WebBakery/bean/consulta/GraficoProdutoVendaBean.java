package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import br.com.WebBakery.dao.ProdutoVendaDao;
import br.com.WebBakery.model.graphics.ProdutoVendaGraphicValues;
import br.com.WebBakery.util.MessagesUtil;
import br.com.WebBakery.util.StringUtil;

@Named
@ViewScoped
public class GraficoProdutoVendaBean implements Serializable {

    private static final long serialVersionUID = 3897043169800518984L;

    private static final String TICK_FORMAT = "%i";
    private static final int MAX_VALUE_AXYS_X = 200;
    private static final int MIN_VALUE_AXYS_X = 0;
    private static final String TITLE = "Produtos mais Vendidos";

    private HorizontalBarChartModel horizontalBarModel;

    @Inject
    private ProdutoVendaDao dao;
    private List<ProdutoVendaGraphicValues> maisProdutivos;

    @PostConstruct
    public void init() {
        this.horizontalBarModel = new HorizontalBarChartModel();
        createHorizontalBarModel();
    }

    private void createHorizontalBarModel() {
        ChartSeries grafico = new ChartSeries();
        this.maisProdutivos = dao.getCincoProdutosMaisVendidos();

        this.maisProdutivos.forEach(p -> {
            grafico.set(p.getNomeProduto(), p.getQuantidadeTotalVendidaProduto());
        });

        this.horizontalBarModel.addSeries(grafico);
        this.horizontalBarModel.setTitle(TITLE);
        this.horizontalBarModel.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setTickFormat(TICK_FORMAT);
        xAxis.setMin(MIN_VALUE_AXYS_X);
        xAxis.setMax(MAX_VALUE_AXYS_X);
    }

    public void itemSelect(ItemSelectEvent event) {
        String message = getMessagePrice(event);
        MessagesUtil.showMessage(FacesMessage.SEVERITY_INFO, message);
    }

    private String getMessagePrice(ItemSelectEvent event) {
        return "Preço total: " + StringUtil.formatToMonetaryValue(maisProdutivos
                .get(event.getItemIndex()).getPrecoTotalVendidoProduto());
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

}
