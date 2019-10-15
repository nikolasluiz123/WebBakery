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
        .append("select                                                                                                                                                     ")
        .append("ei.id as idEstoque,                                                                                                                                        ")
        .append("i.nome_ingrediente as nomeIngrediente,                                                                                                                     ")
        .append("(select :pNovaQuantidadeEstoque) - ((select r.quantidade_receita) * (:pQuantidadeProdutoTarefa / (select r.quantidade_receita))) as novoEstoque  ")
        .append("from estoque_ingrediente ei                                                                                                                                ")
        .append("inner join ingrediente i on i.id = ei.id_ingrediente_estoque_ingrediente                                                                                   ")
        .append("inner join receita_ingrediente ri on ri.id_ingrediente_receita_ingrediente = i.id                                                                          ")
        .append("inner join receita r on r.id = ri.id_receita_receita_ingrediente                                                                                           ")
        .append("where ri.id_receita_receita_ingrediente = :pIdReceita and r.id = :pIdReceita                                                                               ");
        
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
            updateEstoque((Integer) obj[0], (Integer) obj[2]);
        }
    }

    private void updateEstoque(Integer idEstoqueIngrediente, Integer novaQuantidadeEstoque) {
        StringBuilder sqlUpdateEstoque = new StringBuilder(QR_NL);
        sqlUpdateEstoque
        .append("update estoque_ingrediente ei")
        .append("set :pNovaQuantidadeEstoque = :pNovaQuantidadeEstoque")
        .append("where ei.id = :pIdEstoqueIngrediente");
        
        Query queryUpdateEstoque = getEntityManager().createNativeQuery(sqlUpdateEstoque.toString());
        queryUpdateEstoque.setParameter("pNovaQuantidadeEstoque", novaQuantidadeEstoque)
                          .setParameter("pIdEstoqueIngrediente", idEstoqueIngrediente)
                          .executeUpdate();
    }
    

    public String getUnidadeMedidaIngrediente(Integer idEstoque, Double quantidadeNovoEstoque) {
        StringBuilder sqlGetUnidadeMedidaExtenso = new StringBuilder(QR_NL);
        
        sqlGetUnidadeMedidaExtenso
        .append("select                                                                                                                                                                                                             ")
        .append("case                                                                                                                                                                                                               ")
        .append("when i.unidade_medida_ingrediente = 0 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Quilogramas'                       ")
        .append("when i.unidade_medida_ingrediente = 0 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Quilograma'                                                                        ")
        .append("when i.unidade_medida_ingrediente = 1 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Gramas'                            ")
        .append("when i.unidade_medida_ingrediente = 1 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Grama'                                                                             ")
        .append("when i.unidade_medida_ingrediente = 2 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Miligramas'                        ")
        .append("when i.unidade_medida_ingrediente = 2 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Miligrama'                                                                         ")
        .append("when i.unidade_medida_ingrediente = 3 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Litros'                            ")
        .append("when i.unidade_medida_ingrediente = 3 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Litro'                                                                             ")
        .append("when i.unidade_medida_ingrediente = 4 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Mililitros'                        ")
        .append("when i.unidade_medida_ingrediente = 4 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Mililitro'                                                                         ")
        .append("when i.unidade_medida_ingrediente = 5 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Xícaras'                           ")
        .append("when i.unidade_medida_ingrediente = 5 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Xícara'                                                                            ")
        .append("when i.unidade_medida_ingrediente = 6 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Colheres de Sopa'                  ")
        .append("when i.unidade_medida_ingrediente = 6 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Colher de Sopa'                                                                    ")
        .append("when i.unidade_medida_ingrediente = 7 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Colheres de Chá'                   ")
        .append("when i.unidade_medida_ingrediente = 7 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Colher de Chá'                                                                     ")
        .append("when i.unidade_medida_ingrediente = 8 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Colheres de Sobremesa'             ")
        .append("when i.unidade_medida_ingrediente = 8 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Colher de Sobremesa'                                                               ")
        .append("when i.unidade_medida_ingrediente = 9 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Pitadas'                           ")
        .append("when i.unidade_medida_ingrediente = 9 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Pitada'                                                                            ")
        .append("when i.unidade_medida_ingrediente = 10 and (:pNovaQuantidadeEstoque > 1 or (:pNovaQuantidadeEstoque <= 0 and :pNovaQuantidadeEstoque != -1)) then 'Unidades'                         ")
        .append("when i.unidade_medida_ingrediente = 10 and :pNovaQuantidadeEstoque = 1 or :pNovaQuantidadeEstoque = -1 then 'Unidade' end as unidade_de_medida                                                 ")
        .append("from estoque_ingrediente ei                                                                                                                                                                                        ")
        .append("inner join ingrediente i on i.id = ei.id_ingrediente_estoque_ingrediente                                                                                                                                           ")
        .append("where ei.id = :pIdEstoqueIngrediente                                                                                                                                                                               ");
        
        String unidade = (String) getEntityManager().createNativeQuery(sqlGetUnidadeMedidaExtenso.toString())
                                                    .setParameter("pIdEstoqueIngrediente", idEstoque)
                                                    .setParameter("pNovaQuantidadeEstoque", quantidadeNovoEstoque)
                                                    .getSingleResult();
        
        return unidade;
    }

}
