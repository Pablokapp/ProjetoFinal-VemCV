package com.dbc.vemcv.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "CARGO")
public class CargoEntity implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cargo")
    @SequenceGenerator(name = "seq_cargo", sequenceName = "seq_cargo", allocationSize = 1)
    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "nome_cargo")
    private String nome;

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
