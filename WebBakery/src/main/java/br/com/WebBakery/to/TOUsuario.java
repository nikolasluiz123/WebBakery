package br.com.WebBakery.to;

import br.com.WebBakery.abstractClass.AbstractBaseTO;
import br.com.WebBakery.core.annotations.TOEntity;
import br.com.WebBakery.enums.EnumTipoUsuario;

public class TOUsuario extends AbstractBaseTO {

    @TOEntity(fieldName = "nome")
    private String nome;
    @TOEntity(fieldName = "sobrenome")
    private String sobrenome;
    @TOEntity(fieldName = "email")
    private String email;
    @TOEntity(fieldName = "senha")
    private String senha;
    @TOEntity(fieldName = "tipo")
    private EnumTipoUsuario tipo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EnumTipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoUsuario tipo) {
        this.tipo = tipo;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

}
