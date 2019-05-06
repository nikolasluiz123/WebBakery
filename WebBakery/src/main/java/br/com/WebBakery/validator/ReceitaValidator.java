package br.com.WebBakery.validator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBaker.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Receita;

public class ReceitaValidator extends AbstractValidator {

    private Receita receita;

    public ReceitaValidator(Receita receita) {
        this.receita = receita;
    }

    @Override
    public void chamarValidacoes() {
        validaDescricao();
        validaQuantidade();
    }

    private void validaDescricao() {
        String descricao = this.receita.getDescricao().trim();

        if (descricao == null || descricao.isEmpty()) {
            getMessages().add("Descri��o � obrigat�ria!");
        } else if (descricao.length() > 255) {
            getMessages().add("Descri��o com exced�ncia de caract�res!");
        }
    }

    private void validaQuantidade() {
        Integer quantidade = this.receita.getQuantidade();

        if (quantidade == null || quantidade <= 0) {
            getMessages().add("Quantidade inv�lida!");
        }
    }

    public boolean existe(List<Receita> receitas) {
        for (Receita receita : receitas) {
            String descricaoSendoCadastradoMaisculo = this.receita.getDescricao().toUpperCase()
                    .trim();
            String descricaoSendoPercorridaMaisculo = receita.getDescricao().toUpperCase().trim();

            Integer quantidadeCadastradaMaisculo = this.receita.getQuantidade();
            Integer quantidadePercorridoMaisculo = receita.getQuantidade();

            if (descricaoSendoCadastradoMaisculo.equals(descricaoSendoPercorridaMaisculo)
                    && quantidadeCadastradaMaisculo.equals(quantidadePercorridoMaisculo)) {
                receita.setAtivo(true);
                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                     "Receita j� cadastrada!",
                                                     "Receita j� cadastrada!"));
                return true;
            }
        }
        return false;
    }
}
