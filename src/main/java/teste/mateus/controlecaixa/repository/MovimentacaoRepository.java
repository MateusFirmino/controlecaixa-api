package teste.mateus.controlecaixa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.mateus.controlecaixa.entity.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

}
