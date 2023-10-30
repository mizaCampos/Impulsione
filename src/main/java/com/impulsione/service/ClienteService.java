package com.impulsione.service;

import com.impulsione.DTO.ClienteDTO;
import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Cliente;
import com.impulsione.POJO.Evento;
import com.impulsione.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

@Service
public interface ClienteService {

    Cliente findById(Long id);

    Cliente save(ClienteDTO cliente);

    Cliente findByUsername(String username);
    String forgotPassword(String email, Model model);
    String findPasswordById(Long id);

    Cliente alterarSenha(Cliente cliente);

    Cliente salveNewName(Cliente cliente);

    Cliente findByVerificationCode(String code);

    Cliente addImage(MultipartFile imageCliente, Principal principal);

}
