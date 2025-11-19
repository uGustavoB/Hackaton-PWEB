package br.edu.ifpb.hackaton.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "corrida")
@Entity
public class Corrida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;
    private String descricao;
    private int tempoSegundos;
    private boolean ativa;

    @OneToMany(mappedBy = "corrida", cascade = CascadeType.ALL)
    private List<Pergunta> perguntas;
}
