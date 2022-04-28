package com.dbc.vemcv.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "ROLE")
public class RoleEntity implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
    @SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1)
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "nome_role")
    private String nome;

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
