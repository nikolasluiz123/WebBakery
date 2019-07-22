package br.com.WebBaker.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> extends Serializable {

    void cadastrar(T model);

    List<T> listarTodos(Boolean ativo);

    T buscarPorId(Integer id);

    void atualizar(T model);
    
}
