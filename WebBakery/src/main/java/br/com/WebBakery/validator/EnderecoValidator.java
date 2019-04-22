package br.com.WebBakery.validator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Endereco;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.model.Logradouro;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.util.Cep_Util;

public class EnderecoValidator {

    private Endereco endereco;

    private List<String> messages = new ArrayList<>();

    public EnderecoValidator(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean ehValido() {
        validaPais();
        validaEstado();
        validaCidade();
        validaLogradouro();

        if (!messages.isEmpty()) {
            return false;
        }
        return true;
    }

    public void mostrarMensagens() {
        messages.forEach(message -> {
            FacesContext.getCurrentInstance()
                    .addMessage("formCadastroFuncionario:messages",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        });

    }

    private void validaPais() {
        if (this.endereco.getPais() == null) {
            this.messages.add("País obrigatório!");
        }
    }

    private void validaEstado() {
        if (this.endereco.getEstado() == null) {
            this.messages.add("Estado obrigatório!");
        }
    }

    private void validaCidade() {
        if (this.endereco.getCidade() == null) {
            this.messages.add("Cidade obrigatória!");
        }
    }

    private void validaLogradouro() {
        if (this.endereco.getLogradouro().getBairro().isEmpty()
                || this.endereco.getLogradouro().getBairro() == null) {
            this.messages.add("Bairro obrigatório!");
        }
        if (this.endereco.getLogradouro().getBairro().length() > 30) {
            this.messages.add("Bairro inválido!");
        }
        if (this.endereco.getLogradouro().getRua().isEmpty()
                || this.endereco.getLogradouro().getRua() == null) {
            this.messages.add("Rua obrigatória!");
        }
        if (this.endereco.getLogradouro().getRua().length() > 30) {
            this.messages.add("Rua inválida!");
        }
        if (this.endereco.getLogradouro().getComplemento().isEmpty()
                || this.endereco.getLogradouro().getComplemento() == null) {
            this.messages.add("Complemento obrigatório!");
        }
        if (this.endereco.getLogradouro().getComplemento().length() > 20) {
            this.messages.add("Complemento inválido!");
        }
        if (this.endereco.getLogradouro().getCep().isEmpty()
                || this.endereco.getLogradouro().getCep() == null) {
            this.messages.add("Cep obrigatório!");
        }
        if (!Cep_Util.EhValido(this.endereco.getLogradouro().getCep())) {
            this.messages.add("Cep inválido!");
        }

    }

    public boolean existe(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {

            Pais paisSendoCadastrado = this.endereco.getPais();
            Pais paisSendoPercorrido = endereco.getPais();

            Estado estadoSendoCadastrado = this.endereco.getEstado();
            Estado estadoSendoPercorrido = endereco.getEstado();

            Cidade cidadeSendoCadastrada = this.endereco.getCidade();
            Cidade cidadeSendoPercorrida = endereco.getCidade();

            Logradouro logradouroSendoCadastrado = this.endereco.getLogradouro();
            Logradouro logradouroSendoPercorrido = endereco.getLogradouro();

            if (paisSendoCadastrado.equals(paisSendoPercorrido)
                    && estadoSendoCadastrado.equals(estadoSendoPercorrido)
                    && cidadeSendoCadastrada.equals(cidadeSendoPercorrida)
                    && logradouroSendoCadastrado.equals(logradouroSendoPercorrido)) {
                endereco.setAtivo(true);
                messages.add("Endereço já cadastrado!");
                return true;
            }
        }
        return false;
    }

    public void clearMessages() {
        this.messages.clear();
    }
}
