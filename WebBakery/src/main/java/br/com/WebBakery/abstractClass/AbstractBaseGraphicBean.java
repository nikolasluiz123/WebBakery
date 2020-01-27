package br.com.WebBakery.abstractClass;

import java.io.Serializable;
import java.util.List;

import br.com.WebBakery.util.StringUtil;

@SuppressWarnings("serial")
public abstract class AbstractBaseGraphicBean implements Serializable {

    protected static final String DEFAULT_TITTLE_GRAPHICS = "WebBakery Graphic";

    private List<Number> values;
    private List<String> labels;

    protected void addValue(Number number) {
        if (number != null) {
            this.values.add(number);
        }
    }

    protected List<Number> getValues() {
        return values;
    }

    protected void addLabel(String label) {
        if (!StringUtil.isNullOrEmpty(label)) {
            this.labels.add(label);
        }
    }

    protected List<String> getLabels() {
        return labels;
    }

}
