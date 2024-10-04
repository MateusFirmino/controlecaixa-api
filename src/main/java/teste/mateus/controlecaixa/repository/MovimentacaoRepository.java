package teste.mateus.controlecaixa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import teste.mateus.controlecaixa.entity.Movimentacao;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

  Page<Movimentacao> findByCaixaIdAndDataBetween(
    Long caixaId,
    LocalDate dtInicial,
    LocalDate dtFinal,
    Pageable pageable
  );

  List<Movimentacao> findByCaixaIdAndDataBetween(
    Long caixaId,
    LocalDate dtInicial,
    LocalDate dtFinal
  );

}

