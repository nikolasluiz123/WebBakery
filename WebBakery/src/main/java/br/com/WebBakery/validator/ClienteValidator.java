package br.com.WebBakery.validator;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.to.TOCliente;
import br.com.WebBakery.util.Cpf_Util;
import br.com.WebBakery.util.Email_Util;
import br.com.WebBakery.util.String_Util;

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
    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com excedência de caractéres!";
    private static final String FIELD_SOBRENOME_REQUIRED = "Sobrenome é obrigatório!";
    private static final String FIELD_SOBRENOME_LIMIT_EXCEDDED = "Sobrenome com excedência de caractéres!";

    private TOCliente cliente;
    private ClienteDao clienteDao;
    private String senha;

    public ClienteValidator(TOCliente toCliente, String senha, ClienteDao clienteDao) {
        this.cliente = toCliente;
        this.senha = senha;
        this.clienteDao = clienteDao;
    }

    @Override
    public void chamarValidacoes() {
        validaDataNascimento();
        validaCpf();
        validaTelefone();
    }

    private void validaTelefone() {
        String telefone = this.cliente.getTelefone().trim();

        if (String_Util.isNullOrEmpty(telefone)) {
            messages.add(FIELD_TELEFONE_REQUIRED);
        }
    }

    private void validaCpf() {
        String cpf = this.cliente.getCpf().trim();
        boolean existe = this.clienteDao.cpfExiste(cpf, true);

        if (String_Util.isNullOrEmpty(cpf)) {
            messages.add(FIELD_CPF_REQUIRED);
        } else if (!Cpf_Util.isValid(cpf)) {
            messages.add(FIELD_CPF_NOT_VALID);
        } else if (existe) {
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
