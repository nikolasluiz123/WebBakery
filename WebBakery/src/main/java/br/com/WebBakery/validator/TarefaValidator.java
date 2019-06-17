package br.com.WebBakery.validator;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.model.Tarefa;

public class TarefaValidator extends AbstractValidator {

    private static final String FIELD_PRODUTO_REQUIRED = "Produto é obrigatório!";
    private static final String FIELD_DATA_INICIO_REQUIRED = "Data de início é obrigatória!";
    private static final String FIELD_DATA_FIM_REQUIRED = "Data de fim é obrigatória!";
    private static final String FIELD_DATA_FIM_NOT_VALID = "Data de fim inválida!";
    private static final String FIELD_DATA_INICIO_NOT_VALID = "Data de início inválida!";

    private Tarefa tarefa;

    public TarefaValidator(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    @Override
    public void chamarValidacoes() {
        validaProduto();
        validaDataInicioFim();
    }

    private void validaProduto() {
        Produto produto = this.tarefa.getProduto();

        if (produto == null) {
            messages.add(FIELD_PRODUTO_REQUIRED);
        }
    }

    private void validaDataInicioFim() {
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

}
