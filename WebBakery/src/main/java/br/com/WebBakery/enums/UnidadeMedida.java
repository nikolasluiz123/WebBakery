package br.com.WebBakery.enums;

public enum UnidadeMedida {

                           KG("Quilograma"),
                           G("Grama"),
                           MG("Miligrama"),
                           L("Litro"),
                           ML("Mililitro"),
                           XICARA("Xícara"),
                           COLHER_SOPA("Colher de Sopa"),
                           COLHER_CHA("Colher de Chá"),
                           COLHER_SOBREMESA("Colher de Sobremesa"),
                           PITADA("Pitada"),
                           UNIDADE("Unidade");

    private String descricao;

    UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
