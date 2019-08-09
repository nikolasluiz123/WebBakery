package br.com.WebBakery.core.tests;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.util.ObjectManipulatorUtil;

public class Main {
    
    public Main() {
        Class<?> clazz = NestedTO.class;
        
        if (clazz.isAssignableFrom(AbstractBaseTO.class)) {
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
        }
        
    }
    
    
    
    
}
