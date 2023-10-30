package com.impulsione.DTO;

import com.impulsione.POJO.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    @Size(min = 3, max = 10, message = "Invalid first name!(3-10 characters)")
    private String firstName;

    @Size(min = 3, max = 10, message = "Invalid last name!(3-10 characters)")
    private String lastName;

    private String username;

    @Size(min = 3, max = 10, message = "Invalid Password!(3-10 characters)")
    private String password;

    private String repeatPassword;

    private String verificationCode;

    private String telephone;

    private String address;
}

