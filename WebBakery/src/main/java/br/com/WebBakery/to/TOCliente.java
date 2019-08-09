package br.com.WebBakery.to;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOCliente extends AbstractBaseTO {

    private String cpf;
    private String telefone;
    private Date dataNascimento;
    private String dataNascimentoFormatada;
    private TOEndereco toEndereco;
    private TOUsuario toUsuario;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataNascimentoFormatada() {
        return dataNascimentoFormatada;
    }

    public void setDataNascimentoFormatada(String dataNascimentoFormatada) {
        this.dataNascimentoFormatada = dataNascimentoFormatada;
    }

    public TOEndereco getToEndereco() {
        return toEndereco;
    }

    public void setToEndereco(TOEndereco toEndereco) {
        this.toEndereco = toEndereco;
    }

    public TOUsuario getToUsuario() {
        return toUsuario;
    }

    public void setToUsuario(TOUsuario toUsuario) {
        this.toUsuario = toUsuario;
    }

}
