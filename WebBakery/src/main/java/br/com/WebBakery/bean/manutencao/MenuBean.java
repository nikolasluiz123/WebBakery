package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.Faces_Util;
import br.com.WebBakery.util.ManipuladorPermissao;

@Named
public class MenuBean {

    private MenuModel model;

    public MenuBean() {
        this.model = new DefaultMenuModel();
        Usuario usuarioLogado = (Usuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");

        DefaultSubMenu primeiroSubmenu = new DefaultSubMenu("Localidades");
        DefaultSubMenu segundoSubmenu = new DefaultSubMenu("Gestão de Pessoas");
        DefaultSubMenu terceiroSubmenu = new DefaultSubMenu("Produção");
        DefaultSubMenu quartoSubmenu = new DefaultSubMenu("Negócios");

        List<DefaultMenuItem> itensMenu = new ArrayList<>();

        DefaultMenuItem item1 = new DefaultMenuItem("País");
        item1.setUrl("cadastroPais.xhtml");
        itensMenu.add(item1);

        DefaultMenuItem item2 = new DefaultMenuItem("Países");
        item2.setUrl("listaPais.xhtml");
        itensMenu.add(item2);

        DefaultMenuItem item3 = new DefaultMenuItem("Estado");
        item3.setUrl("cadastroEstado.xhtml");
        itensMenu.add(item3);

        DefaultMenuItem item4 = new DefaultMenuItem("Estados");
        item4.setUrl("listaEstado.xhtml");
        itensMenu.add(item4);

        DefaultMenuItem item5 = new DefaultMenuItem("TOCidade");
        item5.setUrl("cadastroCidade.xhtml");
        itensMenu.add(item5);

        DefaultMenuItem item6 = new DefaultMenuItem("Cidades");
        item6.setUrl("listaCidade.xhtml");
        itensMenu.add(item6);

        DefaultMenuItem item7 = new DefaultMenuItem("TOCliente");
        item7.setUrl("cadastroCliente.xhtml");
        itensMenu.add(item7);

        DefaultMenuItem item8 = new DefaultMenuItem("Clientes");
        item8.setUrl("listaCliente.xhtml");
        itensMenu.add(item8);

        DefaultMenuItem item9 = new DefaultMenuItem("Funcionário");
        item9.setUrl("cadastroFuncionario.xhtml");
        itensMenu.add(item9);

        DefaultMenuItem item10 = new DefaultMenuItem("Funcionários");
        item10.setUrl("listaFuncionario.xhtml");
        itensMenu.add(item10);

        DefaultMenuItem item11 = new DefaultMenuItem("Usuário");
        item11.setUrl("cadastroUsuario.xhtml");
        itensMenu.add(item11);

        DefaultMenuItem item12 = new DefaultMenuItem("Usuários");
        item12.setUrl("listaUsuario.xhtml");
        itensMenu.add(item12);

        DefaultMenuItem item13 = new DefaultMenuItem("TOProduto");
        item13.setUrl("cadastroProduto.xhtml");
        itensMenu.add(item13);

        DefaultMenuItem item14 = new DefaultMenuItem("Produtos");
        item14.setUrl("listaProduto.xhtml");
        itensMenu.add(item14);

        DefaultMenuItem item15 = new DefaultMenuItem("TOReceita");
        item15.setUrl("cadastroReceita.xhtml");
        itensMenu.add(item15);

        DefaultMenuItem item16 = new DefaultMenuItem("Receitas");
        item16.setUrl("listaReceita.xhtml");
        itensMenu.add(item16);

        DefaultMenuItem item17 = new DefaultMenuItem("TOTarefa");
        item17.setUrl("cadastroTarefa.xhtml");
        itensMenu.add(item17);

        DefaultMenuItem item18 = new DefaultMenuItem("Tarefas");
        item18.setUrl("listaTarefa.xhtml");
        itensMenu.add(item18);

        DefaultMenuItem item19 = new DefaultMenuItem("Estoque Produtos");
        item19.setUrl("estoqueProduto.xhtml");
        itensMenu.add(item19);

        DefaultMenuItem item20 = new DefaultMenuItem("TOVenda");
        item20.setUrl("cadastroVenda.xhtml");
        itensMenu.add(item20);

        DefaultMenuItem item21 = new DefaultMenuItem("Vendas");
        item21.setUrl("listaVenda.xhtml");
        itensMenu.add(item21);

        ManipuladorPermissao manipulador = new ManipuladorPermissao(itensMenu);
        manipulador.esconderItensMenuPara(usuarioLogado.getTipo());

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

        quartoSubmenu.addElement(item20);
        quartoSubmenu.addElement(item21);
        model.addElement(quartoSubmenu);
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

}
