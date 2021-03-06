package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.entitys.Logradouro;
import br.com.WebBakery.to.TOLogradouro;

@Stateless
public class LogradouroDao extends AbstractBaseDao<TOLogradouro> {
    
    private static final long serialVersionUID = -3918599275005523240L;

    @Override
    public void salvar(TOLogradouro to) throws Exception {
        Logradouro l = null;
        if (to.getId() == null) {
            l = new Logradouro();
        } else {
            l = getEntityManager().find(Logradouro.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, l);            
        
        getEntityManager().persist(l);
        getEntityManager().flush();
        to.setId(l.getId());
    }

    @Override
    public TOLogradouro buscarPorId(Integer id) throws Exception {
        Logradouro l = getEntityManager().find(Logradouro.class, id);
        TOLogradouro to = new TOLogradouro();
        getConverter().getTOFromModel(l, to);
        
        return to;
    }

    public List<TOLogradouro> listarTodos(Boolean ativo) throws Exception {
        List<Logradouro> logradouros = new ArrayList<>();
        List<TOLogradouro> toLogradouros = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT l")
        .add("FROM ".concat(Logradouro.class.getName()).concat(" l "))
        .add("WHERE l.ativo = :pAtivo");
        
        logradouros = getEntityManager().createQuery(sql.toString(), Logradouro.class)
                                        .setParameter("pAtivo", ativo)
                                        .getResultList();

        for (Logradouro l : logradouros) {
            TOLogradouro to = new TOLogradouro();
            getConverter().getTOFromModel(l, to);
            toLogradouros.add(to);
        }
        
        return toLogradouros;
    }

}
