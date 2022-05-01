package com.dbc.vemcv.commons;

import com.dbc.vemcv.common.Utils;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class UtilsTest {

    @Test
    public void ValidacaoTempo(){
        LocalDateTime data = LocalDateTime.of(2000,1,1,0,0);
        try {
            Utils.validacaoDeTempo(data,data.plusDays(10),10L,"");

            Exception exception = assertThrows(RegraDeNegocioException.class, ()->Utils.validacaoDeTempo(data,data.plusDays(10),11L,"teste"));

            assertTrue(exception.getMessage().contains("teste"));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateToLocalDateTime(){
        LocalDateTime localDateTime = LocalDateTime.of(2000,1,1,0,0);
        Date date = Date.from(localDateTime.atZone(ZoneId.of("UTC-03:00")).toInstant());

        assertEquals(localDateTime,Utils.dateToLocalDateTime(date));

    }

    @Test
    public void dateToLocalDate(){
        LocalDateTime localDateTime = LocalDateTime.of(2000,1,1,0,0);
        Date date = Date.from(localDateTime.atZone(ZoneId.of("UTC-03:00")).toInstant());
        LocalDate localDate = localDateTime.toLocalDate();

        assertEquals(localDate,Utils.dateToLocalDate(date));
    }

    @Test
    public void localDateToDate(){
        LocalDateTime localDateTime = LocalDateTime.of(2000,1,1,0,0);
        Date date = Date.from(localDateTime.atZone(ZoneId.of("UTC-03:00")).toInstant());
        LocalDate localDate = localDateTime.toLocalDate();

        assertEquals(date,Utils.localDateToDate(localDate));

    }

    @Test
    public void localDateTimeToDate(){
        LocalDateTime localDateTime = LocalDateTime.of(2000,1,1,0,0);
        Date date = Date.from(localDateTime.atZone(ZoneId.of("UTC-03:00")).toInstant());

        assertEquals(date,Utils.localDateTimeToDate(localDateTime));

    }
}
