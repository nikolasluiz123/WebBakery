package br.com.WebBakery.validator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBaker.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Receita;

public class ReceitaValidator extends AbstractValidator {

    private static final String ALREADY_REGISTERED = "Receita j� cadastrada!";
    private static final String FIELD_QUANTIDADE_NOT_VALID = "Quantidade inv�lida!";
    private static final String FIELD_DESCRICAO_LIMIT_EXCEEDED = "Descri��o com exced�ncia de caract�res!";
    private static final String FIELD_DESCRICAO_REQUIRES = "Descri��o � obrigat�ria!";
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
        String descricao = this.receita.getDescricao().trim().replace("\r\n", ", ");

        if (descricao == null || descricao.isEmpty()) {
            getMessages().add(FIELD_DESCRICAO_REQUIRES);
        } else if (descricao.length() > 255) {
            getMessages().add(FIELD_DESCRICAO_LIMIT_EXCEEDED);
        }

        this.receita.setDescricao(descricao);
    }

    private void validaQuantidade() {
        Integer quantidade = this.receita.getQuantidade();

        if (quantidade == null || quantidade <= 0) {
            getMessages().add(FIELD_QUANTIDADE_NOT_VALID);
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
                        .addMessage(null, new FacesMessage(ALREADY_REGISTERED));
                return true;
            }
        }
        return false;
    }
}
