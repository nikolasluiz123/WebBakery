package br.com.WebBakery.validator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBakery.model.Estado;

public class EstadoValidator {

    private Estado estado;

    private List<String> messages = new ArrayList<>();

    public EstadoValidator(Estado estado) {
        this.estado = estado;
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
                    .addMessage("formCadastroEstado:messages",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        });

    }

    private void validaNome() {
        if (this.estado.getNome().isEmpty() || this.estado.getNome() == null) {
            messages.add("Nome obrigatório!");
        }
        if (this.estado.getNome().length() > 50) {
            messages.add("Nome inválido!");
        }
    }

    private void validaSigla() {
        if (this.estado.getSigla().isEmpty() || this.estado.getSigla() == null) {
            messages.add("Sigla obrigatória!");
        }
        if (this.estado.getSigla().length() > 2) {
            messages.add("Sigla inválida!");
        }
        String nomeEstadoMaiusculo = this.estado.getSigla().toUpperCase();
        this.estado.setSigla(nomeEstadoMaiusculo);
    }

    public boolean existe(List<Estado> estados) {
        for (Estado estado : estados) {
            String nomeEstadoSendoCadastradoMaisculo = this.estado.getNome().toUpperCase();
            String siglaEstadoSendoCadastradoMaisculo = this.estado.getNome().toUpperCase();

            String nomeEstadoPercorridoMaisculo = estado.getNome().toUpperCase();
            String siglaEstadoPercorridoMaisculo = estado.getNome().toUpperCase();

            if (nomeEstadoSendoCadastradoMaisculo.equals(nomeEstadoPercorridoMaisculo)
                    && siglaEstadoSendoCadastradoMaisculo.equals(siglaEstadoPercorridoMaisculo)) {
                estado.setAtivo(true);
                messages.add("Estado já cadastrado!");
                return true;
            }
        }
        return false;
    }

    public void clearMessages() {
        this.messages.clear();
    }
}
