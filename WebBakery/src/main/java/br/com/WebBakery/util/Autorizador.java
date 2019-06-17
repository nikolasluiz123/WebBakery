package br.com.WebBakery.util;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.WebBakery.model.Usuario;

public class Autorizador implements PhaseListener {

    private static final long serialVersionUID = -4494952210396066948L;

    @Override
    public void afterPhase(PhaseEvent evento) {

        FacesContext context = evento.getFacesContext();
        String nomePagina = context.getViewRoot().getViewId();

        if ("/login.xhtml".equals(nomePagina)) {
            return;
        }

        Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap()
                .get("usuarioLogado");

        if (usuarioLogado != null) {
            return;
        }

        // redirecionamento para login.xhtml

        try {
            context.getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
