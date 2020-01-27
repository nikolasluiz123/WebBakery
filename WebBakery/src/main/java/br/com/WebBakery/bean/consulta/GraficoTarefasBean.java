package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.MeterGaugeChartModel;

import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.FacesUtil;

@Named
@RequestScoped
public class GraficoTarefasBean implements Serializable {

    private static final long serialVersionUID = 2458707065792908799L;
    
    private MeterGaugeChartModel meterGaugeModel2;
    @Inject
    private TarefaDao dao;

    @PostConstruct
    public void init() {
        createMeterGaugeModels();
    }

    public MeterGaugeChartModel getMeterGaugeModel2() {
        return meterGaugeModel2;
    }

    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>();
        
        intervals.add(5);
        intervals.add(15);
        intervals.add(30);
       
        return new MeterGaugeChartModel(dao.getTarefasPendentes((TOUsuario) FacesUtil.getAttributeFromSession("usuarioLogado")), intervals);
    }

    private void createMeterGaugeModels() {
        meterGaugeModel2 = initMeterGaugeModel();
        meterGaugeModel2.setSeriesColors("66cc66,E7E658,cc6666");
        meterGaugeModel2.setGaugeLabelPosition("bottom");
        meterGaugeModel2.setShowTickLabels(false);
        meterGaugeModel2.setIntervalOuterRadius(100);
    }

}
