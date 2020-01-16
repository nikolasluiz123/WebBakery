package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.to.TOFotoPerfil;

public class FotoValidator extends AbstractValidator {

    private static final String FIELD_FOTO_REQUIRED = "Foto � obrigat�rio.";

    private static final int MAX_SIZE_FOR_IMG = 20480;

    private static final String FIELD_SIZE_LIMIT_EXCEDDED = "Tamanho m�ximo da foto ultrapassado! O tamanho m�ximo da toFoto � 3MB.";

    private TOFotoPerfil toFoto;

    public FotoValidator(TOFotoPerfil toFoto) {
        this.toFoto = toFoto;
    }

    @Override
    public void chamarValidacoes() {
        validarFoto();
    }

    private void validarFoto() {
        if (this.toFoto == null) {
            messages.add(FIELD_FOTO_REQUIRED);
        } else if (toFoto.getTamanho() >= MAX_SIZE_FOR_IMG) {
            messages.add(FIELD_SIZE_LIMIT_EXCEDDED);
        }
    }
}
