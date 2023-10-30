package com.impulsione.service;

import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Evento;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface EventoService {

    Evento findById(Long id);

    Optional<Evento> objOptional(Long id);

    List<Evento> findAll();

    List<Evento> findAllCliente(Long id);

    Evento update(MultipartFile imageEvent, EventoDTO eventoDTO);

    EventoDTO getById(Long id);

    void deleteById(Long id);

    List<Evento>findByCategory(String category);



}
