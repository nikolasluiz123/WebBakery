package br.com.WebBakery.abstractClass;

import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;

import br.com.WebBakery.util.DoubleUtil;
import br.com.WebBakery.util.StringUtil;

@SuppressWarnings("serial")
public abstract class AbstractVerticalGraphicBean extends AbstractBaseGraphicBean {

    private static final int DEFAULT_LEGEND_LABELS_FONT_SIZE = 24;
    private static final String DEFAULT_LEGEND_LABELS_FONT_COLOR = "#2980B9";
    private static final String DEFAULT_LEGEND_LABELS_FONT_STYLE = "bold";

    private static final int DEFAULT_BORDER_WIDTH = 1;

    private static final String DEFAULT_RGB_5 = "rgb(100, 166, 0)";
    private static final String DEFAULT_RGB_4 = "rgb(251, 4, 134)";
    private static final String DEFAULT_RGB_3 = "rgb(100, 4, 84)";
    private static final String DEFAULT_RGB_2 = "rgb(232, 12, 12)";
    private static final String DEFAULT_RGB_1 = "rgb(247, 137, 12)";

    private static final String DEFAULT_RGBA_5 = "rgb(100, 166, 0, 0.4)";
    private static final String DEFAULT_RGBA_4 = "rgb(251, 4, 134, 0.4)";
    private static final String DEFAULT_RGBA_3 = "rgb(100, 4, 84, 0.4)";
    private static final String DEFAULT_RGBA_2 = "rgb(232, 12, 12, 0.4)";
    private static final String DEFAULT_RGBA_1 = "rgb(247, 137, 12, 0.4)";

    private static final String DEFAULT_LEGEND_POSITION = "top";

    private BarChartModel barChartModel;
    private ChartData chartData;

    private List<String> bgColor;
    private List<String> borderColor;

    private BarChartDataSet barDataSet;

    private BarChartOptions options;
    private CartesianScales cScales;
    private CartesianLinearAxes linearAxes;
    private CartesianLinearTicks ticks;

    private Legend legend;
    private LegendLabel legendLabels;

    public AbstractVerticalGraphicBean() {
        this.barChartModel = new BarChartModel();
        this.chartData = new ChartData();

        this.barDataSet = new BarChartDataSet();
        barDataSet.setBorderWidth(DEFAULT_BORDER_WIDTH);

        this.options = new BarChartOptions();
        this.cScales = new CartesianScales();
        this.linearAxes = new CartesianLinearAxes();
        this.ticks = new CartesianLinearTicks();

        this.legend = new Legend();
        this.legend.setPosition(DEFAULT_LEGEND_POSITION);

        this.legendLabels = new LegendLabel();
    }

    protected void showLegend(Boolean show) {
        this.legend.setDisplay(show);
    }

    protected void setLegendPosition(String position) {
        if (!StringUtil.isNullOrEmpty(position)) {
            this.legend.setPosition(position);
        }
    }

    protected void setFontStyleLegendLabels(String style) {
        if (!StringUtil.isNullOrEmpty(style)) {
            this.legendLabels.setFontStyle(style);
        }
    }

    protected void setFontColor(String hex) {
        if (!StringUtil.isNullOrEmpty(hex)) {
            this.legendLabels.setFontColor(hex);
        }
    }

    protected void setFontSize(Integer size) {
        this.legendLabels.setFontSize(size);
    }

    protected void setBeginAtZero(Boolean atZero) {
        if (atZero != null) {
            this.ticks.setBeginAtZero(atZero);
        }
    }

    protected void setTitle(String title) {
        if (!StringUtil.isNullOrEmpty(title)) {
            this.barDataSet.setLabel(title);
        }
    }

    private String getTitle() {
        return this.barDataSet.getLabel();
    }

    protected BarChartModel getBarChartModel() {
        return barChartModel;
    }

    protected void addColor(String rgba) {
        if (!StringUtil.isNullOrEmpty(rgba)) {
            this.bgColor.add(rgba);
        }
    }

    protected List<String> getBgColors() {
        return bgColor;
    }

    protected void addBorderColor(String rgb) {
        if (!StringUtil.isNullOrEmpty(rgb)) {
            this.borderColor.add(rgb);
        }
    }

    protected List<String> getBorderColors() {
        return borderColor;
    }

    protected BarChartDataSet getBarDataSet() {
        return barDataSet;
    }

    protected void setBorderWidth(Integer borderWidth) {
        if (borderWidth != null) {
            barDataSet.setBorderWidth(borderWidth);
        } else {
            barDataSet.setBorderWidth(DEFAULT_BORDER_WIDTH);
        }
    }

    protected void buildGraphic() {
        if (StringUtil.isNullOrEmpty(getTitle())) {
            setTitle(DEFAULT_TITTLE_GRAPHICS);
        }

        if (getValues().size() == 0) {
            addDefaultValues();
        }

        if (getLabels().size() == 0) {
            addDefaultLabels();
        }

        barDataSet.setData(getValues());
        chartData.setLabels(getLabels());

        if (getBgColors().size() == 0) {
            addDefaultBgColors();
        }

        barDataSet.setBackgroundColor(getBgColors());

        if (getBorderColors().size() == 0) {
            addDefaultBorderColors();
        }

        barDataSet.setBorderColor(getBorderColors());
        chartData.addChartDataSet(barDataSet);

        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        
        if (legendLabels.getFontStyle() == null) {
            legendLabels.setFontStyle(DEFAULT_LEGEND_LABELS_FONT_STYLE);
        }
        
        if (legendLabels.getFontColor() == null) {
            legendLabels.setFontColor(DEFAULT_LEGEND_LABELS_FONT_COLOR);
        }
        
        if (legendLabels.getFontSize() == null) {
            legendLabels.setFontSize(DEFAULT_LEGEND_LABELS_FONT_SIZE);
        }
        
        legend.setLabels(legendLabels);
        options.setLegend(legend);
        
        barChartModel.setOptions(options);
    }

    private void addDefaultBgColors() {
        bgColor.add(DEFAULT_RGBA_1);
        bgColor.add(DEFAULT_RGBA_2);
        bgColor.add(DEFAULT_RGBA_3);
        bgColor.add(DEFAULT_RGBA_4);
        bgColor.add(DEFAULT_RGBA_5);
    }

    private void addDefaultBorderColors() {
        borderColor.add(DEFAULT_RGB_1);
        borderColor.add(DEFAULT_RGB_2);
        borderColor.add(DEFAULT_RGB_3);
        borderColor.add(DEFAULT_RGB_4);
        borderColor.add(DEFAULT_RGB_5);
    }

    private void addDefaultValues() {
        for (int i = 0; i < 5; i++) {
            Double value = DoubleUtil.format(((Math.random() * ((200 - 1) + 1)) + 1));
            addValue(value);
        }
    }

    private void addDefaultLabels() {
        for (int i = 1; i < 6; i++) {
            addLabel("Label " + i);
        }
    }

}
