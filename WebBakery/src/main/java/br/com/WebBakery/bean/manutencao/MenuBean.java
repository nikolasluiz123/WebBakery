package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.util.ManipuladorPermissao;

@Named(MenuBean.BEAN_NAME)
public class MenuBean {

    static final String BEAN_NAME = "menuBean";
    
    private MenuModel model;

    public MenuBean() {
        this.model = new DefaultMenuModel();
        TOUsuario usuarioLogado = (TOUsuario) FacesUtil.getHTTPSession().getAttribute("usuarioLogado");

        DefaultSubMenu primeiroSubMenu = new DefaultSubMenu("Gestão de Pessoas");
        DefaultSubMenu segundoSubMenu = new DefaultSubMenu("Produção");

        List<DefaultMenuItem> itensMenu = new ArrayList<>();

        DefaultMenuItem item1 = new DefaultMenuItem("Funcionário");
        item1.setUrl("cadastroFuncionario.xhtml");
        itensMenu.add(item1);

        DefaultMenuItem item2 = new DefaultMenuItem("Funcionários");
        item2.setUrl("listaFuncionario.xhtml");
        itensMenu.add(item2);

        DefaultMenuItem item3 = new DefaultMenuItem("Usuário");
        item3.setUrl("cadastroUsuario.xhtml");
        itensMenu.add(item3);

        DefaultMenuItem item4 = new DefaultMenuItem("Usuários");
        item4.setUrl("listaUsuario.xhtml");
        itensMenu.add(item4);

        DefaultMenuItem item5 = new DefaultMenuItem("Produto");
        item5.setUrl("cadastroProduto.xhtml");
        itensMenu.add(item5);

        DefaultMenuItem item6 = new DefaultMenuItem("Produtos");
        item6.setUrl("listaProduto.xhtml");
        itensMenu.add(item6);

        DefaultMenuItem item7 = new DefaultMenuItem("Receita");
        item7.setUrl("cadastroReceita.xhtml");
        itensMenu.add(item7);

        DefaultMenuItem item8 = new DefaultMenuItem("Receitas");
        item8.setUrl("listaReceita.xhtml");
        itensMenu.add(item8);

        DefaultMenuItem item9 = new DefaultMenuItem("Tarefa");
        item9.setUrl("cadastroTarefa.xhtml");
        itensMenu.add(item9);

        DefaultMenuItem item10 = new DefaultMenuItem("Tarefas");
        item10.setUrl("listaTarefa.xhtml");
        itensMenu.add(item10);

        DefaultMenuItem item11 = new DefaultMenuItem("Estoque Produtos");
        item11.setUrl("estoqueProduto.xhtml");
        itensMenu.add(item11);

        DefaultMenuItem item12 = new DefaultMenuItem("Ingrediente");
        item12.setUrl("cadastroIngrediente.xhtml");
        itensMenu.add(item12);
        
        DefaultMenuItem item13 = new DefaultMenuItem("Registrar Estoque de Ingrediente");
        item13.setUrl("cadastroEstoqueIngrediente.xhtml");
        itensMenu.add(item13);
        
        DefaultMenuItem item14 = new DefaultMenuItem("Estoque Ingrediente");
        item14.setUrl("listaEstoqueIngrediente.xhtml");
        itensMenu.add(item14);

        ManipuladorPermissao manipulador = new ManipuladorPermissao(itensMenu);
        manipulador.esconderItensMenuPara(usuarioLogado.getTipo());

        primeiroSubMenu.addElement(item1);
        primeiroSubMenu.addElement(item2);
        primeiroSubMenu.addElement(item3);
        primeiroSubMenu.addElement(item4);
        model.addElement(primeiroSubMenu);

        segundoSubMenu.addElement(item5);
        segundoSubMenu.addElement(item6);
        segundoSubMenu.addElement(item7);
        segundoSubMenu.addElement(item8);
        segundoSubMenu.addElement(item9);
        segundoSubMenu.addElement(item10);
        segundoSubMenu.addElement(item11);
        segundoSubMenu.addElement(item12);
        segundoSubMenu.addElement(item13);
        segundoSubMenu.addElement(item14);
        model.addElement(segundoSubMenu);

    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

}
