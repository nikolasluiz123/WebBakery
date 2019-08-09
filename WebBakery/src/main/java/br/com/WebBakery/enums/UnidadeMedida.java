package br.com.WebBakery.enums;

public enum UnidadeMedida {

                           KG("Quilograma"),
                           G("Grama"),
                           MG("Miligrama"),
                           L("Litro"),
                           ML("Mililitro"),
                           XICARA("X�cara"),
                           COLHER_SOPA("Colher de Sopa"),
                           COLHER_CHA("Colher de Ch�"),
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
