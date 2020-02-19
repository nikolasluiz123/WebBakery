package transferobject;

import javax.persistence.MappedSuperclass;

import transferobject.abstractclasses.AbstractBaseTO;
import transferobject.annotations.TOEntity;

@MappedSuperclass
public abstract class AbstractArquivoTO extends AbstractBaseTO {

    @TOEntity(fieldName = "bytes")
    private byte[] bytes;

    @TOEntity(fieldName = "extensao")
    private String extensao;

    @TOEntity(fieldName = "tamanho")
    private Long tamanho;

    @TOEntity(fieldName = "nome")
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
