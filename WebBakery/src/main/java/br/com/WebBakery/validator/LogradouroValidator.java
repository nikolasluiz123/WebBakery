package br.com.WebBakery.validator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBakery.model.Logradouro;
import br.com.WebBakery.util.Cep_Util;

public class LogradouroValidator {

    private Logradouro logradouro;

    private List<String> messages = new ArrayList<>();

    public LogradouroValidator(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public boolean ehValido() {
        validaBairro();
        validaCep();
        validaRua();
        validaComplemento();

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

    private void validaBairro() {
        if (this.logradouro.getBairro().isEmpty() || this.logradouro.getBairro() == null) {
            messages.add("Bairro obrigat�rio!");
        }
        if (this.logradouro.getBairro().length() > 50) {
            messages.add("Bairro inv�lido!");
        }
    }

    private void validaCep() {
        if (this.logradouro.getCep().isEmpty() || this.logradouro.getCep() == null) {
            messages.add("Cep obrigat�rio!");
        }
        if (this.logradouro.getCep().length() > 9 || !Cep_Util.EhValido(this.logradouro.getCep())) {
            messages.add("Cep inv�lido!");
        }
    }

    private void validaRua() {
        if (this.logradouro.getRua().isEmpty() || this.logradouro.getRua() == null) {
            messages.add("Rua obrigat�ria!");
        }
        if (this.logradouro.getRua().length() > 50) {
            messages.add("Rua inv�lida!");
        }
    }

    private void validaComplemento() {
        if (this.logradouro.getComplemento().isEmpty()
                || this.logradouro.getComplemento() == null) {
            messages.add("Complemento obrigat�rio!");
        }
        if (this.logradouro.getComplemento().length() > 50) {
            messages.add("Complemento inv�lido!");
        }
    }

    public boolean existe(List<Logradouro> logradouros) {
        for (Logradouro logradouro : logradouros) {
            String bairroSendoCadastradoMaiusculo = this.logradouro.getBairro().toUpperCase();
            String bairroSendoPercorridoMaiusculo = logradouro.getBairro().toUpperCase();

            String cepSendoCadastradoMaiusculo = this.logradouro.getCep().toUpperCase();
            String cepSendoPercorridoMaiusculo = logradouro.getCep().toUpperCase();

            String ruaSendoCadastradoMaiuscula = this.logradouro.getRua().toUpperCase();
            String ruaSendoPercorridaMaiuscula = logradouro.getRua().toUpperCase();

            String complementoSendoCadastradoMaiusculo = this.logradouro.getComplemento()
                    .toUpperCase();
            String complementoSendoPercorridoMaiusculo = logradouro.getComplemento().toUpperCase();

            if (bairroSendoCadastradoMaiusculo.equals(bairroSendoPercorridoMaiusculo)
                    && cepSendoCadastradoMaiusculo.equals(cepSendoPercorridoMaiusculo)
                    && ruaSendoCadastradoMaiuscula.equals(ruaSendoPercorridaMaiuscula)
                    && complementoSendoCadastradoMaiusculo
                            .equals(complementoSendoPercorridoMaiusculo)) {
                logradouro.setAtivo(true);
                messages.add("Logradouro j� cadastrado!");
                return true;
            }
        }
        return false;
    }

    public void clearMessages() {
        this.messages.clear();
    }
}
