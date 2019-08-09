package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.FotoProduto;
import br.com.WebBakery.to.TOFotoProduto;

@Stateless
public class FotoProdutoDao extends AbstractBaseDao<TOFotoProduto> {

    private static final long serialVersionUID = -8347345341892955875L;

//    @PersistenceContext
//    transient private EntityManager entityManager;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return this.entityManager;
//    }
    
    @Override
    public void cadastrar(TOFotoProduto to) throws Exception {
        FotoProduto fotoProduto = new FotoProduto();
        getConverter().getModelFromTO(to, fotoProduto);
        getEntityManager().persist(fotoProduto);
    }

    @Override
    public TOFotoProduto buscarPorId(Integer id) throws Exception {
        FotoProduto fotoProduto = getEntityManager().find(FotoProduto.class, id);
        TOFotoProduto to = new TOFotoProduto();
        getConverter().getTOFromModel(fotoProduto, to);
        
        return to;
    }

    @Override
    public void atualizar(TOFotoProduto to) throws Exception {
        FotoProduto fotoProduto = new FotoProduto();
        getConverter().getModelFromTO(to, fotoProduto);
        getEntityManager().merge(fotoProduto);
    }

    public List<TOFotoProduto> listarTodos(Boolean ativo) throws Exception {
        List<FotoProduto> fotosProdutos = new ArrayList<>();
        List<TOFotoProduto> toFotoProdutos = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT fp")
        .add("FROM ".concat(FotoProduto.class.getName()).concat(" fp "))
        .add("WHERE fp.ativo = :pAtivo");

        fotosProdutos = getEntityManager().createQuery(sql.toString(), FotoProduto.class)
                                          .setParameter("pAtivo", ativo)
                                          .getResultList();

        for (FotoProduto fp : fotosProdutos) {
            TOFotoProduto to = new TOFotoProduto();
            getConverter().getTOFromModel(fp, to);
            toFotoProdutos.add(to);
        }
        
        return toFotoProdutos;
    }

    public List<TOFotoProduto> listarFotosProduto(Integer idProduto) throws Exception {
        List<FotoProduto> fotosProdutos = new ArrayList<>();
        List<TOFotoProduto> toFotoProdutos = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT fp")
        .add("FROM ".concat(FotoProduto.class.getName()).concat(" fp "))
        .add("WHERE")
        .add("fp.ativo = :pAtivo")
        .add("AND fp.produto.id = :pIdProduto");
        
        fotosProdutos = getEntityManager().createQuery(sql.toString(), FotoProduto.class)
                                          .setParameter("pAtivo", true)
                                          .setParameter("pIdProduto", idProduto)
                                          .getResultList();

        for (FotoProduto fp : fotosProdutos) {
            TOFotoProduto to = new TOFotoProduto();
            getConverter().getTOFromModel(fp, to);
            toFotoProdutos.add(to);
        }
        
        return toFotoProdutos;
    }

    public void inativarFotos(Integer idProduto) {
       StringJoiner selectFotosProduto = new StringJoiner("\n");
       selectFotosProduto
       .add("(SELECT ")
       .add("fp.id")
       .add("FROM ".concat(FotoProduto.class.getName()) + " fp ")
       .add("WHERE fp.ativo = :pAtivo")
       .add("AND fp.produto.id = :pIdProduto)");
       
       StringJoiner updateFotosProduto = new StringJoiner("\n");
       updateFotosProduto
       .add("UPDATE ".concat(FotoProduto.class.getName()) + " fp ")
       .add("SET fp.ativo = false")
       .add("WHERE fp.id IN ".concat(selectFotosProduto.toString()));
       
       Query query = getEntityManager().createQuery(updateFotosProduto.toString());
       query
       .setParameter("pAtivo", true)
       .setParameter("pIdProduto", idProduto);
       
       query.executeUpdate();
    }

}
