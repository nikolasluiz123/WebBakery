package br.com.WebBakery.util;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.menu.DefaultMenuItem;

import br.com.WebBakery.enums.EnumTipoUsuario;

public class ManipuladorPermissao {

    private List<String> permissoesPadeiro;
    private List<String> permissoesAdministradorEstoque;
    private List<String> permissoesCaixa;
    private List<String> permissoesCliente;
    private List<String> permissoesGerente;
    private List<DefaultMenuItem> itensMenu;

    public ManipuladorPermissao(List<DefaultMenuItem> itensMenu) {
        this.permissoesPadeiro = new ArrayList<>();
        this.permissoesAdministradorEstoque = new ArrayList<>();
        this.permissoesCaixa = new ArrayList<>();
        this.permissoesCliente = new ArrayList<>();
        this.permissoesGerente = new ArrayList<>();
        this.itensMenu = itensMenu;

        initListPermissoesAdministradorEstoque();
        initListPermissoesCaixa();
        initListPermissoesCliente();
        initListPermissoesGerente();
        initListPermissoesPadeiro();
    }

    private void initListPermissoesGerente() {
        permissoesGerente.add("País");
        permissoesGerente.add("Países");
        permissoesGerente.add("Estado");
        permissoesGerente.add("Estados");
        permissoesGerente.add("Cidade");
        permissoesGerente.add("Cidades");
        permissoesGerente.add("Cliente");
        permissoesGerente.add("Clientes");
        permissoesGerente.add("Funcionário");
        permissoesGerente.add("Funcionários");
        permissoesGerente.add("Usuário");
        permissoesGerente.add("Usuários");
        permissoesGerente.add("Produto");
        permissoesGerente.add("Produtos");
        permissoesGerente.add("Receita");
        permissoesGerente.add("Receitas");
        permissoesGerente.add("Tarefa");
        permissoesGerente.add("Tarefas");
        permissoesGerente.add("Estoque Produtos");
        permissoesGerente.add("Venda");
        permissoesGerente.add("Vendas");
        permissoesGerente.add("Ingrediente");
        permissoesGerente.add("Registrar Estoque de Ingrediente");
        permissoesGerente.add("Estoque Ingrediente");
    }

    private void initListPermissoesCliente() {
        permissoesCliente.add("Cliente");
    }

    private void initListPermissoesCaixa() {
        permissoesGerente.add("Venda");
        permissoesGerente.add("Vendas");
    }

    private void initListPermissoesAdministradorEstoque() {
        permissoesAdministradorEstoque.add("Tarefa");
        permissoesAdministradorEstoque.add("Tarefas");
        permissoesAdministradorEstoque.add("Estoque Produtos");
        permissoesGerente.add("Ingrediente");
        permissoesGerente.add("Registrar Estoque de Ingrediente");
        permissoesGerente.add("Estoque Ingrediente");
    }

    private void initListPermissoesPadeiro() {
        permissoesPadeiro.add("Tarefas");
    }

    public void esconderItensMenuPara(EnumTipoUsuario tipo) {
        if (tipo.equals(EnumTipoUsuario.ADMINISTRADOR_ESTOQUE)) {
            lerPermissoes(this.permissoesAdministradorEstoque);
        } else if (tipo.equals(EnumTipoUsuario.CAIXA)) {
            lerPermissoes(this.permissoesCaixa);
        } else if (tipo.equals(EnumTipoUsuario.CLIENTE)) {
            lerPermissoes(this.permissoesCliente);
        } else if (tipo.equals(EnumTipoUsuario.GERENTE)) {
            lerPermissoes(this.permissoesGerente);
        } else if (tipo.equals(EnumTipoUsuario.PADEIRO)) {
            lerPermissoes(this.permissoesPadeiro);
        }
    }

    private void lerPermissoes(List<String> permissoes) {
        for (int i = 0; i < itensMenu.size(); i++) {
            if (!permissoes.contains(itensMenu.get(i).getValue())) {
                itensMenu.get(i).setRendered(false);
            }
        }
    }
    
    public List<String> getPermissoesPadeiro() {
        return permissoesPadeiro;
    }

    public void setPermissoesPadeiro(List<String> permissoesPadeiro) {
        this.permissoesPadeiro = permissoesPadeiro;
    }

    public List<String> getPermissoesAdministradorEstoque() {
        return permissoesAdministradorEstoque;
    }

    public void setPermissoesAdministradorEstoque(List<String> permissoesAdministradorEstoque) {
        this.permissoesAdministradorEstoque = permissoesAdministradorEstoque;
    }

    public List<String> getPermissoesCaixa() {
        return permissoesCaixa;
    }

    public void setPermissoesCaixa(List<String> permissoesCaixa) {
        this.permissoesCaixa = permissoesCaixa;
    }

    public List<String> getPermissoesCliente() {
        return permissoesCliente;
    }

    public void setPermissoesCliente(List<String> permissoesCliente) {
        this.permissoesCliente = permissoesCliente;
    }

    public List<String> getPermissoesGerente() {
        return permissoesGerente;
    }

    public void setPermissoesGerente(List<String> permissoesGerente) {
        this.permissoesGerente = permissoesGerente;
    }

    public List<DefaultMenuItem> getItensMenu() {
        return itensMenu;
    }

    public void setItensMenu(List<DefaultMenuItem> itensMenu) {
        this.itensMenu = itensMenu;
    }

}
