package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.to.TOCliente;

@Stateless
public class ClienteDao extends AbstractBaseDao<TOCliente> {

    // @PersistenceContext
    // transient private EntityManager entityManager;
    //
    // @Override
    // protected EntityManager getEntityManager() {
    // return this.entityManager;
    // }

    private static final long serialVersionUID = 3335677484935538227L;

    @Override
    public void cadastrar(TOCliente to) throws Exception {
        Cliente c = new Cliente();
        getConverter().getModelFromTO(to, c);
        getEntityManager().persist(c);
    }

    @Override
    public TOCliente buscarPorId(Integer id) throws Exception {
        Cliente c = getEntityManager().find(Cliente.class, id);
        TOCliente to = new TOCliente();
        getConverter().getTOFromModel(c, to);

        return to;
    }

    @Override
    public void atualizar(TOCliente to) throws Exception {
        Cliente c = new Cliente();
        getConverter().getTOFromModel(c, to);
        getEntityManager().merge(c);
    }

    public List<TOCliente> listarTodos(Boolean ativo) throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        List<TOCliente> toClientes = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT c")
        .add("FROM ".concat(Cliente.class.getName()).concat(" c "))
        .add("WHERE c.ativo = :pAtivo")
        .add("ORDER BY c.nome, c.sobrenome");

        clientes = getEntityManager().createQuery(sql.toString(), Cliente.class)
                                     .setParameter("pAtivo", ativo)
                                     .getResultList();
        
        for (Cliente c : clientes) {
            TOCliente to = new TOCliente();
            getConverter().getTOFromModel(c, to);
            toClientes.add(to);
        }

        return toClientes;
    }

    public boolean cpfExiste(String cpf, Boolean ativo) {
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT c")
        .add("FROM ".concat(Cliente.class.getName()).concat(" c "))
        .add("WHERE")
        .add("c.ativo = :pAtivo")
        .add("AND c.cpf = :pCpf");
        
        try {
            getEntityManager().createQuery(sql.toString(), Cliente.class)
                              .setParameter("pCpf", cpf)
                              .setParameter("pAtivo", ativo).getSingleResult();

        } catch (NoResultException e) {
            return false;
        }
        return true;
    }

    public TOCliente buscarPorIdUsuario(Integer idUsuario) throws Exception {
        Cliente c = new Cliente();
        TOCliente to = new TOCliente();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT")
        .add("c.id,")
        .add("c.nome,")
        .add("c.sobrenome")
        .add("FROM ".concat(Cliente.class.getName()).concat(" c "))
        .add("WHERE")
        .add("c.ativo = :pAtivo")
        .add("AND c.usuario.id = :pIdUsuario");
        
        c = getEntityManager().createQuery(sql.toString(), Cliente.class)
                              .setParameter("pIdUsuario", idUsuario)
                              .setParameter("pAtivo", true)
                              .getSingleResult();
        
        getConverter().getTOFromModel(c, to);
        return to;
    }

}
