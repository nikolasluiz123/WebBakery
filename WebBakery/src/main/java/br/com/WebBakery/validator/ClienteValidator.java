package br.com.WebBakery.validator;

import java.util.Date;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.to.TOCliente;
import br.com.WebBakery.util.Cpf_Util;
import br.com.WebBakery.util.String_Util;

public class ClienteValidator extends AbstractValidator {

    private static final String FIELD_DATA_NASCIMENTO_NOT_VALID = "Data de nascimento inválida!";
    private static final String FIELD_DATA_NASCIMENTO_REQUIRED = "Data de nascimento obrigatória!";
    private static final String FIELD_CPF_NOT_VALID = "Cpf inválido!";
    private static final String FIELD_CPF_REQUIRED = "Cpf é obrigatório!";
    private static final String FIELD_TELEFONE_REQUIRED = "Telefone é obrigatório!";
    private static final String FIELD_CPF_EXIST = "Cpf já cadastrado!";

    private TOCliente cliente;
    private ClienteDao clienteDao;

    public ClienteValidator(TOCliente toCliente, ClienteDao clienteDao) {
        this.cliente = toCliente;
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
        boolean existe = this.clienteDao.cpfExiste(this.cliente, true);

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
