package teste.mateus.controlecaixa.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teste.mateus.controlecaixa.controller.dtos.movimentacao.CriarMovimentacaoDto;
import teste.mateus.controlecaixa.controller.dtos.movimentacao.RespostaMovimentacaoDto;
import teste.mateus.controlecaixa.controller.dtos.relatorios.RespostaBalancoDto;
import teste.mateus.controlecaixa.controller.dtos.relatorios.RespostaMovIntervaloDto;
import teste.mateus.controlecaixa.entity.Movimentacao;
import teste.mateus.controlecaixa.exception.IllegalArgumentException;
import teste.mateus.controlecaixa.repository.CaixaRepository;
import teste.mateus.controlecaixa.repository.MovimentacaoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MovimentacaoService {

  private final MovimentacaoRepository movimentacaoRepository;

  private final CaixaRepository caixaRepository;

  private final CaixaService caixaService;

  @Transactional
  public RespostaMovimentacaoDto criarMovimentacao(CriarMovimentacaoDto criarMovimentacaoDto) {
    Movimentacao novoMovimentacao = new Movimentacao(criarMovimentacaoDto);
    var validarCaixa = caixaService.buscarPorId(criarMovimentacaoDto.caixa().getId());

    switch (criarMovimentacaoDto.tipo()) {
      case SAIDA -> validarCaixa.removerValor(criarMovimentacaoDto.valor());
      case ENTRADA -> validarCaixa.adicionarValor(criarMovimentacaoDto.valor());
      default -> throw new IllegalArgumentException("Tipo de movimentação inválido.");
    }
    caixaRepository.save(validarCaixa);

    Movimentacao movimentacaoSalva = movimentacaoRepository.save(novoMovimentacao);
    movimentacaoSalva.setCaixa(validarCaixa);

    return RespostaMovimentacaoDto.toDto(movimentacaoSalva);
  }

  public Page<RespostaMovimentacaoDto> listarMovimentacao(Pageable pageable) {
    Page<Movimentacao> movimentacoesPaginadas = movimentacaoRepository.findAll(pageable);
    return movimentacoesPaginadas.map(RespostaMovimentacaoDto::toDto);
  }

  public Page<RespostaMovIntervaloDto> listarMovimentacoesPorIntervalo(
    Long caixaId,
    LocalDate dtInicial,
    LocalDate dtFinal,
    Pageable pageable) {
    Page<Movimentacao> movimentacoesPaginadas = movimentacaoRepository.findByCaixaIdAndDataBetween(caixaId, dtInicial, dtFinal, pageable);
    return movimentacoesPaginadas.map(RespostaMovIntervaloDto::toDto);
  }

  // TODO: reaproveitando a mesma função para o endpoint de balanço,
  //  balanço e (entradas e saídas) fazem o mesmo calculo só muda o intervalo de datas/ano
  public RespostaBalancoDto calcularBalanco(
    Long caixaId,
    LocalDate dtInicial,
    LocalDate dtFinal
  ) {
    List<Movimentacao> movimentacoes = movimentacaoRepository.findByCaixaIdAndDataBetween(caixaId, dtInicial, dtFinal);

    BigDecimal totalEntradas = BigDecimal.ZERO;
    BigDecimal totalSaidas = BigDecimal.ZERO;

    for (Movimentacao mov : movimentacoes) {
      switch (mov.getTipo()) {
        case ENTRADA -> totalEntradas = totalEntradas.add(mov.getValor());
        case SAIDA -> totalSaidas = totalSaidas.add(mov.getValor());
      }
    }
    final var saldo = totalEntradas.subtract(totalSaidas);
    return  RespostaBalancoDto.toDto(totalEntradas,totalSaidas,saldo);
  }

  @Transactional
  public void excluirMovimentacao(Long id) {
    Movimentacao movimentacao = movimentacaoRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Movimentação com id " + id + " não encontrada."));
    movimentacaoRepository.delete(movimentacao);
  }

}
