package com.dbc.vemcv.model;

import com.dbc.vemcv.enums.ServerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "SERVER_PROPERTIES")
@AllArgsConstructor
@NoArgsConstructor
public class ServerProperties {
    @Id
    @Column(name = "id")
    Integer id;

    @Column(name = "ultima_atualizacao")
    LocalDateTime ultimaAtualizacao;

    @Column(name = "status")
    ServerStatus status;

}
