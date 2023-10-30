package com.impulsione.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.impulsione.POJO.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private Long id;

    private String eventName;

    private String date;

    private String category;

    private String image;

    private String instagram;

    private String texto;

    private String endereco;

    public EventoDTO(Evento evento){
        this.eventName = evento.getEventName();
        this.date = evento.getDate();
        this.category = evento.getCategory();
        this.image = evento.getImage();
        this.instagram = evento.getInstagram();
        this.texto = evento.getTexto();
        this.endereco = evento.getEndereco();
    }

    public void fromEvento(Evento evento){
        this.eventName = evento.getEventName();
        this.date = evento.getDate();
        this.category = evento.getCategory();
        this.image = evento.getImage();
        this.instagram = evento.getInstagram();
        this.texto = evento.getTexto();
        this.endereco = evento.getEndereco();
    }
}
