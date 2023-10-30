package com.impulsione.POJO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;


import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evento", uniqueConstraints = @UniqueConstraint(columnNames = {"eventName","image"}))
public class Evento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "eventName")
    private String eventName;

    @Column(name = "eventDate")
    private String date;

    @Column(name = "category")
    private String category;

    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name = "instagramBios")
    private String instagram;

    @Column(name = "description")
    private String texto;

    @Column(name = "endereco")
    private String endereco;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;
}
