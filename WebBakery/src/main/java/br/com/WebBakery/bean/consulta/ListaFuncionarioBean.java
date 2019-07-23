package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.model.Funcionario;

@Named
@ViewScoped
public class ListaFuncionarioBean extends AbstractBaseListMBean<Funcionario> {

    private static final long serialVersionUID = 5087202394870258058L;

    private static final String FUNCIONARIO_INATIVATED_SUCCESSFULLY = "Funcionário atualizado com sucesso!";

    private FuncionarioDao funcionarioDao;
    private List<Funcionario> funcionarios;
    private List<Funcionario> funcionariosFiltrados;

    private EnderecoDao enderecoDao;

    public void init() {
        this.funcionarioDao = new FuncionarioDao(this.em);
        this.funcionarios = new ArrayList<>();
        initListFuncionarios();
    }

    @Transactional
    public void inativar(Funcionario funcionario) {
        funcionario.setAtivo(false);
        funcionario.getEndereco().setAtivo(false);
        // verificar se é necessário inativar o logradouro
        this.funcionarioDao.atualizar(funcionario);
        this.enderecoDao.atualizar(funcionario.getEndereco());
        initListFuncionarios();
        context.addMessage(null, new FacesMessage(FUNCIONARIO_INATIVATED_SUCCESSFULLY));
    }

    @Transactional
    public void carregar(Integer funcionarioID) throws IOException {
        setObjetoSessao(funcionarioID, "FuncionarioID", "cadastroFuncionario.xhtml");
    }

    private void initListFuncionarios() {
        this.funcionarios = this.funcionarioDao.listarTodos(true);
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Funcionario> getFuncionariosFiltrados() {
        return funcionariosFiltrados;
    }

    public void setFuncionariosFiltrados(List<Funcionario> funcionariosFiltrados) {
        this.funcionariosFiltrados = funcionariosFiltrados;
    }

}
