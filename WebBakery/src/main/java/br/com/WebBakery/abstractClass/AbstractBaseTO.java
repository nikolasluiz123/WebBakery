package br.com.WebBakery.abstractClass;

import br.com.WebBakery.core.annotations.TOEntity;

public class AbstractBaseTO {

    @TOEntity(fieldName = "id")
    private Integer id;

    @TOEntity(fieldName = "ativo")
    private boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    @Override 
    public int hashCode() { 
        final int prime = 31; 
        int result = 1; 
        result = prime * result + ((id == null) ? 0 : id.hashCode()); 
        return result; 
    } 
 
    @Override 
    public boolean equals(Object obj) { 
        if (this == obj) 
            return true; 
        if (obj == null) 
            return false; 
        if (getClass() != obj.getClass()) 
            return false; 
 
        return (obj instanceof AbstractBaseTO) ? (this.getId() == null ? this == obj 
                : this.getId().equals(((AbstractBaseTO) obj).getId())) 
                : false; 
    } 

}
