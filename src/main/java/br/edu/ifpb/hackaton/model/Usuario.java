package br.edu.ifpb.hackaton.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "usuario")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String nome;

    private boolean isAdmin;
}
