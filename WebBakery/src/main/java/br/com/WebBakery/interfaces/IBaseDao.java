package br.com.WebBakery.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> extends Serializable {

    void salvar(T to) throws Exception;

    List<T> listarTodos(Boolean ativo) throws Exception;

    T buscarPorId(Integer id) throws Exception;

}
