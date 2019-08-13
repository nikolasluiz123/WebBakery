package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.EstoqueProduto;
import br.com.WebBakery.to.TOEstoqueProduto;

@Stateless
public class EstoqueProdutoDao extends AbstractBaseDao<TOEstoqueProduto> {

    private static final long serialVersionUID = -2808765073810346192L;

    @Override
    public void salvar(TOEstoqueProduto to) throws Exception {
        EstoqueProduto ep = null;
        if (to.getId() == null) {
            ep = new EstoqueProduto();
        } else {
            ep = getEntityManager().find(EstoqueProduto.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, ep);            
        
        getEntityManager().persist(ep);
    }

    @Override
    public TOEstoqueProduto buscarPorId(Integer id) throws Exception {
        EstoqueProduto estoqueProduto = getEntityManager().find(EstoqueProduto.class, id);
        TOEstoqueProduto toEstoqueProduto = new TOEstoqueProduto();
        getConverter().getTOFromModel(estoqueProduto, toEstoqueProduto);
        return toEstoqueProduto;
    }

//    public void atualizar(TOEstoqueProduto toEstoque, Integer qtd) throws Exception {
//        EstoqueProduto estoque = new EstoqueProduto();
//        getConverter().getModelFromTO(toEstoque, estoque);
//        estoque.setQuantidade(estoque.getQuantidade() + qtd);
//        getEntityManager().merge(estoque);
//    }

    public TOEstoqueProduto existe(Integer produtoId) throws Exception {
        TOEstoqueProduto toEstoqueProduto = new TOEstoqueProduto();  
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ep")
        .add("FROM ".concat(EstoqueProduto.class.getName()).concat(" ep "))
        .add("WHERE")
        .add("ep.ativo = :pAtivo")
        .add("AND ep.produto.id = :pProdutoId");
        
        EstoqueProduto estoqueProduto = getEntityManager().createQuery(sql.toString(), EstoqueProduto.class)
                                                          .setParameter("pAtivo", true)
                                                          .setParameter("pId", produtoId)
                                                          .getSingleResult();
       
        getConverter().getTOFromModel(estoqueProduto, toEstoqueProduto);
        
        return toEstoqueProduto;
    }

    public TOEstoqueProduto buscarPorIdProduto(Integer produtoId) throws Exception {
        TOEstoqueProduto toEstoqueProduto = new TOEstoqueProduto();
                
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ep")
        .add("FROM ".concat(EstoqueProduto.class.getName()).concat(" ep "))
        .add("WHERE")
        .add("ep.ativo = :pAtivo")
        .add("ep.produto.id = :pProdutoId");
        
        EstoqueProduto estoque = getEntityManager().createQuery(sql.toString(), EstoqueProduto.class)
                                                   .setParameter("pAtivo", true)
                                                   .setParameter("pProdutoId", produtoId)
                                                   .getSingleResult();
        
        getConverter().getTOFromModel(estoque, toEstoqueProduto);

        return toEstoqueProduto;
    }

    @Override
    public List<TOEstoqueProduto> listarTodos(Boolean ativo) throws Exception {
        List<EstoqueProduto> estoquesProdutos = new ArrayList<>();
        List<TOEstoqueProduto> toEstoquesProdutos = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ep")
        .add("FROM ".concat(EstoqueProduto.class.getName()).concat(" ep "))
        .add("WHERE")
        .add("ep.ativo = :pAtivo")
        .add("ORDER BY ep.produto.descricao");
        
        estoquesProdutos = getEntityManager().createQuery(sql.toString(), EstoqueProduto.class)
                                             .setParameter("pAtivo", true)
                                             .getResultList();
        
        for (EstoqueProduto e : estoquesProdutos) {
            TOEstoqueProduto to = new TOEstoqueProduto();
            getConverter().getTOFromModel(e, to);
            toEstoquesProdutos.add(to);
        }

        return toEstoquesProdutos;
    }

}
