package br.com.WebBakery.validator;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBaker.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.model.Receita;

public class ProdutoValidator extends AbstractValidator {

    private Produto produto;

    public ProdutoValidator(Produto produto) {
        this.produto = produto;
    }

    @Override
    public void chamarValidacoes() {
        validaDescricao();
        validaTempoValidade();
        validaPreco();
    }

    private void validaDescricao() {
        if (this.produto.getDescricao().isEmpty() || this.produto.getDescricao() == null) {
            messages.add("Descrição é obrigatória!");
        }
        if (this.produto.getDescricao().length() > 50) {
            messages.add("Descrição com excedência de caractéres!");
        }
    }

    private void validaTempoValidade() {
        if (this.produto.getTempoValido() == null) {
            messages.add("Data de validade é obrigatória!");
        }
    }

    private void validaPreco() {
        if (this.produto.getPreco() == null || this.produto.getPreco() == BigDecimal.ZERO) {
            messages.add("Preço inválido!");
        }
    }

    public boolean existe(List<Produto> produtos) {
        for (Produto produto : produtos) {
            String descricaoSendoCadastradaMaiscula = this.produto.getDescricao().toUpperCase();
            String descricaoSendoPercorridaMaiscula = produto.getDescricao().toUpperCase();

            Long dataValidadeSendoCadastrada = this.produto.getTempoValido().getTime();
            Long dataValidadeSendoPercorrida = produto.getTempoValido().getTime();

            BigDecimal precoSendoCadastrado = this.produto.getPreco();
            BigDecimal precoSendoPercorrido = produto.getPreco();

            Receita receitaSendoCadastrada = this.produto.getReceita();
            Receita receitaSendoPercorrida = produto.getReceita();

            if (descricaoSendoCadastradaMaiscula.equals(descricaoSendoPercorridaMaiscula)
                    && dataValidadeSendoCadastrada.equals(dataValidadeSendoPercorrida)
                    && precoSendoCadastrado.equals(precoSendoPercorrido)
                    && receitaSendoCadastrada.equals(receitaSendoPercorrida)) {
                produto.setAtivo(true);
                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                     "Produto já cadastrado!",
                                                     "Produto já cadastrado!"));
                return true;
            }
        }
        return false;
    }
}
