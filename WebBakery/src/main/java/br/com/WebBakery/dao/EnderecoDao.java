package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Endereco;

@Stateless
public class EnderecoDao extends AbstractBaseDao<Endereco> {

    private static final long serialVersionUID = -462049822493846497L;

    public EnderecoDao(EntityManager em) {
        this.em = em;
    }

    public EnderecoDao() {
    }

    public List<Endereco> listarTodos(Boolean ativo) {

        List<Endereco> enderecos = new ArrayList<>();

        enderecos = em
                .createQuery("SELECT e FROM Endereco e WHERE e.ativo = :pAtivo ORDER BY e.pais.nome",
                             Endereco.class)
                .setParameter("pAtivo", ativo).getResultList();

        return enderecos;
    }

    public List<Endereco> listarTodos() {
        List<Endereco> enderecos = new ArrayList<>();

        enderecos = em.createQuery("SELECT e FROM Endereco e WHERE 1=1 ORDER BY e.pais.nome",
                                   Endereco.class)
                .getResultList();

        return enderecos;
    }

}
