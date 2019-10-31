package br.com.WebBakery.model.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Estado.TABLE_NAME)
public class Estado extends AbstractBaseModel {
    
    public static final String TABLE_NAME = "estado";
    public static final String FK_NAME = "id_estado";

    private static final String FK_PAIS_ESTADO = Pais.FK_NAME + "_" + Estado.TABLE_NAME;
    private static final String SIGLA_ESTADO = "sigla" + "_" + Estado.TABLE_NAME;
    private static final String NOME_ESTADO = "nome" + "_" + Estado.TABLE_NAME;
    
    @Column(length = STRING_LENGTH_32C, name = NOME_ESTADO)
    private String nome;
    
    @Column(length = STRING_LENGTH_4C, name = SIGLA_ESTADO)
    private String sigla;
    
    @ManyToOne
    @JoinColumn(name = FK_PAIS_ESTADO)
    private Pais pais;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
