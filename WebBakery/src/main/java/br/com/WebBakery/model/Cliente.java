package br.com.WebBakery.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Cliente.TABLE_NAME)
public class Cliente extends AbstractBaseModel {

    public static final String TABLE_NAME = "cliente";
    public static final String FK_NAME = "id_cliente";

    private static final String TELEFONE_CLIENTE = "telefone" + "_" + Cliente.TABLE_NAME;
    private static final String CPF_CLIENTE = "cpf" + "_" + Cliente.TABLE_NAME;
    private static final String FK_USUARIO_CLIENTE = Usuario.FK_NAME + "_" + Cliente.TABLE_NAME;
    private static final String FK_ENDERECO_CLIENTE = Endereco.FK_NAME + "_" + Cliente.TABLE_NAME;
    private static final String DATA_NASCIMENTO_CLIENTE = "data_nascimento" + "_" + Cliente.TABLE_NAME;

    @Column(length = STRING_LENGTH_32C, name = CPF_CLIENTE)
    private String cpf;

    @Column(length = STRING_LENGTH_32C, name = TELEFONE_CLIENTE)
    private String telefone;

    @Column(name = DATA_NASCIMENTO_CLIENTE)
    private Date dataNascimento;

    @OneToOne
    @JoinColumn(name = FK_ENDERECO_CLIENTE)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = FK_USUARIO_CLIENTE)
    private Usuario usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
