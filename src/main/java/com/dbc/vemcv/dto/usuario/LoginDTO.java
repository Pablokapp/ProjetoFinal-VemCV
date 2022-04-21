package com.dbc.vemcv.dto.usuario;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    @ApiModelProperty("Login de acesso, deve ser o e-mail cadastrado")
    private String usuario;

    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Senha do usuário")
    private String senha;
}
