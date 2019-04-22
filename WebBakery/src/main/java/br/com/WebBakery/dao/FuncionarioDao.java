package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Funcionario;

@Stateless
public class FuncionarioDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    transient private EntityManager em;

    public FuncionarioDao(EntityManager em) {
        this.em = em;
    }

    public FuncionarioDao() {
    }

    public void cadastrar(Funcionario funcionario) {
        em.persist(funcionario);
    }

    public List<Funcionario> listarTodos(Boolean ativo) {

        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios = em
                .createQuery("SELECT f FROM Funcionario f WHERE f.ativo = :pAtivo ORDER BY f.nome",
                             Funcionario.class)
                .setParameter("pAtivo", ativo).getResultList();

        return funcionarios;
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios = em.createQuery("SELECT f FROM Funcionario f WHERE 1=1 ORDER BY f.nome",
                                      Funcionario.class)
                .getResultList();

        return funcionarios;
    }

    public Funcionario buscarPelaId(Integer id) {
        return em.find(Funcionario.class, id);
    }

    public void atualizar(Funcionario funcionario) {
        em.merge(funcionario);
    }
}
