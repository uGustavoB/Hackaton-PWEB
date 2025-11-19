package br.edu.ifpb.hackaton.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "pergunta")
@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String enunciado;

    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;

    private String respostaCorreta;

    @ManyToOne()
    @JoinColumn(name = "corrida_id")
    private Corrida corrida;
}
