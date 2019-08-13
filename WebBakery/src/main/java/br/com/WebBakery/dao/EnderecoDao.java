package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Endereco;
import br.com.WebBakery.to.TOEndereco;

@Stateless
public class EnderecoDao extends AbstractBaseDao<TOEndereco> {
    
    private static final long serialVersionUID = -462049822493846497L;
    
    @Override
    public void salvar(TOEndereco to) throws Exception {
        Endereco e = new Endereco();
        getConverter().getModelFromTO(to, e);
        getEntityManager().merge(e);
    }

    @Override
    public TOEndereco buscarPorId(Integer id) throws Exception {
        Endereco e = getEntityManager().find(Endereco.class, id);
        TOEndereco to = new TOEndereco();
        getConverter().getTOFromModel(e, to);
        
        return to;
    }

    public List<TOEndereco> listarTodos(Boolean ativo) throws Exception {
        List<Endereco> enderecos = new ArrayList<>();
        List<TOEndereco> toEnderecos = new ArrayList<>();

        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT e")
        .add("FROM ".concat(Endereco.class.getName()).concat(" e "))
        .add("WHERE e.ativo = :pAtivo")
        .add("ORDER BY e.pais.nome");
        
        enderecos = getEntityManager().createQuery(sql.toString(), Endereco.class)
                                      .setParameter("pAtivo", ativo)
                                      .getResultList();
        
        for (Endereco e : enderecos) {
            TOEndereco to = new TOEndereco();
            getConverter().getTOFromModel(e, to);
            toEnderecos.add(to);
        }

        return toEnderecos;
    }

}
