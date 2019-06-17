package br.com.WebBakery.bean.manutencao;

import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@Named
public class MenuBean {

    private MenuModel model;

    public MenuBean() {
        this.model = new DefaultMenuModel();

        DefaultSubMenu primeiroSubmenu = new DefaultSubMenu("Localidades");
        DefaultSubMenu segundoSubmenu = new DefaultSubMenu("Gestão de Pessoas");
        DefaultSubMenu terceiroSubmenu = new DefaultSubMenu("Produção");

        DefaultMenuItem item1 = new DefaultMenuItem("País");
        item1.setUrl("cadastroPais.xhtml");

        DefaultMenuItem item2 = new DefaultMenuItem("Países");
        item2.setUrl("listaPais.xhtml");

        DefaultMenuItem item3 = new DefaultMenuItem("Estado");
        item3.setUrl("cadastroEstado.xhtml");

        DefaultMenuItem item4 = new DefaultMenuItem("Estados");
        item4.setUrl("listaEstado.xhtml");

        DefaultMenuItem item5 = new DefaultMenuItem("Cidade");
        item5.setUrl("cadastroCidade.xhtml");

        DefaultMenuItem item6 = new DefaultMenuItem("Cidades");
        item6.setUrl("listaCidade.xhtml");

        DefaultMenuItem item7 = new DefaultMenuItem("Cliente");
        item7.setUrl("cadastroCliente.xhtml");

        DefaultMenuItem item8 = new DefaultMenuItem("Clientes");
        item8.setUrl("listaCliente.xhtml");

        DefaultMenuItem item9 = new DefaultMenuItem("Funcionário");
        item9.setUrl("cadastroFuncionario.xhtml");

        DefaultMenuItem item10 = new DefaultMenuItem("Funcionários");
        item10.setUrl("listaFuncionario.xhtml");

        DefaultMenuItem item11 = new DefaultMenuItem("Usuário");
        item11.setUrl("cadastroUsuario.xhtml");

        DefaultMenuItem item12 = new DefaultMenuItem("Usuários");
        item12.setUrl("listaUsuario.xhtml");

        DefaultMenuItem item13 = new DefaultMenuItem("Produto");
        item13.setUrl("cadastroProduto.xhtml");

        DefaultMenuItem item14 = new DefaultMenuItem("Produtos");
        item14.setUrl("listaProduto.xhtml");

        DefaultMenuItem item15 = new DefaultMenuItem("Receita");
        item15.setUrl("cadastroReceita.xhtml");

        DefaultMenuItem item16 = new DefaultMenuItem("Receitas");
        item16.setUrl("listaReceita.xhtml");

        DefaultMenuItem item17 = new DefaultMenuItem("Tarefa");
        item17.setUrl("cadastroTarefa.xhtml");

        DefaultMenuItem item18 = new DefaultMenuItem("Tarefas");
        item18.setUrl("listaTarefa.xhtml");

        DefaultMenuItem item19 = new DefaultMenuItem("Estoque Produtos");
        item19.setUrl("estoqueProduto.xhtml");

        primeiroSubmenu.addElement(item1);
        primeiroSubmenu.addElement(item2);
        primeiroSubmenu.addElement(item3);
        primeiroSubmenu.addElement(item4);
        primeiroSubmenu.addElement(item5);
        primeiroSubmenu.addElement(item6);
        model.addElement(primeiroSubmenu);

        segundoSubmenu.addElement(item7);
        segundoSubmenu.addElement(item8);
        segundoSubmenu.addElement(item9);
        segundoSubmenu.addElement(item10);
        segundoSubmenu.addElement(item11);
        segundoSubmenu.addElement(item12);
        model.addElement(segundoSubmenu);

        terceiroSubmenu.addElement(item13);
        terceiroSubmenu.addElement(item14);
        terceiroSubmenu.addElement(item15);
        terceiroSubmenu.addElement(item16);
        terceiroSubmenu.addElement(item17);
        terceiroSubmenu.addElement(item18);
        terceiroSubmenu.addElement(item19);
        model.addElement(terceiroSubmenu);

    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

}
