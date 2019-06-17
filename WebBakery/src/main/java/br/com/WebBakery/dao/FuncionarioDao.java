package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Funcionario;

@Stateless
public class FuncionarioDao implements IBaseDao<Funcionario> {

    private static final long serialVersionUID = -4485066218691416726L;

    @PersistenceContext
    transient private EntityManager em;

    public FuncionarioDao(EntityManager em) {
        this.em = em;
    }

    public FuncionarioDao() {
    }

    @Override
    public void cadastrar(Funcionario model) {
        em.persist(model);
    }

    @Override
    public List<Funcionario> listarTodos(Boolean ativo) {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios = em.createQuery("SELECT f FROM Funcionario f WHERE f.ativo = :pAtivo",
                                      Funcionario.class)
                .setParameter("pAtivo", ativo).getResultList();

        return funcionarios;
    }

    @Override
    public Funcionario buscarPorId(Integer id) {
        return em.find(Funcionario.class, id);
    }

    @Override
    public void atualizar(Funcionario model) {
        em.merge(model);
    }

}
