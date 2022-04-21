package com.dbc.vemcv.dto.usuario;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @ApiModelProperty("Identificador do usuario")
    private Integer idUsuario;

    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Nome completo do usuario")
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    @ApiModelProperty("E-mail do usuário")
    private String email;
}
