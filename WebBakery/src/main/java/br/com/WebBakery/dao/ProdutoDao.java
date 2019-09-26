package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.FotoProduto;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.to.TOFotoProduto;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOProdutoComFoto;

@Stateless
public class ProdutoDao extends AbstractBaseDao<TOProduto> {

    private static final long serialVersionUID = 8719276490177777230L;

    @Override
    public void salvar(TOProduto to) throws Exception {
        Produto p = null;
        if (to.getId() == null) {
            p = new Produto();
        } else {
            p = getEntityManager().find(Produto.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, p);            
        
        getEntityManager().persist(p);
        getEntityManager().flush();
        to.setId(p.getId());
    }

    @Override
    public TOProduto buscarPorId(Integer id) throws Exception {
        Produto p = getEntityManager().find(Produto.class, id);
        TOProduto to = new TOProduto();
        getConverter().getTOFromModel(p, to);

        return to;
    }

    public List<TOProduto> listarTodos(Boolean ativo) throws Exception {
        List<Produto> produtos = new ArrayList<>();
        List<TOProduto> toProdutos = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT p")
        .add("FROM ".concat(Produto.class.getName()).concat(" p "))
        .add("WHERE p.ativo = :pAtivo");

        produtos = getEntityManager().createQuery(sql.toString(), Produto.class)
                .setParameter("pAtivo", ativo).getResultList();

        for (Produto p : produtos) {
            TOProduto to = new TOProduto();
            getConverter().getTOFromModel(p, to);
            toProdutos.add(to);
        }

        return toProdutos;
    }

    public List<TOProdutoComFoto> listarTodosProdutoComFotos() throws Exception {
        List<Produto> produtos = new ArrayList<>();
        List<FotoProduto> fotosProdutos = new ArrayList<>();
        
        List<TOProduto> toProdutos = new ArrayList<>();
        List<TOFotoProduto> toFotosProduto = new ArrayList<>();
        
        List<TOProdutoComFoto> toProdutosComFotos = new ArrayList<>();
        
        StringJoiner sqlProdutos = new StringJoiner(QR_NL);
        sqlProdutos
        .add("SELECT p")
        .add("FROM ".concat(Produto.class.getName()).concat(" p "))
        .add("WHERE p.ativo = :pAtivo");
        
        StringJoiner sqlFotos = new StringJoiner(QR_NL);
        sqlFotos
        .add("SELECT fp")
        .add("FROM ".concat(FotoProduto.class.getName()).concat(" fp "))
        .add("WHERE")
        .add("fp.ativo = :pAtivo");
        
        produtos = getEntityManager().createQuery(sqlProdutos.toString(), Produto.class)
                                     .setParameter("pAtivo", true)
                                     .getResultList();
        
        fotosProdutos = getEntityManager().createQuery(sqlFotos.toString(), FotoProduto.class)
                                          .setParameter("pAtivo", true)
                                          .getResultList();            
        
        for (Produto p : produtos) {
            TOProduto to = new TOProduto();
            getConverter().getTOFromModel(p, to);
            toProdutos.add(to);
        }
        
        for (FotoProduto fp : fotosProdutos) {
            TOFotoProduto to = new TOFotoProduto();
            getConverter().getTOFromModel(fp, to);
            toFotosProduto.add(to);
        }
        
        List<TOFotoProduto> toFotos = new ArrayList<>();
        for (TOProduto toProduto : toProdutos) {
            TOProdutoComFoto toProdutoComFoto = new TOProdutoComFoto();
            toProdutoComFoto.setToProduto(toProduto);

            for (TOFotoProduto toFotoProduto : toFotosProduto) {
                if (toFotoProduto.getToProduto().getId() == toProduto.getId()) {
                    toFotos.add(toFotoProduto);
                }
            }
            
            toProdutoComFoto.setToFotos(toFotos);
            toProdutosComFotos.add(toProdutoComFoto);
        }
        
        return toProdutosComFotos;
    }
}
