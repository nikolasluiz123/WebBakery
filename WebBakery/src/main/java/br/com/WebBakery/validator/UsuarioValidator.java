package br.com.WebBakery.validator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.Email_Util;

public class UsuarioValidator {

    private Usuario usuario;
    private List<String> messages = new ArrayList<>();
    @Inject
    transient private FacesContext context;

    public UsuarioValidator(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean ehValido() {
        validaEmail();
        validaSenha();

        if (!messages.isEmpty()) {
            return false;
        }
        return true;
    }

    public void mostrarMensagens() {
        messages.forEach(message -> {
            context.addMessage("formCadastroUsuario:messages",
                               new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        });
    }

    private void validaEmail() {
        if (this.usuario.getEmail().isEmpty() || this.usuario.getEmail() == null) {
            messages.add("E-mail é obrigatório!");
        }
        if (this.usuario.getEmail().length() > 80
                || !Email_Util.EhValido(this.usuario.getEmail())) {
            messages.add("E-mail inválido!");
        }
    }

    private void validaSenha() {
        if (this.usuario.getSenha().isEmpty() || this.usuario.getSenha() == null) {
            messages.add("Senha é obrigatória!");
        }
        if (this.usuario.getSenha().length() > 255 || this.usuario.getSenha().length() < 5) {
            messages.add("Senha inválida!");
        }
    }

    public boolean existe(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            String emailSendoCadastradoMaiusculo = this.usuario.getEmail().toUpperCase();
            String emailSendoPercorridoMaiusculo = usuario.getEmail().toUpperCase();

            if (emailSendoCadastradoMaiusculo.equals(emailSendoPercorridoMaiusculo)) {
                usuario.setAtivo(true);
                messages.add("Usuário já cadastrado!");
                return true;
            }
        }
        return false;
    }

    public void clearMessages() {
        this.messages.clear();
    }
}
