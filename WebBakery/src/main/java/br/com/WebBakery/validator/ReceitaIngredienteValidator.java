package br.com.WebBakery.validator;

import java.util.List;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOReceitaIngrediente;

public class ReceitaIngredienteValidator extends AbstractValidator {

    private List<TOReceitaIngrediente> toReceitasIngredientes;

    public ReceitaIngredienteValidator(List<TOReceitaIngrediente> toReceitasIngredientes) {
        this.toReceitasIngredientes = toReceitasIngredientes;
    }

    @Override
    public void chamarValidacoes() {
        validarQuantidades();

    }

    private void validarQuantidades() {
        for (TOReceitaIngrediente to : toReceitasIngredientes) {
            Double quantidadeIngrediente = to.getQuantidadeIngrediente();
            
            if (quantidadeIngrediente == null || quantidadeIngrediente <= 0) {
               messages.add("Quantidade do ingrediente " + to.getToIngrediente().getNome() + " é obrigatória.");      
            } 
        }
    }

}
