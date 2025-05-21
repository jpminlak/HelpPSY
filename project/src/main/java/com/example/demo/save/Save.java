package com.example.demo.save;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Save {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10)
    private String name;

    @Column(length = 20)
    private String pass;

    private LocalDateTime createDate;

}