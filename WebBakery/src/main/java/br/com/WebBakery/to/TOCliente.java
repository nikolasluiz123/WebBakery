package br.com.WebBakery.to;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;

public class TOCliente extends AbstractBaseTO {

    @TOEntity(fieldName = "cpf")
    private String cpf;
    @TOEntity(fieldName = "telefone")
    private String telefone;
    @TOEntity(fieldName = "dataNascimento")
    private Date dataNascimento;
    @TOEntity(fieldName = "endereco")
    private TOEndereco toEndereco;
    @TOEntity(fieldName = "usuario")
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
