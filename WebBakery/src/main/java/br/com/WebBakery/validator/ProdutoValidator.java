package br.com.WebBakery.validator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBaker.abstractClass.AbstractValidator;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.model.Receita;

public class ProdutoValidator extends AbstractValidator {

    private static final String ALREADY_REGISTERED = "Produto já cadastrado!";
    private static final String FIELD_PRECO_NOT_VALID = "Preço inválido!";
    private static final String FIELD_TEMPO_VALIDO_NOT_VALID = "Tempo de validade inválido!";
    private static final String FIELD_TEMPO_VALIDO_REQUIRED = "Tempo de validade é obrigatório!";
    private static final String FIELD_DESCRICAO_LIMIT_EXCEEDED = "Descrição com excedência de caractéres!";
    private static final String FIELD_DESCRICAO_REQUIRED = "Descrição é obrigatória!";
    private static final String FIELD_PRECO_REQUIRED = "Preço é obrigatório!";
    private static final String FIELD_RECEITA_REQUIRED = "Receita é obrigatória!";

    private Produto produto;

    public ProdutoValidator(Produto produto) {
        this.produto = produto;
    }

    @Override
    public void chamarValidacoes() {
        validaDescricao();
        validaTempoValidade();
        validaPreco();
        validaReceita();
    }

    private void validaDescricao() {
        String descricao = this.produto.getDescricao().trim();

        if (descricao == null || descricao.isEmpty()) {
            messages.add(FIELD_DESCRICAO_REQUIRED);
        } else if (descricao.length() > 50) {
            messages.add(FIELD_DESCRICAO_LIMIT_EXCEEDED);
        }
        this.produto.setDescricao(descricao);
    }

    private void validaTempoValidade() {
        Integer tempoValido = this.produto.getTempoValido();

        if (tempoValido == null) {
            messages.add(FIELD_TEMPO_VALIDO_REQUIRED);
        } else if (tempoValido == 0) {
            messages.add(FIELD_TEMPO_VALIDO_NOT_VALID);
        }
    }

    private void validaPreco() {
        Double preco = this.produto.getPreco();

        if (preco == null) {
            messages.add(FIELD_PRECO_REQUIRED);
        } else if (preco <= 0) {
            messages.add(FIELD_PRECO_NOT_VALID);
        }

    }

    private void validaReceita() {
        Receita receita = this.produto.getReceita();

        if (receita == null) {
            messages.add(FIELD_RECEITA_REQUIRED);
        }
    }

    public boolean existe(List<Produto> produtos) {
        for (Produto produto : produtos) {
            String descricaoSendoCadastradaMaiscula = this.produto.getDescricao().toUpperCase();
            String descricaoSendoPercorridaMaiscula = produto.getDescricao().toUpperCase();

            Integer tempoValidoSendoCadastrada = this.produto.getTempoValido();
            Integer tempoValidoSendoPercorrida = produto.getTempoValido();

            Double precoSendoCadastrado = this.produto.getPreco();
            Double precoSendoPercorrido = produto.getPreco();

            Integer receitaSendoCadastrada = this.produto.getReceita().getId();
            Integer receitaSendoPercorrida = produto.getReceita().getId();

            if (descricaoSendoCadastradaMaiscula.equals(descricaoSendoPercorridaMaiscula)
                    && tempoValidoSendoCadastrada.equals(tempoValidoSendoPercorrida)
                    && precoSendoCadastrado.equals(precoSendoPercorrido)
                    && receitaSendoCadastrada.equals(receitaSendoPercorrida)) {
                produto.setAtivo(true);
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(ALREADY_REGISTERED));
                return true;
            }
        }
        return false;
    }
}
