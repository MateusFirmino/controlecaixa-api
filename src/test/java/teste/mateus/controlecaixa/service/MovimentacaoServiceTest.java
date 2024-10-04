package teste.mateus.controlecaixa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import teste.mateus.controlecaixa.controller.dtos.relatorios.RespostaBalancoDto;
import teste.mateus.controlecaixa.entity.Movimentacao;
import teste.mateus.controlecaixa.entity.Tipo;
import teste.mateus.controlecaixa.exception.IllegalArgumentException;
import teste.mateus.controlecaixa.repository.CaixaRepository;
import teste.mateus.controlecaixa.repository.MovimentacaoRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovimentacaoServiceTest {

  @Mock
  private MovimentacaoRepository movimentacaoRepository;

  @Mock
  private CaixaRepository caixaRepository;

  @InjectMocks
  private CaixaService caixaService;

  @InjectMocks
  private MovimentacaoService movimentacaoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Deve criar uma movimentação com sucesso")
  void criarMovimentacaoComSucesso() {
  }

  @Test
  void listarMovimentacao() {
  }

  @Test
  void listarMovimentacoesPorIntervalo() {
  }

  @Test
  @DisplayName("Deve calcular o balanço corretamente")
  void calcularBalancoComSucesso() {
    // Arrange
    Long caixaId = 1L;
    LocalDate dtInicial = LocalDate.of(2023, 10, 1);
    LocalDate dtFinal = LocalDate.of(2023, 10, 31);

    Movimentacao mov1 = new Movimentacao();
    mov1.setTipo(Tipo.ENTRADA);
    mov1.setValor(new BigDecimal("100"));

    Movimentacao mov2 = new Movimentacao();
    mov2.setTipo(Tipo.SAIDA);
    mov2.setValor(new BigDecimal("50"));

    List<Movimentacao> movimentacoes = List.of(mov1, mov2);

    when(movimentacaoRepository.findByCaixaIdAndDataBetween(caixaId, dtInicial, dtFinal))
      .thenReturn(movimentacoes);


    RespostaBalancoDto balanco = movimentacaoService.calcularBalanco(caixaId, dtInicial, dtFinal);

    assertNotNull(balanco);
    assertEquals(new BigDecimal("100"), balanco.entrada());
    assertEquals(new BigDecimal("50"), balanco.saida());
    assertEquals(new BigDecimal("50"), balanco.saldo());

    verify(movimentacaoRepository, times(1))
      .findByCaixaIdAndDataBetween(caixaId, dtInicial, dtFinal);
  }

  @Test
  @DisplayName("Deve lançar Exceção se a data inicial for maior que a data final")
  void calcularBalancoComDatasInvalidas() {
    Long caixaId = 1L;
    LocalDate dtInicial = LocalDate.of(2023, 10, 31);
    LocalDate dtFinal = LocalDate.of(2023, 10, 1);

    IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> movimentacaoService.calcularBalanco(caixaId, dtInicial, dtFinal)
    );

    assertEquals("A data inicial não pode ser posterior à data final.", exception.getMessage());

    verify(movimentacaoRepository, times(0))
      .findByCaixaIdAndDataBetween(anyLong(), any(), any());
  }

  @Test
  void excluirMovimentacao() {
  }

}
