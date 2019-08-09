package br.com.WebBakery.abstractClass;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractArquivoTO extends AbstractBaseTO {

    private byte[] bytes;

    private String extensao;

    private Long tamanho;

    private String nome;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
