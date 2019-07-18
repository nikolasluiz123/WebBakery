package br.com.WebBakery.validator;

import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Foto;

public class FotoValidator extends AbstractValidator {

    private static final String FIELD_SIZE_LIMIT_EXCEDDED = "Tamanho m�ximo da foto ultrapassado! O tamanho m�ximo da foto � 3MB.";

    private Foto foto;

    public FotoValidator(Foto foto) {
        this.foto = foto;
    }

    @Override
    public void chamarValidacoes() {
        validaTamanho();
    }

    private void validaTamanho() {
        Long tamanho = this.foto.getTamanho();
        if (tamanho <= 20480) {
            messages.add(FIELD_SIZE_LIMIT_EXCEDDED);
        }
    }
}
