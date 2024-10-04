package teste.mateus.controlecaixa.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teste.mateus.controlecaixa.controller.dtos.movimentacao.CriarMovimentacaoDto;
import teste.mateus.controlecaixa.controller.dtos.movimentacao.RespostaMovimentacaoDto;
import teste.mateus.controlecaixa.controller.dtos.relatorios.RespostaBalancoDto;
import teste.mateus.controlecaixa.controller.dtos.relatorios.RespostaMovIntervaloDto;
import teste.mateus.controlecaixa.controller.response.ApiResponse;
import teste.mateus.controlecaixa.service.MovimentacaoService;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/movimentacao")
public class MovimentacaoController {

  private final MovimentacaoService movimentacaoService;

  @PostMapping
  public ResponseEntity<RespostaMovimentacaoDto> criarMovimentacao(@RequestBody CriarMovimentacaoDto criarMovimentacaoDto) {
    final var resposta= movimentacaoService.criarMovimentacao(criarMovimentacaoDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<RespostaMovimentacaoDto>>>listarMovimentacoes(Pageable pageable) {
    Page<RespostaMovimentacaoDto> movimentacoes = movimentacaoService.listarMovimentacao(pageable);
    return ResponseEntity.ok(ApiResponse.of(movimentacoes));
  }

  @GetMapping("/intervalo")
  public ResponseEntity<ApiResponse<Page<RespostaMovIntervaloDto>>> listarMovimentacoesPorIntervalo(
    @RequestParam("caixaId") Long caixaId,
    @RequestParam("dtInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtInicial,
    @RequestParam("dtFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtFinal,
    Pageable pageable
  ) {

    Page<RespostaMovIntervaloDto> movimentacoes = movimentacaoService.
      listarMovimentacoesPorIntervalo(caixaId, dtInicial, dtFinal, pageable);
    return ResponseEntity.ok(ApiResponse.of(movimentacoes));
  }

  @GetMapping("/balanco")
  public ResponseEntity<ApiResponse<RespostaBalancoDto>> calcularBalanco(
    @RequestParam("caixaId") Long caixaId,
    @RequestParam("dtInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtInicial,
    @RequestParam("dtFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtFinal
  ) {

    RespostaBalancoDto balanco = movimentacaoService.calcularBalanco(caixaId, dtInicial, dtFinal);
    return ResponseEntity.ok(ApiResponse.of(balanco));

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> excluirMovimentacao(@PathVariable Long id) {
    movimentacaoService.excluirMovimentacao(id);
    return ResponseEntity.ok(ApiResponse.empty());
  }

}

