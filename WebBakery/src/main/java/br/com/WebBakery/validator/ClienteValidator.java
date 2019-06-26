package br.com.WebBakery.validator;

import java.util.Date;

import javax.persistence.EntityManager;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.util.Cpf_Util;
import br.com.WebBakery.util.Email_Util;

public class ClienteValidator extends AbstractValidator {

    private static final String FIELD_DATA_NASCIMENTO_NOT_VALID = "Data de nascimento inválida!";
    private static final String FIELD_DATA_NASCIMENTO_REQUIRED = "Data de nascimento obrigatória!";
    private static final String FIELD_CPF_NOT_VALID = "Cpf inválido!";
    private static final String FIELD_CPF_REQUIRED = "Cpf é obrigatório!";
    private static final String FIELD_TELEFONE_REQUIRED = "Telefone é obrigatório!";
    private static final String FIELD_SENHA_NOT_VALID = "Senha inválida!";
    private static final String FIELD_SENHA_REQUIRED = "Senha é obrigatória!";
    private static final String FIELD_EMAIL_NOT_VALID = "E-mail inválido!";
    private static final String FIELD_EMAIL_REQUIRED = "E-mail é obrigatório!";
    private static final String FIELD_CPF_EXIST = "Cpf já cadastrado!";

    private Cliente cliente;
    private ClienteDao clienteDao;

    public ClienteValidator(Cliente cliente, EntityManager em) {
        this.cliente = cliente;
        this.clienteDao = new ClienteDao(em);
    }

    @Override
    public void chamarValidacoes() {
        validaDataNascimento();
        validaCpf();
        validaTelefone();
        validaUsuario();
    }

    private void validaUsuario() {
        String email = this.cliente.getUsuario().getEmail().trim();
        String senha = this.cliente.getUsuario().getSenha().trim();

        if (email == null || email.isEmpty()) {
            messages.add(FIELD_EMAIL_REQUIRED);
        }
        if (!Email_Util.isValid(email)) {
            messages.add(FIELD_EMAIL_NOT_VALID);
        }
        if (senha == null || senha.isEmpty()) {
            messages.add(FIELD_SENHA_REQUIRED);
        }
        if (senha.length() > 255 || senha.length() < 5) {
            messages.add(FIELD_SENHA_NOT_VALID);
        }
    }

    private void validaTelefone() {
        String telefone = this.cliente.getTelefone().trim();

        if (telefone.isEmpty() || telefone == null) {
            messages.add(FIELD_TELEFONE_REQUIRED);
        }
    }

    private void validaCpf() {
        String cpf = this.cliente.getCpf().trim();

        if (cpf.isEmpty() || cpf == null) {
            messages.add(FIELD_CPF_REQUIRED);
        }
        if (!Cpf_Util.isValid(cpf)) {
            messages.add(FIELD_CPF_NOT_VALID);
        }
        boolean existe = this.clienteDao.cpfExiste(cpf, true);
        if (existe) {
            messages.add(FIELD_CPF_EXIST);
        }
    }

    private void validaDataNascimento() {
        Date dataNascimento = this.cliente.getDataNascimento();

        if (dataNascimento == null) {
            messages.add(FIELD_DATA_NASCIMENTO_REQUIRED);
        } else if (dataNascimento.after(new Date())) {
            messages.add(FIELD_DATA_NASCIMENTO_NOT_VALID);
        }
    }
}
