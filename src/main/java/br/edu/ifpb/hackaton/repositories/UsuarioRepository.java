package br.edu.ifpb.hackaton.repositories;

import br.edu.ifpb.hackaton.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario getByNome(String nome);
}
