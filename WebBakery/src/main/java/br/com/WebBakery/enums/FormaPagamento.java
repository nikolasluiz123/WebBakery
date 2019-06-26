package br.com.WebBakery.enums;

public enum FormaPagamento {

                            DINHEIRO("Dinheiro"),
                            CARTAO_CREDITO("Cartão de Crédito"),
                            CARTAO_DEBITO("Cartão de Débito");

    private String nome;

    private FormaPagamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
