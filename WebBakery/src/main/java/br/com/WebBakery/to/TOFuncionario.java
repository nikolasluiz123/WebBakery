package br.com.WebBakery.to;

import java.math.BigDecimal;
import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;

public class TOFuncionario extends AbstractBaseTO {

    private BigDecimal salario;
    private TOEndereco endereco;
    private TOUsuario toUsuario;
    private Date dataNascimento;
    private String dataNascimentoFormatada;
    private String cpf;
    private String rg;
    private String telefone;

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public TOEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(TOEndereco endereco) {
        this.endereco = endereco;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TOUsuario getToUsuario() {
        return toUsuario;
    }

    public void setToUsuario(TOUsuario toUsuario) {
        this.toUsuario = toUsuario;
    }

    public String getDataNascimentoFormatada() {
        return dataNascimentoFormatada;
    }

    public void setDataNascimentoFormatada(String dataNascimentoFormatada) {
        this.dataNascimentoFormatada = dataNascimentoFormatada;
    }

}
