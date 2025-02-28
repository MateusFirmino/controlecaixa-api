package teste.mateus.controlecaixa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.mateus.controlecaixa.entity.Caixa;

import java.util.Optional;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {

  Optional<Caixa> findCaixaByDescricao(String descricao);


}
