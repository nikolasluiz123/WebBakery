package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.to.TOCidade;

@Stateless
public class CidadeDao extends AbstractBaseDao<TOCidade> {

    private static final long serialVersionUID = -8347345341892955875L;

    // @PersistenceContext
    // transient private EntityManager entityManager;
    //
    // @Override
    // protected EntityManager getEntityManager() {
    // return this.entityManager;
    // }

    @Override
    public void cadastrar(TOCidade to) throws Exception {
        Cidade c = new Cidade();
        getConverter().getModelFromTO(to, c);
        getEntityManager().persist(c);
    }

    @Override
    public TOCidade buscarPorId(Integer id) throws Exception {
        Cidade c = getEntityManager().find(Cidade.class, id);
        TOCidade to = new TOCidade();
        getConverter().getTOFromModel(c, to);

        return to;
    }

    @Override
    public void atualizar(TOCidade to) throws Exception {
        Cidade c = new Cidade();
        getConverter().getModelFromTO(to, c);
        getEntityManager().merge(c);
    }

    @Override
    public List<TOCidade> listarTodos(Boolean ativo) throws Exception {
        List<Cidade> cidades = new ArrayList<>();
        List<TOCidade> toCidades = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT c")
        .add("FROM ".concat(Cidade.class.getName()).concat(" c "))
        .add("WHERE c.ativo = :pAtivo");
        
        cidades = getEntityManager().createQuery(sql.toString(), Cidade.class)
                                    .setParameter("pAtivo", ativo)
                                    .getResultList();
        
        for (Cidade cidade : cidades) {
            TOCidade to = new TOCidade();
            getConverter().getTOFromModel(cidade, to);
            toCidades.add(to);
        }

        return toCidades;
    }

    public List<TOCidade> listarTodos(boolean ativo, Integer estadoId) throws Exception {
        List<Cidade> cidades = new ArrayList<>();
        List<TOCidade> toCidades = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT c")
        .add("FROM ".concat(Cidade.class.getName()).concat(" c "))
        .add("WHERE")
        .add("c.ativo = :pAtivo")
        .add("AND c.estado.id = :pIdEstado");
        
        cidades = getEntityManager().createQuery(sql.toString(), Cidade.class)
                                    .setParameter("pAtivo", ativo)
                                    .setParameter("pIdEstado", estadoId)
                                    .getResultList();
        
        for (Cidade cidade : cidades) {
            TOCidade to = new TOCidade();
            getConverter().getTOFromModel(cidade, to);
            toCidades.add(to);
        }

        return toCidades;
    }
}
