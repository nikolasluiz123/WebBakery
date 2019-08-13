package br.com.WebBakery.validator;

import java.util.Date;

import javax.inject.Inject;

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

    private TOCliente cliente;
    @Inject
    private ClienteDao clienteDao;
    private EnderecoValidator enderecoValidator;
    private String senha;

    public ClienteValidator(TOCliente toCliente, String senha) {
        this.cliente = toCliente;
        this.senha = senha;
        this.enderecoValidator = new EnderecoValidator(toCliente.getToEndereco());
    }

    @Override
    public void chamarValidacoes() {
        validaDataNascimento();
        validaCpf();
        validaTelefone();
        validaUsuario();
        this.enderecoValidator.chamarValidacoes();
    }

    private void validaUsuario() {
        String email = this.cliente.getToUsuario().getEmail().trim();

        if (String_Util.isNullOrEmpty(email)) {
            messages.add(FIELD_EMAIL_REQUIRED);
        }
        if (!Email_Util.isValid(email)) {
            messages.add(FIELD_EMAIL_NOT_VALID);
        }
        if (this.senha == null || this.senha.isEmpty()) {
            messages.add(FIELD_SENHA_REQUIRED);
        }
        if (this.senha.length() > 255 || this.senha.length() < 5) {
            messages.add(FIELD_SENHA_NOT_VALID);
        }
    }

    private void validaTelefone() {
        String telefone = this.cliente.getTelefone().trim();

        if (String_Util.isNullOrEmpty(telefone)) {
            messages.add(FIELD_TELEFONE_REQUIRED);
        }
    }

    private void validaCpf() {
        String cpf = this.cliente.getCpf().trim();

        if (String_Util.isNullOrEmpty(cpf)) {
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
