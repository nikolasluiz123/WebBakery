package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.to.TOEstado;

@Stateless
public class EstadoDao extends AbstractBaseDao<TOEstado> {
//    @PersistenceContext
//    transient private EntityManager entityManager;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return this.entityManager;
//    }

    private static final long serialVersionUID = -783763888391855583L;

    @Override
    public void cadastrar(TOEstado to) throws Exception {
        Estado e = new Estado();
        getConverter().getModelFromTO(to, e);
        getEntityManager().persist(e);
    }

    @Override
    public TOEstado buscarPorId(Integer id) throws Exception {
        Estado e = getEntityManager().find(Estado.class, id);
        TOEstado to = new TOEstado();
        getConverter().getTOFromModel(e, to);
        
        return to;
    }

    @Override
    public void atualizar(TOEstado to) throws Exception {
        Estado e = new Estado();
        getConverter().getModelFromTO(to, e);
        
    }
    
    public List<TOEstado> listarTodos(Boolean ativo) throws Exception {
        List<Estado> estados = new ArrayList<>();
        List<TOEstado> toEstados = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT e")
        .add("FROM ".concat(Estado.class.getName()).concat(" e "))
        .add("WHERE e.ativo = :pAtivo")
        .add("ORDER BY e.nome");
        
        estados = getEntityManager().createQuery(sql.toString(), Estado.class)
                                    .setParameter("pAtivo", ativo)
                                    .getResultList();

        for (Estado e : estados) {
            TOEstado to = new TOEstado();
            getConverter().getTOFromModel(e, to);
            toEstados.add(to);
        }

        return toEstados;
    }

    public List<TOEstado> listarTodos(boolean ativo, Integer paisId) throws Exception {
        List<Estado> estados = new ArrayList<>();
        List<TOEstado> toEstados = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT e")
        .add("FROM ".concat(Estado.class.getName()).concat(" e "))
        .add("WHERE")
        .add("e.ativo = :pAtivo")
        .add("AND e.pais.id = :pPaisID")
        .add("ORDER BY e.nome");
        
        estados = getEntityManager().createQuery(sql.toString(), Estado.class)
                                    .setParameter("pAtivo", ativo)
                                    .setParameter("pPaisID", paisId)
                                    .getResultList();

        for (Estado e : estados) {
            TOEstado to = new TOEstado();
            getConverter().getTOFromModel(e, to);
            toEstados.add(to);
        }
        
        return toEstados;
    }

}
