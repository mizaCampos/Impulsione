package com.impulsione.rest;

import com.impulsione.DTO.ClienteDTO;
import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Cliente;
import com.impulsione.POJO.Evento;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/cliente")
public interface RestCliente {

    @PostMapping("/signUp")
    String signUp(@Valid @ModelAttribute("clienteDto") ClienteDTO clienteDTO,
                  BindingResult result,
                  Model model);


    @PostMapping
    ResponseEntity<Cliente> create(@Valid @RequestBody ClienteDTO clienteDTO);

    @GetMapping("/eventos")
    String eventosClientes(Model model, Principal principal);


    //metodo para alterar senha

    @PostMapping(path = "/alterar")
    public String replaceSenha(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword,
                               @RequestParam("repeatPassword") String repeatPassword, Principal principal, Model model);


    //metodo para alterar nome
    @PostMapping(path = "/alterarNome")
    public String replaceNome(@RequestParam("nome") String nome, Principal principal, Model model);

    @PostMapping(path = "/alterarLastName")
    public String replaceLastName(@RequestParam("lastName") String lastName, Principal principal, Model model);

    @PostMapping(path = "/alterarTelefone")
    public String replaceTelephone(@RequestParam("telefone") String newTelefone, Principal principal, Model model);

    @PostMapping(path = "/alterarEndereco")
    public String replaceEndereco(@RequestParam("endereco") String newEndereco, Principal principal, Model model);


    @PostMapping(path = "/alterarEmail")
    public String replaceEmail(@RequestParam("email") String newEmail, Principal principal, Model model);


    //metodo adicionado no dia 26/10
    @GetMapping(path = "/privacidade")
    public String minhaConta(Model model, Principal principal);


    //adicionar Foto
    @PostMapping("/adicionarFoto")
    public String addImage(@RequestParam("imageCliente") MultipartFile imageCliente,
                           Model model, Principal principal,
                           RedirectAttributes attributes);

    

}
