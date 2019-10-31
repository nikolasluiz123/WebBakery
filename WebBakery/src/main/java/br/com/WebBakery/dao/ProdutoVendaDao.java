package br.com.WebBakery.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.entitys.ProdutoVenda;
import br.com.WebBakery.model.graphics.ProdutoVendaGraphicValues;
import br.com.WebBakery.to.TOProdutoVenda;

@Stateless
public class ProdutoVendaDao extends AbstractBaseDao<TOProdutoVenda> {
    
    private static final long serialVersionUID = -1109157795344801242L;

    @Override
    public void salvar(TOProdutoVenda to) throws Exception {
        ProdutoVenda pv = null;
        if (to.getId() == null) {
            pv = new ProdutoVenda();
        } else {
            pv = getEntityManager().find(ProdutoVenda.class, to.getId());
        }
        
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

    public List<TOProdutoVenda> buscarPorIdVenda(Integer IdVenda) throws Exception {
        List<ProdutoVenda> produtosVendas = new ArrayList<>();
        List<TOProdutoVenda> toProdutosVendas = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT pv                                               ")
        .add("FROM ".concat(ProdutoVenda.class.getName()).concat(" pv "))
        .add("WHERE                                                   ")
        .add("pv.ativo = :pAtivo                                      ")
        .add("AND pv.venda.id = :pIdVenda                             ");
        
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
    
    public List<ProdutoVendaGraphicValues> getCincoProdutosMaisVendidos() {
        
        List<ProdutoVendaGraphicValues> listGraphicValues = new ArrayList<>();
        
        StringBuilder sql  = new StringBuilder(QR_NL);
        sql
        .append("select                                                                                                 ")
        .append("p.descricao_produto as nomeProduto,                                                                    ")
        .append("(select sum(pv.quantidade_produto_venda)) as quantidadeVendida,                                        ")
        .append("(select round(cast(sum(p.preco_produto * pv.quantidade_produto_venda) as numeric), 2)) as lucro        ")
        .append("from produto_venda pv                                                                                  ")
        .append("inner join produto p on p.id = pv.id_produto_produto_venda                                             ")
        .append("where pv.ativo = true and p.ativo = true                                                               ")
        .append("group by p.id                                                                                          ")
        .append("order by quantidadeVendida desc                                                                        ")
        .append("limit 5                                                                                                ");
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = getEntityManager().createNativeQuery(sql.toString()).getResultList();
        
        for (Object[] obj : resultList) {
            ProdutoVendaGraphicValues graphicValues = new ProdutoVendaGraphicValues((String) obj[0], (BigInteger) obj[1], (BigDecimal) obj[2]);
            listGraphicValues.add(graphicValues);
        }
        
        return listGraphicValues;
    }

    @Override
    public List<TOProdutoVenda> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

}
