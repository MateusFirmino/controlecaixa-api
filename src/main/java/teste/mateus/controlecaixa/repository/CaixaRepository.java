package teste.mateus.controlecaixa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.mateus.controlecaixa.entity.Caixa;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {


}
