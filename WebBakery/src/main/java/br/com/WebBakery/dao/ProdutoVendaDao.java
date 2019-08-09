package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.ProdutoVenda;
import br.com.WebBakery.to.TOProdutoVenda;

@Stateless
public class ProdutoVendaDao extends AbstractBaseDao<TOProdutoVenda> {
//    @PersistenceContext
//    transient private EntityManager entityManager;
//    
//    @Override
//    protected EntityManager getEntityManager() {
//        return this.entityManager;
//    }
    
    private static final long serialVersionUID = -1109157795344801242L;

    @Override
    public void cadastrar(TOProdutoVenda to) throws Exception {
        ProdutoVenda pv = new ProdutoVenda();
        getConverter().getModelFromTO(to, pv);
        getEntityManager().persist(pv);
    }

    @Override
    public TOProdutoVenda buscarPorId(Integer id) throws Exception {
        ProdutoVenda pv = getEntityManager().find(ProdutoVenda.class, id);
        TOProdutoVenda to = new TOProdutoVenda();
        getConverter().getTOFromModel(pv, to);
        
        return to;
    }

    @Override
    public void atualizar(TOProdutoVenda to) throws Exception {
        ProdutoVenda pv = new ProdutoVenda();
        getConverter().getModelFromTO(to, pv);
        getEntityManager().merge(pv);
    }
    
    public List<TOProdutoVenda> buscarPorIdVenda(Integer IdVenda) throws Exception {
        List<ProdutoVenda> produtosVendas = new ArrayList<>();
        List<TOProdutoVenda> toProdutosVendas = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT pv")
        .add("FROM ".concat(ProdutoVenda.class.getName()).concat(" pv "))
        .add("WHERE")
        .add("pv.ativo = :pAtivo")
        .add("AND pv.venda.id = :pIdVenda");
        
        produtosVendas = getEntityManager().createQuery(sql.toString(), ProdutoVenda.class)
                                           .setParameter("pAtivo", true)
                                           .setParameter("pIdVenda", IdVenda)
                                           .getResultList();

        for (ProdutoVenda pv : produtosVendas) {
            TOProdutoVenda to = new TOProdutoVenda();
            getConverter().getTOFromModel(pv, to);
            toProdutosVendas.add(to);
        }
        
        return toProdutosVendas;
    }

    @Override
    public List<TOProdutoVenda> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

}
