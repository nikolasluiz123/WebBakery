package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOEstoqueIngrediente;

public class EstoqueIngredienteValidator extends AbstractValidator {

    private static final String FIELD_QUANTIDADE_NOT_VALID = "Quantidade inválida!";
    private static final String FIELD_INGREDIENTE_REQUIRED = "Ingrediente é obrigatório!";
    
    private TOEstoqueIngrediente toEstoqueIngrediente;
    
    public EstoqueIngredienteValidator(TOEstoqueIngrediente toEstoqueIngrediente) {
        this.toEstoqueIngrediente = toEstoqueIngrediente;
    }

    @Override
    public void chamarValidacoes() {
        validarQuantidade();
        validarIngrediente();
    }

    private void validarQuantidade() {
        if (this.toEstoqueIngrediente.getQuantidade() <= 0) {
            messages.add(FIELD_QUANTIDADE_NOT_VALID);
        }
    }
    
    private void validarIngrediente() {
        if (this.toEstoqueIngrediente.getToIngrediente() == null) {
            messages.add(FIELD_INGREDIENTE_REQUIRED);
        }
    }
}
