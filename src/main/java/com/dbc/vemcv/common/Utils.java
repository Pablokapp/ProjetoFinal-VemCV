package com.dbc.vemcv.common;

import com.dbc.vemcv.exceptions.RegraDeNegocioException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    private static final String ZONA_LOCAL = "UTC-03:00";

    public static void validacaoDeTempo(LocalDateTime dataInicial, LocalDateTime dataFinal, Long diasDecorridos, String mensagem) throws RegraDeNegocioException {
        if(dataFinal.minusDays(diasDecorridos).plusMinutes(1).isBefore(dataInicial)){
            throw new RegraDeNegocioException(mensagem);
        }
    }

    public static LocalDateTime dateToLocalDateTime(Date data){
        return Instant.ofEpochMilli(data.getTime())
                .atZone(ZoneId.of(ZONA_LOCAL))
                .toLocalDateTime();
    }

    public static LocalDate dateToLocalDate(Date data){
        return data.toInstant()
                .atZone(ZoneId.of(ZONA_LOCAL))
                .toLocalDate();
    }

    public static Date localDateToDate(LocalDate data){
        return localDateTimeToDate(data.atStartOfDay());
    }

    public static Date localDateTimeToDate(LocalDateTime data){
        return Date.from(data.atZone(ZoneId.of(ZONA_LOCAL)).toInstant());
    }
}
