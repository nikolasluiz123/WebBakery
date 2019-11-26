package br.com.WebBakery.bean.consulta;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractVerticalGraphicBean;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.model.graphics.FuncionarioGraphicValues;
import br.com.WebBakery.util.Calendar_Util;

@Named
@ViewScoped
public class GraficoFuncionariosBean2 extends AbstractVerticalGraphicBean {

    private static final long serialVersionUID = -8704501528429821580L;

    private static final String GRAPHIC_TITLE = "Caixas que mais Realizam Vendas";

    private List<FuncionarioGraphicValues> funcionariosMaisProdutivos;
    
    @Inject
    private FuncionarioDao dao;
    
    @PostConstruct
    private void init() {

        Calendar initialDate = Calendar_Util.getInstanceWhithLocale(26, 9, 2019);
        Calendar finalDate = Calendar_Util.getInstanceWhithLocale();
        
        funcionariosMaisProdutivos = dao.getCincoFuncionariosQueMaisVendem(initialDate, finalDate);
        
        setTitle(GRAPHIC_TITLE);
        
        funcionariosMaisProdutivos.forEach(i -> {
            addValue(i.getQuantidadeVendasRealizadas());
            addLabel(i.getNomeFuncionario());
        });
        
    }

}
