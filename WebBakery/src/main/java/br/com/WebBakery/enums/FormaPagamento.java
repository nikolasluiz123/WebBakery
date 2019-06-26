package br.com.WebBakery.enums;

public enum FormaPagamento {

                            DINHEIRO("Dinheiro"),
                            CARTAO_CREDITO("Cart�o de Cr�dito"),
                            CARTAO_DEBITO("Cart�o de D�bito");

    private String nome;

    private FormaPagamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
