package com.dbc.vemcv.enums;

public enum Role {
    CADASTRADOR(1);

    private final Integer idRole;

    Role(Integer idRole) {
        this.idRole = idRole;
    }

    public Integer getRole() {
        return idRole;
    }
}
