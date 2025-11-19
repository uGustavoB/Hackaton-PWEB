package br.edu.ifpb.hackaton.repositories;

import br.edu.ifpb.hackaton.model.Corrida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer> {
    List<Corrida> findByAtivaTrue();

    @Query("SELECT c FROM Corrida c LEFT JOIN FETCH c.perguntas WHERE c.id = :id")
    Optional<Corrida> findByIdWithPerguntas(@Param("id") Integer corridaId);
}
