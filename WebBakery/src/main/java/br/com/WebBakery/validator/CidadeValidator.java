package br.com.WebBakery.validator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBakery.model.Cidade;

public class CidadeValidator {

    private Cidade cidade;

    private List<String> messages = new ArrayList<>();

    public CidadeValidator(Cidade cidade) {
        this.cidade = cidade;
    }

    public boolean ehValido() {
        validaNome();

        if (!messages.isEmpty()) {
            return false;
        }
        return true;
    }

    public void mostrarMensagens() {
        messages.forEach(message -> {
            FacesContext.getCurrentInstance()
                    .addMessage("formCadastroCidade:messages",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        });

    }

    private void validaNome() {
        if (this.cidade.getNome().isEmpty() || this.cidade.getNome() == null) {
            messages.add("Nome obrigatório!");
        }
        if (this.cidade.getNome().length() > 50) {
            messages.add("Nome inválido!");
        }
    }

    public boolean existe(List<Cidade> cidades) {
        for (Cidade cidade : cidades) {
            String nomeCidadeSendoCadastradoMaisculo = this.cidade.getNome().toUpperCase();
            String nomeCidadePercorridoMaisculo = cidade.getNome().toUpperCase();

            if (nomeCidadeSendoCadastradoMaisculo.equals(nomeCidadePercorridoMaisculo)) {
                cidade.setAtivo(true);
                messages.add("Cidade já cadastrada!");
                return true;
            }
        }
        return false;
    }

    public void clearMessages() {
        this.messages.clear();
    }
}
