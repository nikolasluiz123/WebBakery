package br.com.WebBakery.interfaces;

public interface IBaseListMBean<T> extends IBaseMBean {

    public void inativar(T to);

    public void carregar(Integer id) throws Exception;
    
}
