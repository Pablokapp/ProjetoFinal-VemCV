package com.dbc.vemcv.security;

import com.dbc.vemcv.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String CHAVE_REGRAS = "REGRAS";

    @Value("${jwt.expiration}")
    private String expiration;
    @Value("${jwt.secret}")
    private String secret;

    public String getToken(Authentication authentication){//GERA UM TOKEN A PARTIR DO USUARIO E SENHA
        UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();

        Date now = new Date();//data atual
        Date exp = new Date(now.getTime()+Long.parseLong(expiration));//data de expiracao

        List<String> regras = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setIssuer("VemCV-api")
                .setSubject(usuario.getIdUsuario().toString())
                .claim(CHAVE_REGRAS, regras)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return PREFIX + token;
    }

    public Authentication getAuthentication(HttpServletRequest request){//VERIFICA AUTENTICACAO, RETORNANDO UM "UPAT" COM NOME DE USUARIO E PERMISSOES
        String tokenBearer = request.getHeader(HEADER_AUTHORIZATION);//busca o header com "Authorization"
        if(tokenBearer!=null){
            String token = tokenBearer.replaceFirst(PREFIX, "");
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String user = body.getSubject();

            if(user!=null){
                List<String> regras = new ArrayList<>();
                for(Object o: body.get(CHAVE_REGRAS, List.class))regras.add(o.toString());
                List<SimpleGrantedAuthority> roles = regras.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(user, null, roles);
            }
        }
        return null;
    }
}

