package br.edu.ifpb.hackaton.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "resultado")
@Entity
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String participante;
    private int pontuacao;
    private LocalDateTime dataHora;

    @ManyToOne()
    @JoinColumn(name = "corrida_id")
    private Corrida corrida;
}
