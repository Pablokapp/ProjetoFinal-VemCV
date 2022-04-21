package com.dbc.vemcv.enums;

public enum Cargo {
    CADASTRADOR(1);

    private final Integer idCargo;

    Cargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getCargo() {
        return idCargo;
    }
}
