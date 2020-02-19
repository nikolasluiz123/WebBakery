package br.com.WebBakery.validator;

import java.util.Date;
import java.util.List;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.enums.EnumUnidadeMedida;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOTarefa;
import br.com.WebBakery.util.StringUtil;

public class TarefaValidator extends AbstractValidator {

    private static final String FIELD_PRODUTO_REQUIRED = "TOProduto é obrigatório!";
    private static final String FIELD_DATA_INICIO_REQUIRED = "Data de início é obrigatória!";
    private static final String FIELD_DATA_FIM_REQUIRED = "Data de fim é obrigatória!";
    private static final String FIELD_DATA_FIM_NOT_VALID = "Data de fim inválida!";
    private static final String FIELD_DATA_INICIO_NOT_VALID = "Data de início inválida!";

    private TOTarefa toTarefa;
    private TarefaDao tarefaDao;

    public TarefaValidator(TOTarefa tarefa, TarefaDao tarefaDao) {
        this.toTarefa = tarefa;
        this.tarefaDao = tarefaDao;
    }

    @Override
    public void chamarValidacoes() {
        validarProduto();
        validarDataInicioFim();
        validarIngredientesEstoque();
    }

    private void validarProduto() {
        TOProduto toProduto = this.toTarefa.getToProduto();

        if (toProduto == null) {
            messages.add(FIELD_PRODUTO_REQUIRED);
        }
    }

    private void validarDataInicioFim() {
        Date dataInicio = this.toTarefa.getDataInicio();
        Date dataFim = this.toTarefa.getDataFim();
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
        if (this.toTarefa.getToProduto() != null) {
            Integer idReceita = this.toTarefa.getToProduto().getToReceita().getId();
            Integer quantidadeProdutoTarefa = this.toTarefa.getQuantidade();
            
            List<Object[]> result = this.tarefaDao.getNovoEstoque(idReceita, quantidadeProdutoTarefa);
            
            for (Object[] obj : result) {
                Double quantidadeNovoEstoque = Double.parseDouble(obj[2].toString());
                
                if (quantidadeNovoEstoque < 0) {
                    EnumUnidadeMedida unidadeMedidaIngrediente = EnumUnidadeMedida.values()[Integer.parseInt(obj[3].toString())];
                    String nomeIngrediente = obj[1].toString();
                    String quantidadeNovoEstoqueFormatada = StringUtil.formatTwoDecimalPlaces(quantidadeNovoEstoque * -1);
                    messages.add("Não há " + nomeIngrediente + " suficiente no estoque. Faltam " + quantidadeNovoEstoqueFormatada  + " " + unidadeMedidaIngrediente);
                }
            }
        }
    }
}
