package br.com.WebBakery.enums;

public enum EnumUnidadeMedida {

                           KG("Quilograma"),
                           G("Grama"),
                           MG("Miligrama"),
                           L("Litro"),
                           ML("Mililitro"),
                           UN("Unidade");

    private String descricao;

    EnumUnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
