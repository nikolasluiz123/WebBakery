package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.entitys.EstoqueProduto;
import br.com.WebBakery.to.TOEstoqueProduto;

@Stateless
public class EstoqueProdutoDao extends AbstractBaseDao<TOEstoqueProduto> {

    private static final long serialVersionUID = -2808765073810346192L;

    @Override
    public void salvar(TOEstoqueProduto to) throws Exception {
        EstoqueProduto estoqueProdutoDoBanco = existe(to.getToProduto().getId());
        
        if (estoqueProdutoDoBanco != null) {
            Integer quantidadeRegistrada = to.getQuantidade();
            Integer quantidadeDoBanco = estoqueProdutoDoBanco.getQuantidade();
            estoqueProdutoDoBanco.setQuantidade(quantidadeRegistrada + quantidadeDoBanco);
            
            getEntityManager().persist(estoqueProdutoDoBanco);
        } else {
            EstoqueProduto estoqueProduto = new EstoqueProduto();
            getConverter().getModelFromTO(to, estoqueProduto);
            getEntityManager().persist(estoqueProduto);
        }
    }
    
    public void descontarEstoque(TOEstoqueProduto to) {
        EstoqueProduto estoqueProdutoDoBanco = existe(to.getToProduto().getId());
        
        if (estoqueProdutoDoBanco != null) {
            Integer quantidadeRegistrada = to.getQuantidade();
            estoqueProdutoDoBanco.setQuantidade(quantidadeRegistrada);
            getEntityManager().persist(estoqueProdutoDoBanco);
        } 
    }

    @Override
    public TOEstoqueProduto buscarPorId(Integer id) throws Exception {
        EstoqueProduto estoqueProduto = getEntityManager().find(EstoqueProduto.class, id);
        TOEstoqueProduto toEstoqueProduto = new TOEstoqueProduto();
        getConverter().getTOFromModel(estoqueProduto, toEstoqueProduto);
        return toEstoqueProduto;
    }

    private EstoqueProduto existe(Integer produtoId) {
        EstoqueProduto ep = new EstoqueProduto();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ep                                                 ")
        .add("FROM ".concat(EstoqueProduto.class.getName()).concat(" ep "))
        .add("WHERE                                                     ")
        .add("ep.ativo = :pAtivo                                        ")
        .add("AND ep.produto.id = :pProdutoId                           ");
        
        try {
            
        ep = getEntityManager().createQuery(sql.toString(), EstoqueProduto.class)
                               .setParameter("pAtivo", true)
                               .setParameter("pProdutoId", produtoId)
                               .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
       
        return ep;
    }

    public TOEstoqueProduto buscarPorIdProduto(Integer produtoId) throws Exception {
        TOEstoqueProduto toEstoqueProduto = new TOEstoqueProduto();
                
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ep                                                     ")
        .add("FROM ".concat(EstoqueProduto.class.getName()).concat(" ep     "))
        .add("WHERE                                                         ")
        .add("ep.ativo = :pAtivo                                            ")
        .add("and ep.produto.id = :pProdutoId                               ");
        
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
        .add("SELECT ep                                                 ")
        .add("FROM ".concat(EstoqueProduto.class.getName()).concat(" ep "))
        .add("WHERE                                                     ")
        .add("ep.ativo = :pAtivo                                        ")
        .add("ORDER BY ep.produto.descricao                             ");
        
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
