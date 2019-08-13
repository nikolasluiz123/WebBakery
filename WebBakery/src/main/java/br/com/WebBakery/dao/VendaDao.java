package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Venda;
import br.com.WebBakery.to.TOVenda;

@Stateless
public class VendaDao extends AbstractBaseDao<TOVenda> {

    private static final long serialVersionUID = 3684593379387389702L;

    @Override
    public void salvar(TOVenda to) throws Exception {
        Venda v = new Venda();
        getConverter().getModelFromTO(to, v);
        getEntityManager().merge(v);
    }

    @Override
    public TOVenda buscarPorId(Integer id) throws Exception {
        Venda v = getEntityManager().find(Venda.class, id);
        TOVenda to = new TOVenda();
        getConverter().getTOFromModel(v, to);
        
        return to;
    }

    @Override
    public List<TOVenda> listarTodos(Boolean ativo) throws Exception {
        List<Venda> vendas = new ArrayList<>();
        List<TOVenda> toVendas = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT v")
        .add("FROM ".concat(Venda.class.getName()).concat(" v "))
        .add("WHERE v.ativo = :pAtivo");
        
        vendas = getEntityManager().createQuery(sql.toString(), Venda.class)
                                   .setParameter("pAtivo", ativo)
                                   .getResultList();
        
        for (Venda v : vendas) {
            TOVenda to = new TOVenda();
            getConverter().getTOFromModel(v, to);
            toVendas.add(to);
        }
        
        return toVendas;
    }

}