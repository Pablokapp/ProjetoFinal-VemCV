package com.dbc.vemcv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Nome completo do usuario")
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email(message = "E-mail inválido")
    @ApiModelProperty("E-mail do usuário")
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 32, message = "A senha deve conter no mínimo 8 e no máximo 32 caracteres")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "A senha deve conter ao menos um número"),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "A senha deve conter ao menos uma letra minuscula"),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "A senha deve conter ao menos uma letra maiuscula"),
            @Pattern(regexp = "(?=.*[!@#$%^&*+=?]).+", message ="A senha deve conter ao menos um caracter especial (!@#$%^&*+=?)"),
            @Pattern(regexp = "(?=\\S+$).+", message = "A senha não pode conter espaços em branco")
    })
    @ApiModelProperty("Senha do usuário, deve conter no mínimo oito caracteres, pelo menos uma letra maiúscula, " +
            "uma letra minúscula, um número e um caractere especial")
    private String senha;
}