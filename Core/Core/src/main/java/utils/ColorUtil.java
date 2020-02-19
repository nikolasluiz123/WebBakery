package utils;

import java.awt.Color;

public class ColorUtil {

    private static final String DECIMAL_SEPARATOR = ",";
    private static final String OPEN_PARENTESE = "(";
    private static final String CLOSE_PARENTESE = ")";
    private static final String RGBA = "RGBA";

    public static String getColor(int red, int green, int blue, int alpha) {
        Color color = new Color(red, green, blue, alpha);
        
        return RGBA + OPEN_PARENTESE + 
                      color.getRed() + DECIMAL_SEPARATOR + 
                      color.getGreen() + DECIMAL_SEPARATOR + 
                      color.getBlue() + DECIMAL_SEPARATOR + 
                      color.getAlpha() + 
                      CLOSE_PARENTESE;
    }
    
    public static String getColor(int red, int green, int blue) {
        Color color = new Color(red, green, blue);
        
        return RGBA + OPEN_PARENTESE + 
                      color.getRed() + DECIMAL_SEPARATOR + 
                      color.getGreen() + DECIMAL_SEPARATOR + 
                      color.getBlue() +
                      CLOSE_PARENTESE;
    }
    
}
