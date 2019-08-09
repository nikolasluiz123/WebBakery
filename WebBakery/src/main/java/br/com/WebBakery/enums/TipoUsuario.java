package br.com.WebBakery.enums;

public enum TipoUsuario {

                         PADEIRO("Padeiro"),
                         ADMINISTRADOR_ESTOQUE("Administrador de Estoque"),
                         CAIXA("Caixa"),
                         GERENTE("Gerente"),
                         CLIENTE("TOCliente");

    private String nome;

    private TipoUsuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
