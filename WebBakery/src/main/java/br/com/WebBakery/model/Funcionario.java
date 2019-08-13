package br.com.WebBakery.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Funcionario.TABLE_NAME)
public class Funcionario extends AbstractBaseModel {

    static final String TABLE_NAME = "funcionario";
    public static final String FK_NAME = "id_funcionario";

    private static final String TELEFONE_FUNCIONARIO = "telefone" + "_" + Funcionario.TABLE_NAME;
    private static final String RG_FUNCIONARIO = "rg" + "_" + Funcionario.TABLE_NAME;
    private static final String CPF_FUNCIONARIO = "cpf" + "_" + Funcionario.TABLE_NAME;
    private static final String DATA_NASCIMENTO_FUNCIONARIO = "data_nascimento" + "_" + Funcionario.TABLE_NAME;
    private static final String FK_USUARIO_FUNCIONARIO = Usuario.FK_NAME + "_" + Funcionario.TABLE_NAME;
    private static final String FK_ENDERECO_FUNCIONARIO = Endereco.FK_NAME + "_" +Funcionario.TABLE_NAME;
    private static final String SALARIO_FUNCIONARIO = "salario" + "_" + Funcionario.TABLE_NAME;

    @Column(name = SALARIO_FUNCIONARIO)
    private BigDecimal salario;

    @OneToOne
    @JoinColumn(name = FK_ENDERECO_FUNCIONARIO)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = FK_USUARIO_FUNCIONARIO)
    private Usuario usuario;

    @Column(name = DATA_NASCIMENTO_FUNCIONARIO)
    private Date dataNascimento;

    @Column(length = STRING_LENGTH_32C, name = CPF_FUNCIONARIO)
    private String cpf;

    @Column(length = STRING_LENGTH_32C, name = RG_FUNCIONARIO)
    private String rg;

    @Column(length = STRING_LENGTH_32C, name = TELEFONE_FUNCIONARIO)
    private String telefone;

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

}
