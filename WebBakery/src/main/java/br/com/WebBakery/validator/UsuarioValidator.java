package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.Email_Util;
import br.com.WebBakery.util.Hash_Util;
import br.com.WebBakery.util.String_Util;

public class UsuarioValidator extends AbstractValidator {

    private static final String FIELD_SENHA_REQUIRED = "Senha é obrigatória!";
    private static final String FIELD_EMAIL_NOT_VALID = "E-mail inválido!";
    private static final String FIELD_EMAIL_LIMIT_EXCEDDED = "E-mail com excedência de caractéres!";
    private static final String FIELD_EMAIL_REQUIRED = "E-mail é obrigatório!";
    private static final String FIELD_EMAIL_EXIST = "E-mail já cadatrado!";
    private static final String FIELD_SENHA_LIMIT_EXCEDDED = "Senha com excedência de caractéres!";
    private static final String FIELD_SENHA_VERY_WEAK = "Senha muito fraca!";
    private static final String FIELD_NOME_REQUIRED = "Nome é obrigatório!";
    private static final String FIELD_NOME_LIMIT_EXCEDDED = "Nome com excedência de caractéres!";
    private static final String FIELD_SOBRENOME_REQUIRED = "Sobrenome é obrigatório!";
    private static final String FIELD_SOBRENOME_LIMIT_EXCEDDED = "Sobrenome com excedência de caractéres!";

    private TOUsuario toUsuario;
    private UsuarioDao usuarioDao;
    private String senha;

    public UsuarioValidator(TOUsuario toUsuario, String senha, UsuarioDao usuarioDao) {
        this.toUsuario = toUsuario;
        this.usuarioDao = usuarioDao;
        this.senha = senha;
    }

    @Override
    public void chamarValidacoes() {
        validaNome();
        validaSobrenome();
        validaEmail();
        validaSenha();
    }

    private void validaNome() {
        String nome = this.toUsuario.getNome().trim();

        if (String_Util.isNullOrEmpty(nome)) {
            this.messages.add(FIELD_NOME_REQUIRED);
        }
        if (nome.length() > 40) {
            this.messages.add(FIELD_NOME_LIMIT_EXCEDDED);
        }
    }

    private void validaSobrenome() {
        String sobrenome = this.toUsuario.getSobrenome().trim();

        if (String_Util.isNullOrEmpty(sobrenome)) {
            this.messages.add(FIELD_SOBRENOME_REQUIRED);
        }
        if (sobrenome.length() > 40) {
            this.messages.add(FIELD_SOBRENOME_LIMIT_EXCEDDED);
        }
    }

    private void validaEmail() {
        String email = this.toUsuario.getEmail().trim();

        if (String_Util.isNullOrEmpty(email)) {
            messages.add(FIELD_EMAIL_REQUIRED);
        }
        if (email.length() > 50) {
            messages.add(FIELD_EMAIL_LIMIT_EXCEDDED);
        }
        if (!Email_Util.isValid(email)) {
            messages.add(FIELD_EMAIL_NOT_VALID);
        }

        if (this.toUsuario.getId() == null) {
            boolean existe = this.usuarioDao.emailExiste(email);
            if (existe) {
                messages.add(FIELD_EMAIL_EXIST);
            }
        }
    }

    private void validaSenha() {
        if (String_Util.isNullOrEmpty(this.senha)) {
            messages.add(FIELD_SENHA_REQUIRED);
        }
        if (this.senha.length() > 80) {
            messages.add(FIELD_SENHA_LIMIT_EXCEDDED);
        }
        if (this.senha.length() < 7) {
            messages.add(FIELD_SENHA_VERY_WEAK);
        }
        Integer senhaHash = Hash_Util.getHashCode(this.senha);
        this.toUsuario.setSenha(senhaHash);
    }
}
