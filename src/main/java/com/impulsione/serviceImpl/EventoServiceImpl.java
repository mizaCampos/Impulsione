package com.impulsione.serviceImpl;

import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Cliente;
import com.impulsione.POJO.Evento;
import com.impulsione.config.ClienteDetails;
import com.impulsione.repositories.EventoRepository;
import com.impulsione.service.EventoService;
import com.impulsione.utils.ImageUpload;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventoServiceImpl implements EventoService {

    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    ClienteServiceImpl clienteService;

    @Autowired
    ImageUpload imageUpload;
    @Autowired
    ClienteDetails clienteDetails;


    @Override
    public Evento findById(Long id) {
        Optional<Evento> obj = eventoRepository.findById(id);
        return obj.orElseThrow();
    }

    @Override
    public Optional<Evento> objOptional(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    //pegar o usuario logado, verificar a autenticacao e pegar a lista de eventos dele// implementar isso
    @Override
    public List<Evento> findAllCliente(Long id) {
        Cliente cliente = clienteService.findById(id);
            List<Evento> eventos = cliente.getEvents();
            return eventos;
    }


    public Evento createEventoWithAuten(MultipartFile imageEvent, Long getId, Evento evento) {
        log.info("Entrou no createEventoWIth");
        try {
            if(imageEvent == null){
                evento.setImage(null);
            }else{
                if(imageUpload.uploadImage(imageEvent)){
                    System.out.println("Upload successfully");
                }
                evento.setImage(Base64.getEncoder().encodeToString(imageEvent.getBytes()));
            }
            Cliente cliente = clienteService.findById(getId);
            evento.setCliente(cliente);
            return eventoRepository.save(evento);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public List<Evento> findByClienteId(Long clienteId) {
        return eventoRepository.findByClienteId(clienteId);
    }

    @Override
    public Evento update(MultipartFile imageEvent,EventoDTO eventoDTO) {
        try {
            Evento evento = findById(eventoDTO.getId());
            if(imageEvent == null){
                evento.setImage(evento.getImage());
            }else {
                if(imageUpload.checkExisted(imageEvent) == false){
                    System.out.println("Upload to folder");
                       imageUpload.uploadImage(imageEvent);
                }
                evento.setImage(Base64.getEncoder().encodeToString(imageEvent.getBytes()));
            }
            evento.setEventName(eventoDTO.getEventName());
            evento.setDate(eventoDTO.getDate());
            evento.setCategory(eventoDTO.getCategory());
            evento.setInstagram(eventoDTO.getInstagram());
            evento.setTexto(eventoDTO.getTexto());
            evento.setEndereco(evento.getEndereco());
            return eventoRepository.save(evento);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EventoDTO getById(Long id) {
        Evento evento = eventoRepository.getById(id);
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(evento.getId());
        eventoDTO.setEventName(evento.getEventName());
        eventoDTO.setDate(evento.getDate());
        eventoDTO.setCategory(evento.getCategory());
        eventoDTO.setImage(evento.getImage());
        eventoDTO.setInstagram(evento.getInstagram());
        eventoDTO.setTexto(evento.getTexto());
        return eventoDTO;
    }


    @Override
    public void deleteById(Long id) {
        try {
            eventoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Evento> findByCategory(String category) {

        return eventoRepository.findByCategory(category);
    }
}




