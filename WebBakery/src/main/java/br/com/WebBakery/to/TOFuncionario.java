package br.com.WebBakery.to;

import java.math.BigDecimal;
import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOFuncionario extends AbstractBaseTO {

    @TOEntity(fieldName = "salario")
    private BigDecimal salario;
    @TOEntity(fieldName = "endereco")
    private TOEndereco toEndereco;
    @TOEntity(fieldName = "usuario")
    private TOUsuario toUsuario;
    @TOEntity(fieldName = "dataNascimento")
    private Date dataNascimento;
    @TOEntity(fieldName = "cpf")
    private String cpf;
    @TOEntity(fieldName = "rg")
    private String rg;
    @TOEntity(fieldName = "telefone")
    private String telefone;

    public TOFuncionario() {
        setToEndereco(new TOEndereco());
        getToEndereco().setToPais(new TOPais());
        getToEndereco().setToEstado(new TOEstado());
        getToEndereco().setToCidade(new TOCidade());
        getToEndereco().setToLogradouro(new TOLogradouro());
        setToUsuario(new TOUsuario());
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public TOEndereco getToEndereco() {
        return toEndereco;
    }

    public void setToEndereco(TOEndereco toEndereco) {
        this.toEndereco = toEndereco;
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

}
