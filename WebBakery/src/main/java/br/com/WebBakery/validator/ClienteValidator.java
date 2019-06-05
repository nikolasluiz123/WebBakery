package br.com.WebBakery.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.WebBaker.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.util.Cpf_Util;

public class ClienteValidator extends AbstractValidator {

    private Cliente cliente;

    private List<String> messages = new ArrayList<>();

    public ClienteValidator(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaSobrenome();
        validaDataNascimento();
        validaCpf();
        validaTelefone();
        validaUsuario();
    }

    private void validaUsuario() {
        if (this.cliente.getUsuario() == null) {
            messages.add("Usuário é obrigatório!");
        }
        if (this.cliente.getUsuario().getEmail() == null
                || this.cliente.getUsuario().getEmail().isEmpty()) {
            messages.add("E-mail é obrigatório!");
        }
        if (this.cliente.getUsuario().getSenha() == null
                || this.cliente.getUsuario().getSenha().isEmpty()) {
            messages.add("Senha é obrigatória!");
        }
    }

    private void validaTelefone() {
        if (this.cliente.getTelefone().isEmpty() || this.cliente.getTelefone() == null) {
            messages.add("Telefone é obrigatório!");
        }
    }

    private void validaCpf() {
        if (this.cliente.getCpf().isEmpty() || this.cliente.getCpf() == null) {
            messages.add("Cpf é obrigatório!");
        }
        if (!Cpf_Util.isValid(this.cliente.getCpf())) {
            messages.add("Cpf inválido!");
        }
    }

    private void validaDataNascimento() {
        if (this.cliente.getDataNascimento() == null) {
            messages.add("Data de nascimento obrigatória!");
        }
        if (this.cliente.getDataNascimento().after(new Date())) {
            messages.add("Data de nascimento inválida!");
        }
    }

    private void validaSobrenome() {
        if (this.cliente.getSobrenome().isEmpty() || this.cliente.getSobrenome() == null) {
            messages.add("Sobrenome obrigatório!");
        }
        if (this.cliente.getSobrenome().length() > 50) {
            messages.add("Sobrenome inválido!");
        }
    }

    private void validaNome() {
        if (this.cliente.getNome().isEmpty() || this.cliente.getNome() == null) {
            messages.add("Nome obrigatório!");
        }
        if (this.cliente.getNome().length() > 50) {
            messages.add("Nome inválido!");
        }
    }

    public boolean existe(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {

            String nomeClienteSendoCadastradoMaisculo = this.cliente.getNome().toUpperCase();
            String nomeClienteSendoPercorridoMaisculo = cliente.getNome().toUpperCase();

            String sobrenomeClienteSendoCadastradoMaisculo = this.cliente.getSobrenome()
                    .toUpperCase();
            String sobrenomeClienteSendoPercorridoMaisculo = cliente.getSobrenome().toUpperCase();

            Long dataNascimentoClienteSendoCadastrado = this.cliente.getDataNascimento().getTime();
            Long dataNascimentoClienteSendoPercorrido = cliente.getDataNascimento().getTime();

            String telefoneClienteSendoCadastrado = this.cliente.getTelefone();
            String telefoneClienteSendoPercorrido = cliente.getTelefone();

            String emailClienteSendoCadastradoMaisculo = this.cliente.getUsuario().getEmail()
                    .toUpperCase();
            String emailClienteSendoPercorridoMaisculo = cliente.getUsuario().getEmail()
                    .toUpperCase();

            String senhaClienteSendoCadastradoMaisculo = this.cliente.getUsuario().getSenha()
                    .toUpperCase();
            String senhaClienteSendoPercorridoMaisculo = cliente.getUsuario().getSenha()
                    .toUpperCase();

            Integer paisEnderecoClienteSendoCadastrado = this.cliente.getEndereco().getPais()
                    .getId();
            Integer paisEnderecoClienteSendoPercorrido = cliente.getEndereco().getPais().getId();

            Integer estadoEnderecoClienteSendoCadastrado = this.cliente.getEndereco().getEstado()
                    .getId();
            Integer estadoEnderecoClienteSendoPercorrido = cliente.getEndereco().getEstado()
                    .getId();

            Integer cidadeEnderecoClienteSendoCadastrado = this.cliente.getEndereco().getCidade()
                    .getId();
            Integer cidadeEnderecoClienteSendoPercorrido = cliente.getEndereco().getCidade()
                    .getId();

            String bairroLogradouroEnderecoClienteSendoCadastradoMaiusculo = this.cliente
                    .getEndereco().getLogradouro().getBairro().toUpperCase();
            String bairroLogradouroEnderecoClienteSendoPercorridoMaiusculo = cliente.getEndereco()
                    .getLogradouro().getBairro().toUpperCase();

            String cepLogradouroEnderecoClienteSendoCadastradoMaiusculo = this.cliente.getEndereco()
                    .getLogradouro().getCep().toUpperCase();
            String cepLogradouroEnderecoClienteSendoPercorridoMaiusculo = cliente.getEndereco()
                    .getLogradouro().getCep().toUpperCase();

            String ruaLogradouroEnderecoClienteSendoCadastradoMaiusculo = this.cliente.getEndereco()
                    .getLogradouro().getRua().toUpperCase();
            String ruaLogradouroEnderecoClienteSendoPercorridoMaiusculo = cliente.getEndereco()
                    .getLogradouro().getRua().toUpperCase();

            String complementoLogradouroEnderecoClienteSendoCadastradoMaiusculo = this.cliente
                    .getEndereco().getLogradouro().getComplemento().toUpperCase();
            String complementoLogradouroEnderecoClienteSendoPercorridoMaiusculo = cliente
                    .getEndereco().getLogradouro().getComplemento().toUpperCase();

            if (nomeClienteSendoCadastradoMaisculo.equals(nomeClienteSendoPercorridoMaisculo)
                    && sobrenomeClienteSendoCadastradoMaisculo
                            .equals(sobrenomeClienteSendoPercorridoMaisculo)
                    && dataNascimentoClienteSendoCadastrado
                            .equals(dataNascimentoClienteSendoPercorrido)
                    && telefoneClienteSendoCadastrado.equals(telefoneClienteSendoPercorrido)
                    && emailClienteSendoCadastradoMaisculo
                            .equals(emailClienteSendoPercorridoMaisculo)
                    && senhaClienteSendoCadastradoMaisculo
                            .equals(senhaClienteSendoPercorridoMaisculo)
                    && paisEnderecoClienteSendoCadastrado.equals(paisEnderecoClienteSendoPercorrido)
                    && estadoEnderecoClienteSendoCadastrado
                            .equals(estadoEnderecoClienteSendoPercorrido)
                    && cidadeEnderecoClienteSendoCadastrado
                            .equals(cidadeEnderecoClienteSendoPercorrido)
                    && bairroLogradouroEnderecoClienteSendoCadastradoMaiusculo
                            .equals(bairroLogradouroEnderecoClienteSendoPercorridoMaiusculo)
                    && cepLogradouroEnderecoClienteSendoCadastradoMaiusculo
                            .equals(cepLogradouroEnderecoClienteSendoPercorridoMaiusculo)
                    && ruaLogradouroEnderecoClienteSendoCadastradoMaiusculo
                            .equals(ruaLogradouroEnderecoClienteSendoPercorridoMaiusculo)
                    && complementoLogradouroEnderecoClienteSendoCadastradoMaiusculo
                            .equals(complementoLogradouroEnderecoClienteSendoPercorridoMaiusculo)) {
                cliente.setAtivo(true);
                return true;
            }
        }
        return false;
    }
}
