package com.impulsione.POJO;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    private String telephone;

    private String address;

    private String role;

    @OneToMany(mappedBy = "cliente")
    private List<Evento> events = new ArrayList<>();

    private String verificationCode;


    public Cliente(Long id, String firstName){
        this.id = id;
        this.firstName = firstName;
    }
}
