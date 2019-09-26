package br.com.WebBakery.validator;

import java.util.Date;
import java.util.List;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.EstoqueIngredienteDao;
import br.com.WebBakery.dao.ReceitaIngredienteDao;
import br.com.WebBakery.to.TOEstoqueIngrediente;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOReceitaIngrediente;
import br.com.WebBakery.to.TOTarefa;

public class TarefaValidator extends AbstractValidator {

    private static final String FIELD_PRODUTO_REQUIRED = "TOProduto é obrigatório!";
    private static final String FIELD_DATA_INICIO_REQUIRED = "Data de início é obrigatória!";
    private static final String FIELD_DATA_FIM_REQUIRED = "Data de fim é obrigatória!";
    private static final String FIELD_DATA_FIM_NOT_VALID = "Data de fim inválida!";
    private static final String FIELD_DATA_INICIO_NOT_VALID = "Data de início inválida!";

    private TOTarefa tarefa;
    private ReceitaIngredienteDao receitaIngredienteDao;
    private EstoqueIngredienteDao estoqueIngredienteDao;

    public TarefaValidator(TOTarefa tarefa,
                           ReceitaIngredienteDao receitaIngredienteDao,
                           EstoqueIngredienteDao estoqueIngredienteDao) {
        this.tarefa = tarefa;
        this.receitaIngredienteDao = receitaIngredienteDao;
        this.estoqueIngredienteDao = estoqueIngredienteDao;
    }

    @Override
    public void chamarValidacoes() {
        validarProduto();
        validarDataInicioFim();
        validarIngredientesEstoque();
    }

    private void validarProduto() {
        TOProduto toProduto = this.tarefa.getToProduto();

        if (toProduto == null) {
            messages.add(FIELD_PRODUTO_REQUIRED);
        }
    }

    private void validarDataInicioFim() {
        Date dataInicio = this.tarefa.getDataInicio();
        Date dataFim = this.tarefa.getDataFim();
        Date hoje = new Date();

        if (dataInicio == null) {
            messages.add(FIELD_DATA_INICIO_REQUIRED);
        } else if (dataFim.before(dataInicio)) {
            messages.add(FIELD_DATA_FIM_NOT_VALID);
        }
        if (dataFim == null) {
            messages.add(FIELD_DATA_FIM_REQUIRED);
        } else if (dataInicio.before(hoje)) {
            messages.add(FIELD_DATA_INICIO_NOT_VALID);
        }
    }

    private void validarIngredientesEstoque() {
        try {
            Integer idReceita = this.tarefa.getToProduto().getToReceita().getId();
            List<TOReceitaIngrediente> toReceitaIngredientes = this.receitaIngredienteDao
                    .listarTodos(true, idReceita);

            List<TOEstoqueIngrediente> toEstoqueIngredientes = this.estoqueIngredienteDao
                    .listarTodos(true, toReceitaIngredientes);

            if (!toEstoqueIngredientes.isEmpty()) {
                descontarEstoqueIngredientes(toReceitaIngredientes, toEstoqueIngredientes);
            } else {
                messages.add("Não há ingredientes em estoque.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void descontarEstoqueIngredientes(List<TOReceitaIngrediente> toReceitaIngredientes,
                                              List<TOEstoqueIngrediente> toEstoqueIngredientes)
            throws Exception {
        for (TOReceitaIngrediente toReceitaIngrediente : toReceitaIngredientes) {
            for (TOEstoqueIngrediente toEstoqueIngrediente : toEstoqueIngredientes) {
                descontar(toReceitaIngrediente, toEstoqueIngrediente);
            }
        }
    }

    private void descontar(TOReceitaIngrediente toReceitaIngrediente,
                           TOEstoqueIngrediente toEstoqueIngrediente)
            throws Exception {

        String nomeIngrediente = toReceitaIngrediente.getToIngrediente().getNome();
        Double quantidadeIngredienteReceita = toReceitaIngrediente.getQuantidadeIngrediente();
        Double quantidadeIngredienteEstoque = toEstoqueIngrediente.getQuantidade();

        if (quantidadeIngredienteReceita > quantidadeIngredienteEstoque) {
            messages.add("Não há " + nomeIngrediente + " suficiente para fazer a receita.");
        } else {
            quantidadeIngredienteReceita = quantidadeIngredienteReceita * (this.tarefa.getQuantidade() / quantidadeIngredienteReceita); 
            this.estoqueIngredienteDao.descontarEstoque(toEstoqueIngrediente,
                                                        quantidadeIngredienteReceita);
        }
    }

}
