package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Tarefa;
import br.com.WebBakery.to.TOTarefa;

@Stateless
public class TarefaDao extends AbstractBaseDao<TOTarefa> {

    private static final long serialVersionUID = -8579725218176379779L;

    @Override
    public void salvar(TOTarefa to) throws Exception {
        Tarefa t = null;
        if (to.getId() == null) {
            t = new Tarefa();
        } else {
            t = getEntityManager().find(Tarefa.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, t);            
        
        getEntityManager().persist(t);
        getEntityManager().flush();
        to.setId(t.getId());
    }

    @Override
    public TOTarefa buscarPorId(Integer id) throws Exception {
        Tarefa t = getEntityManager().find(Tarefa.class, id);
        TOTarefa to = new TOTarefa();
        getConverter().getTOFromModel(t, to);
        
        return to;
    }

    @Override
    public List<TOTarefa> listarTodos(Boolean ativo) throws Exception {
        List<Tarefa> tarefas = new ArrayList<>();
        List<TOTarefa> toTarefas = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT t")
        .add("FROM ".concat(Tarefa.class.getName()).concat(" t "))
        .add("WHERE t.ativo = :pAtivo");

        tarefas = getEntityManager().createQuery(sql.toString(), Tarefa.class)
                                    .setParameter("pAtivo", ativo)
                                    .getResultList();
        
        for (Tarefa t : tarefas) {
            TOTarefa to = new TOTarefa();
            getConverter().getTOFromModel(t, to);
            toTarefas.add(to);
        }

        return toTarefas;
    }
    
    public List<Object[]> getNovoEstoque(Integer idReceita, Integer quantidadeProdutoTarefa) {
        StringBuilder sqlNovoEstoque = new StringBuilder(QR_NL);
        sqlNovoEstoque
        .append("select                                                                                                                                                                              ")
        .append("ei.id as idEstoque,                                                                                                                                                                 ")
        .append("i.nome_ingrediente as nomeIngrediente,                                                                                                                                              ")
        .append("(select ei.quantidade_estoque_ingrediente) - ((select ri.quantidade_ingrediente_receita_ingrediente) * (:pQuantidadeProdutoTarefa / (select r.quantidade_receita))) as novoEstoque, ")
        .append("i.unidade_medida_ingrediente                                                                                                                                                        ")
        .append("from estoque_ingrediente ei                                                                                                                                                         ")
        .append("inner join ingrediente i on i.id = ei.id_ingrediente_estoque_ingrediente                                                                                                            ")
        .append("inner join receita_ingrediente ri on ri.id_ingrediente_receita_ingrediente = i.id                                                                                                   ")
        .append("inner join receita r on r.id = ri.id_receita_receita_ingrediente                                                                                                                    ")
        .append("where ri.id_receita_receita_ingrediente = :pIdReceita and r.id = :pIdReceita                                                                                                        ");
        
        Query queryNovoEstoque = getEntityManager().createNativeQuery(sqlNovoEstoque.toString());
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = queryNovoEstoque.setParameter("pIdReceita", idReceita)
                                                    .setParameter("pQuantidadeProdutoTarefa", quantidadeProdutoTarefa)
                                                    .getResultList();
        
        return resultList;
    }
    
    public void descontarEstoque(Integer idReceita, Integer quantidadeProdutoTarefa) throws IllegalArgumentException {
        List<Object[]> resultList = getNovoEstoque(idReceita, quantidadeProdutoTarefa);
        
        for (Object[] obj : resultList) {
            updateEstoque((Integer) obj[0], (Double) obj[2]);
        }
    }

    private void updateEstoque(Integer idEstoqueIngrediente, Double novaQuantidadenstoque) {
        StringBuilder sqlUpdateEstoque = new StringBuilder(QR_NL);
        sqlUpdateEstoque
        .append("update estoque_ingrediente                                         ")
        .append("set quantidade_estoque_ingrediente = :pNovaQuantidadeEstoque       ")
        .append("where id = :pIdEstoqueIngrediente                                  ");
        
        getEntityManager().createNativeQuery(sqlUpdateEstoque.toString())
                          .setParameter("pNovaQuantidadeEstoque", novaQuantidadenstoque)
                          .setParameter("pIdEstoqueIngrediente", idEstoqueIngrediente)
                          .executeUpdate();
    }
}
