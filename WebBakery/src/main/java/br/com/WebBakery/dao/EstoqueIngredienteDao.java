package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.EstoqueIngrediente;
import br.com.WebBakery.to.TOEstoqueIngrediente;

public class EstoqueIngredienteDao extends AbstractBaseDao<TOEstoqueIngrediente> {

    private static final long serialVersionUID = 7869019362295500463L;

    @Override
    public void salvar(TOEstoqueIngrediente to) throws Exception {
        EstoqueIngrediente estoqueIngredienteDoBanco = existe(to.getToIngrediente().getId());
        
        if (estoqueIngredienteDoBanco != null) {
            Double quantidadeRegistrada = to.getQuantidade();
            Double quantidadeDoBanco = estoqueIngredienteDoBanco.getQuantidade();
            estoqueIngredienteDoBanco.setQuantidade(quantidadeRegistrada + quantidadeDoBanco);
            
            getEntityManager().persist(estoqueIngredienteDoBanco);
        } else {
            EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
            getConverter().getModelFromTO(to, estoqueIngrediente);
            getEntityManager().persist(estoqueIngrediente);
        }
    }

    @Override
    public List<TOEstoqueIngrediente> listarTodos(Boolean ativo) throws Exception {
        List<EstoqueIngrediente> estoqueIngredientes = new ArrayList<>();
        List<TOEstoqueIngrediente> toEstoqueIngredientes = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ei")
        .add("FROM ".concat(EstoqueIngrediente.class.getName()).concat(" ei "))
        .add("WHERE ei.ativo = :pAtivo")
        .add("ORDER BY ei.quantidade");
        
        estoqueIngredientes = getEntityManager().createQuery(sql.toString(), EstoqueIngrediente.class)
                                                .setParameter("pAtivo", ativo)
                                                .getResultList();
        
        for (EstoqueIngrediente ei : estoqueIngredientes) {
            TOEstoqueIngrediente to = new TOEstoqueIngrediente();
            getConverter().getTOFromModel(ei, to);
            toEstoqueIngredientes.add(to);
        }
        
        return toEstoqueIngredientes;
    }

    @Override
    public TOEstoqueIngrediente buscarPorId(Integer id) throws Exception {
        EstoqueIngrediente ei = getEntityManager().find(EstoqueIngrediente.class, id);
        TOEstoqueIngrediente to = new TOEstoqueIngrediente();
        getConverter().getTOFromModel(ei, to);

        return to;
    }
    
    private EstoqueIngrediente existe(Integer ingredienteId) {
        EstoqueIngrediente ep = new EstoqueIngrediente();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ei")
        .add("FROM ".concat(EstoqueIngrediente.class.getName()).concat(" ei "))
        .add("WHERE")
        .add("ei.ativo = :pAtivo")
        .add("AND ei.ingrediente.id = :pIngredienteId");
        
        try {
            
        ep = getEntityManager().createQuery(sql.toString(), EstoqueIngrediente.class)
                               .setParameter("pAtivo", true)
                               .setParameter("pIngredienteId", ingredienteId)
                               .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
       
        return ep;
    }

}
