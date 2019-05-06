package br.com.WebBakery.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBakery.model.Funcionario;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.Cpf_Util;

public class FuncionarioValidator {

    private Funcionario funcionario;

    private List<String> messages = new ArrayList<>();

    public FuncionarioValidator(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public boolean ehValido() {
        validaNome();
        validaSobrenome();
        validaCpf();
        validaRg();
        validaTelefone();
        validaDataNascimento();
        validaSalario();
        validaUsuario();

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

    private void validaNome() {
        if (this.funcionario.getNome().isEmpty() || this.funcionario.getNome() == null) {
            this.messages.add("Nome obrigat�rio!");
        }
        if (this.funcionario.getNome().length() > 50) {
            this.messages.add("Nome inv�lido!");
        }
    }

    private void validaSobrenome() {
        if (this.funcionario.getSobrenome().isEmpty() || this.funcionario.getSobrenome() == null) {
            this.messages.add("Sobrenome obrigat�rio!");
        }
        if (this.funcionario.getSobrenome().length() > 50) {
            this.messages.add("Sobrenome inv�lido!");
        }
    }

    private void validaCpf() {
        String cpf = this.funcionario.getCpf().replace(".", "").replace("-", "");
        if (cpf.isEmpty() || cpf == null) {
            this.messages.add("CPF obrigat�rio!");
        }
        if (!Cpf_Util.isValid(cpf)) {
            this.messages.add("CPF inv�lido!");
        }
    }

    private void validaRg() {
        String rg = this.funcionario.getRg().replace(".", "");
        if (rg.isEmpty() || rg == null) {
            this.messages.add("RG obrigat�rio!");
        }
        if (rg.length() != 7) {
            this.messages.add("RG inv�lido!");
        }
    }

    private void validaTelefone() {
        String telefone = this.funcionario.getTelefone().replace("(", "").replace(")", "")
                .replace("-", "").replace(" ", "");
        if (telefone.isEmpty() || telefone == null) {
            this.messages.add("Telefone obrigat�rio!");
        }
        if (telefone.length() > 11) {
            this.messages.add("Telefone inv�lido!");
        }
    }

    private void validaDataNascimento() {
        if (this.funcionario.getDataNascimento() == null) {
            this.messages.add("Data de Nascimento obrigat�ria!");
        }
    }

    private void validaSalario() {
        if (this.funcionario.getSalario().equals(BigDecimal.ZERO)) {
            this.messages.add("Sal�rio inv�lido!");
        }
    }

    private void validaUsuario() {
        if (this.funcionario.getUsuario() == null) {
            this.messages.add("Usu�rio obrigat�rio!");
        }
    }

    public boolean existe(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {

            String nomeSendoCadastradoMaiusculo = this.funcionario.getNome().toUpperCase();
            String nomeSendoPercorridoMaiusculo = funcionario.getNome().toUpperCase();

            String sobrenomeSendoCadastradoMaiusculo = this.funcionario.getSobrenome()
                    .toUpperCase();
            String sobrenomeSendoPercorridoMaiusculo = funcionario.getSobrenome().toUpperCase();

            String cpfSendoCadastrado = this.funcionario.getCpf();
            String cpfSendoPercorrido = funcionario.getCpf();

            String rgSendoCadastrado = this.funcionario.getRg();
            String rgSendoPercorrido = funcionario.getRg();

            String telefoneSendoCadastrado = this.funcionario.getTelefone();
            String telefoneSendoPercorrido = funcionario.getTelefone();

            Long dataNascimentoSendoCadastrada = this.funcionario.getDataNascimento().getTime();
            Long dataNascimentoSendoPercorrida = funcionario.getDataNascimento().getTime();

            BigDecimal salarioSendoCadastrado = this.funcionario.getSalario();
            BigDecimal salarioSendoPercorrido = funcionario.getSalario();

            Usuario usuarioSendoCadastrado = this.funcionario.getUsuario();
            Usuario usuarioSendoPercorrido = funcionario.getUsuario();

            if (nomeSendoCadastradoMaiusculo.equals(nomeSendoPercorridoMaiusculo)
                    && sobrenomeSendoCadastradoMaiusculo.equals(sobrenomeSendoPercorridoMaiusculo)
                    && cpfSendoCadastrado.equals(cpfSendoPercorrido)
                    && rgSendoCadastrado.equals(rgSendoPercorrido)
                    && telefoneSendoCadastrado.equals(telefoneSendoPercorrido)
                    && dataNascimentoSendoCadastrada.equals(dataNascimentoSendoPercorrida)
                    && salarioSendoCadastrado.equals(salarioSendoPercorrido)
                    && usuarioSendoCadastrado.equals(usuarioSendoPercorrido)) {
                funcionario.setAtivo(true);
                messages.add("Funcion�rio j� cadastrado!");
                return true;
            }
        }
        return false;
    }

    public void clearMessages() {
        this.messages.clear();
    }
}
