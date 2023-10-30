package com.impulsione.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ExtractTimeExample {

    public String extrairHora(String dataEHora){

        if(dataEHora.equals("")){
            return "Horario a ser definido";
        }else {
            // Formate a data e hora com DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Parse a data e hora para LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(dataEHora, formatter);

            // Extraia apenas o horário (HH:mm)
            String horario = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            System.out.println("Horário: " + horario);
            return horario;
        }
    }

    public String extrairData(String dataHoraCompleta){

        if(dataHoraCompleta.equals("")){
            return "Data a ser definida";
        }else {

            // Formate a data e hora com DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Parse a data e hora para LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(dataHoraCompleta, formatter);

            // Extraia apenas a data (dd/MM/yyyy)
            String data = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            return data;

        }
    }

}
