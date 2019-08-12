package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.to.TOPais;

@Stateless
public class PaisDao extends AbstractBaseDao<TOPais> {
    
    private static final long serialVersionUID = 1904464340270603917L;

    @Override
    public void cadastrar(TOPais to) throws Exception {
        Pais p = new Pais();
        getConverter().getModelFromTO(to, p);
        getEntityManager().persist(p);
    }

    @Override
    public TOPais buscarPorId(Integer id) throws Exception {
        Pais p = getEntityManager().find(Pais.class, id);
        TOPais to = new TOPais();
        getConverter().getTOFromModel(p, to);
        return to;
    }

    @Override
    public void atualizar(TOPais to) throws Exception {
        Pais p = new Pais();
        getConverter().getTOFromModel(p, to);
        getEntityManager().merge(p);
    }

    @Override
    public List<TOPais> listarTodos(Boolean ativo) throws Exception {
        List<Pais> paises = new ArrayList<>();
        List<TOPais> tos = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT p")
        .add("FROM ".concat(Pais.class.getName().concat(" p ")))
        .add("WHERE p.ativo = :pAtivo");
        
        paises = getEntityManager().createQuery(sql.toString(), Pais.class)
                                   .setParameter("pAtivo", ativo)
                                   .getResultList();

        for (Pais p : paises) {
            TOPais to = new TOPais();
            getConverter().getTOFromModel(p, to);
            tos.add(to);
        }

        return tos;
    }

}
