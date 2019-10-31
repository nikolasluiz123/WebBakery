package br.com.WebBakery.abstractClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public abstract class AbstractBaseGraphicBean implements Serializable {
    
    public static final String DEFAULT_TITTLE_GRAPHICS = "WebBakery Graphics";
    
    public List<Number> values = new ArrayList<>();
    public List<String> labels = new ArrayList<>();
    public List<String> bgColor = new ArrayList<>();
    public List<String> borderColor = new ArrayList<>();

}
