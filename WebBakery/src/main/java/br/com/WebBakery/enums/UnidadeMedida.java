package br.com.WebBakery.enums;

public enum UnidadeMedida {

                           KG("Quilograma"),
                           G("Grama"),
                           MG("Miligrama"),
                           L("Litro"),
                           ML("Mililitro"),
                           UN("Unidade");

    private String descricao;

    UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
