package com.damascena.apollovendas.domains.enums;

public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private Integer codigo;
    private String descricao;

    private Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer codigo) {

        if (codigo != null) {
            for (Perfil perfil : Perfil.values()) {
                if (codigo.equals(perfil.getCodigo())) {
                    return perfil;
                }
            }
        }

        return null;
    }
}