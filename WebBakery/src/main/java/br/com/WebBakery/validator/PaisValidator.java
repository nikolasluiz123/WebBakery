package br.com.WebBakery.validator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBakery.model.Pais;

public class PaisValidator {

    private Pais pais;
    private List<String> messages = new ArrayList<>();

    public PaisValidator(Pais pais) {
        this.pais = pais;
    }

    public boolean ehValido() {
        validaNome();
        validaSigla();

        if (!messages.isEmpty()) {
            return false;
        }
        return true;
    }

    public void mostrarMensagens() {
        messages.forEach(message -> {
            FacesContext.getCurrentInstance()
                    .addMessage("formCadastroPais:messages",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        });
    }

    private void validaNome() {
        if (this.pais.getNome().isEmpty() || this.pais.getNome() == null) {
            messages.add("Nome obrigatório!");
        }
        if (this.pais.getNome().length() > 50) {
            messages.add("Nome inválido!");
        }
    }

    private void validaSigla() {
        if (this.pais.getSigla().isEmpty() || this.pais.getSigla() == null) {
            messages.add("Sigla obrigatória!");
        }
        if (this.pais.getSigla().length() > 2) {
            messages.add("Sigla inválida!");
        }
        String nomePaisMaiusculo = this.pais.getSigla().toUpperCase();
        this.pais.setSigla(nomePaisMaiusculo);
    }

    public boolean existe(List<Pais> paises) {
        for (Pais pais : paises) {
            String nomePaisSendoCadastradoMaisculo = this.pais.getNome().toUpperCase();
            String siglaPaisSendoCadastradoMaisculo = this.pais.getNome().toUpperCase();

            String nomePaisPercorridoMaisculo = pais.getNome().toUpperCase();
            String siglaPaisPercorridoMaisculo = pais.getNome().toUpperCase();

            if (nomePaisSendoCadastradoMaisculo.equals(nomePaisPercorridoMaisculo)
                    && siglaPaisSendoCadastradoMaisculo.equals(siglaPaisPercorridoMaisculo)) {
                pais.setAtivo(true);
                messages.add("Pais já cadastrado!");
                return true;
            }
        }
        return false;
    }

    public void clearMessages() {
        this.messages.clear();
    }
}
