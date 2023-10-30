package com.impulsione.RestImpl;

import com.impulsione.DTO.ClienteDTO;
import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Cliente;
import com.impulsione.POJO.Evento;
import com.impulsione.config.ClienteDetails;
import com.impulsione.repositories.ClienteRepository;
import com.impulsione.rest.RestCliente;
import com.impulsione.serviceImpl.ClienteServiceImpl;
import com.impulsione.serviceImpl.EventoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class RestClienteImpl implements RestCliente {

    @Autowired
    ClienteServiceImpl clienteService;

    @Autowired
    EventoServiceImpl eventoService;

    @Autowired
    ClienteDetails clienteDetails;

    @Override
    public ResponseEntity<Cliente> create(ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteDTO));
    }

    @Override
    public String signUp(ClienteDTO clienteDTO, BindingResult result, Model model) {
        return null;
    }

    @Override
    public String eventosClientes(Model model, Principal principal) {

        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        log.info(username);
        Long id = clienteDetails.getId(username);
        log.info(id.toString());
        List<Evento> eventoList = eventoService.findByClienteId(id);
        model.addAttribute("eventos", eventoList);
        return "eventosUser";
    }


    /////alterar senha
    public String alterarSenha(Model model) {
        model.addAttribute("title", "Alterar");
        return "alterar";
    }


    public String replaceSenha(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword,
                               @RequestParam("repeatPassword") String repeatPassword, Principal principal, Model model){

        log.info("Entrou no alterar senha");
        String username = principal.getName();
        Long getId = clienteDetails.getId(username);
        String hashedPassword = clienteService.findPasswordById(getId);

        Cliente cliente = clienteService.findByUsername(username);

        log.info(cliente.getUsername());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //verificando se a senhas se conhecidem
        boolean isMatch = passwordEncoder.matches(password, hashedPassword);
        
        if(isMatch){

            if(newPassword.equals(repeatPassword)){
                System.out.println("Senha válida. O usuário está autenticado.");
                cliente.setPassword(passwordEncoder.encode(newPassword));
                clienteService.alterarSenha(cliente);
                log.info("Senha Atualizada");
                return "login";
            }else {
                System.out.println("As senhas são diferentes");
                return "alterar";
            }
        }else {
            System.out.println("Senha inválida. A autenticação falhou.");
            return "redirect:/alterar";
        }

    }

    //fim do bloco de alterar senha

    //metodo adicionado em dia 26/10
    @Override
    public String replaceNome(String nome, Principal principal, Model model) {

        if(principal == null){
            return "redirect:/login";
        }

        log.info("Entrou no alterar nome");
        String username = principal.getName();

        Cliente cliente = clienteService.findByUsername(username);

        if(nome != null){
            cliente.setFirstName(nome);
            clienteService.salveNewName(cliente);
            return "redirect:/cliente/privacidade";
        }else {
            model.addAttribute("error", "Informe um nome valido");
            return "redirect:/cliente/privacidade";
        }

    }

    //metodo adicionado em dia 26/10


    @Override
    public String replaceLastName(String lastName, Principal principal, Model model) {

        if(principal == null){
            return "redirect:/login";
        }

        log.info("Entrou no alterar nome");
        String username = principal.getName();

        Cliente cliente = clienteService.findByUsername(username);

        if(lastName != null){
            cliente.setLastName(lastName);
            clienteService.salveNewName(cliente);
            return "redirect:/cliente/privacidade";
        }else {
            model.addAttribute("error", "Informe um nome valido");
            return "redirect:/cliente/privacidade";
        }
    }

    //metodo adicionado em 26/10

    @Override
    public String replaceTelephone(String newTelefone, Principal principal, Model model) {

        if(principal == null){
            return "redirect:/login";
        }

        log.info("Entrou no alterar nome");
        String username = principal.getName();

        Cliente cliente = clienteService.findByUsername(username);
        //aqui o metodo salveNewName foi usado para salvar tambem o telefone, tinha como adpatar =)
        if(newTelefone != null){
            cliente.setTelephone(newTelefone);
            clienteService.salveNewName(cliente);
            return "redirect:/cliente/privacidade";
        }else {
            model.addAttribute("error", "Informe um nome valido");
            return "redirect:/cliente/privacidade";
        }
    }

    //metodo adicionado em 26.10

    @Override
    public String replaceEmail(String newEmail, Principal principal, Model model) {
        if (principal == null){
            return "redirect:/login";
        }

        log.info("Entrou no alterar Email");
        String username = principal.getName();

        //verificando se tem cliente com esse email
        Cliente obj = clienteService.findByUsername(newEmail);

        if(obj != null){
            model.addAttribute("error", "Email ja registrado");
            return "redirect:/cliente/privacidade";
        }else {
            Cliente cliente = clienteService.findByUsername(principal.getName());
            cliente.setUsername(newEmail);
            clienteService.salveNewName(cliente);
            return "redirect:/cliente/privacidade";
        }

    }


    //metodo adicionado em 26/10

    @Override
    public String replaceEndereco(String newEndereco, Principal principal, Model model) {
        if(principal == null){
            return "redirect:/login";
        }

        log.info("Entrou no alterar endereço");
        String username = principal.getName();

        Cliente cliente = clienteService.findByUsername(username);
        //aqui o metodo salveNewName foi usado para salvar tambem o endereço, tinha como adpatar =)
        if(newEndereco != null){
            cliente.setAddress(newEndereco);
            clienteService.salveNewName(cliente);
            return "redirect:/cliente/privacidade";
        }else {
            model.addAttribute("error", "Informe um nome valido");
            return "redirect:/cliente/privacidade";
        }
    }

    //metodo adicionado em dia 26/10
    @Override
    public String minhaConta(Model model, Principal principal) {
        if (principal == null){
            return "redirect:/login";
        }else {
            String username = principal.getName();
            Cliente obj = clienteService.findByUsername(username);
                if(obj == null){
                    return "redirect:/login";
                }
            model.addAttribute("imagem", obj.getImage());
            model.addAttribute("firstName", obj.getFirstName());
            model.addAttribute("lastname", obj.getLastName());
            model.addAttribute("email", username);
            model.addAttribute("contac", obj.getTelephone());
            model.addAttribute("addres", obj.getAddress());
            return "gerenciamento";
        }

    }

    @Override
    public String addImage(MultipartFile imageCliente, Model model, Principal principal, RedirectAttributes attributes) {
        try {
            clienteService.addImage(imageCliente, principal);
            attributes.addFlashAttribute("success", "Imagem adicionada com Sucesso");
            return "redirect:/cliente/privacidade";
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("Error", "ocorreu um erro ao adicionar a imagem");
        }
        return "redirect:/cliente/privacidade";
    }
}



