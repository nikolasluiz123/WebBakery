package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Pais;

@Stateless
public class PaisDao extends AbstractBaseDao<Pais> {

    private static final long serialVersionUID = 1904464340270603917L;

    public List<Pais> listarTodos(Boolean ativo) {
        List<Pais> paises = new ArrayList<>();

        paises = getEntityManager().createQuery("SELECT p FROM Pais p WHERE p.ativo = :pAtivo", Pais.class)
                .setParameter("pAtivo", ativo).getResultList();

        return paises;
    }

    @Override
    public Class<?> getModelClass() {
        return Pais.class;
    }

}
