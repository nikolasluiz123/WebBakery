package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Funcionario;

@Stateless
public class FuncionarioDao extends AbstractBaseDao<Funcionario> {

    private static final long serialVersionUID = -3829525203324472005L;

    public FuncionarioDao(EntityManager em) {
        this.em = em;
    }

    public FuncionarioDao() {
    }

    public List<Funcionario> listarTodos(Boolean ativo) {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios = em.createQuery("SELECT f FROM Funcionario f WHERE f.ativo = :pAtivo",
                                      Funcionario.class)
                .setParameter("pAtivo", ativo).getResultList();

        return funcionarios;
    }

    public Funcionario buscarPorIdUsuario(Integer idUsuario) {
        TypedQuery<Funcionario> query = em
                .createQuery("SELECT f FROM Funcionario f WHERE f.ativo = TRUE AND f.usuario.id = :pIdUsuario",
                             Funcionario.class);
        query.setParameter("pIdUsuario", idUsuario);
        Funcionario f = null;
        try {
            f = query.getSingleResult();
        } catch (NoResultException e) {
            return f;
        }
        return f;
    }
}
