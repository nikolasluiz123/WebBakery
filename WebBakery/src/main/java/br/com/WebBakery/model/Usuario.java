package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.WebBakery.abstractClass.AbstractBaseModel;
import br.com.WebBakery.enums.TipoUsuario;

@Entity(name = Usuario.TABLE_NAME)
public class Usuario extends AbstractBaseModel {
    
    public static final String TABLE_NAME = "usuarios";
    public static final String FK_NAME = "fk_usuario";

    private static final String TIPO_USUARIO_USUARIO = "tipo_usuario" + "_" + Usuario.TABLE_NAME;
    private static final String SENHA_USUARIO = "senha" + "_" + Usuario.TABLE_NAME;
    private static final String EMAIL_USUARIO = "email" + "_" + Usuario.TABLE_NAME;
    private static final String SOBRENOME_USUARIO = "sobrenome" + "_" + Usuario.TABLE_NAME;
    private static final String NOME_USUARIO = "nome" + "_" + Usuario.TABLE_NAME;

    @Column(length = STRING_LENGTH_32C, name = NOME_USUARIO)
    private String nome;

    @Column(length = STRING_LENGTH_32C, name = SOBRENOME_USUARIO)
    private String sobrenome;

    @Column(length = STRING_LENGTH_32C, name = EMAIL_USUARIO)
    private String email;

    @Column(name = SENHA_USUARIO)
    private Integer senha;

    @Column(name = TIPO_USUARIO_USUARIO)
    private TipoUsuario tipo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSenha() {
        return senha;
    }

    public void setSenha(Integer senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

}
