package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Cidade;

@Stateless
public class CidadeDao extends AbstractBaseDao<Cidade> {

    private static final long serialVersionUID = -8347345341892955875L;

    @PersistenceContext
    transient private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    
    public List<Cidade> listarTodos(Boolean ativo) {
        List<Cidade> cidades = new ArrayList<>();

        cidades = getEntityManager().createQuery("SELECT c FROM Cidade c WHERE c.ativo = :pAtivo", Cidade.class)
                .setParameter("pAtivo", ativo).getResultList();

        return cidades;
    }

    public List<Cidade> listarTodos(boolean ativo, Integer estadoId) {
        List<Cidade> cidades = new ArrayList<>();

        cidades = getEntityManager()
                .createQuery("SELECT c FROM Cidade c WHERE c.ativo = :pAtivo AND c.estado.id = :pEstadoId",
                             Cidade.class)
                .setParameter("pAtivo", ativo).setParameter("pEstadoId", estadoId).getResultList();

        return cidades;
    }

    @Override
    public Class<?> getModelClass() {
        return Cidade.class;
    }

}
