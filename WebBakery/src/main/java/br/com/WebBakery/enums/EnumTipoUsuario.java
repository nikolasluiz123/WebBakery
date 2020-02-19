package br.com.WebBakery.enums;

public enum EnumTipoUsuario {

                         PADEIRO("Padeiro"),
                         ADMINISTRADOR_ESTOQUE("Administrador de Estoque"),
                         CAIXA("Caixa"),
                         GERENTE("Gerente"),
                         CLIENTE("Cliente");

    private String nome;

    private EnumTipoUsuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
