package br.edu.ifpb.hackaton.services;

import br.edu.ifpb.hackaton.model.Corrida;
import br.edu.ifpb.hackaton.repositories.CorridaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorridaService {
    private final CorridaRepository corridaRepository;

    public void getCorridaById(int id) {
        corridaRepository.findById(id).get();
    }
    public List<Corrida> getCorridasAtivas() {
        return corridaRepository.findByAtivaTrue();
    }
}
