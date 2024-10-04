package teste.mateus.controlecaixa.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teste.mateus.controlecaixa.controller.dtos.CriarMovimentacaoDto;
import teste.mateus.controlecaixa.controller.dtos.RespostaMovimentacaoDto;
import teste.mateus.controlecaixa.service.MovimentacaoService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movimentacao")
public class MovimentacaoController {

  private final MovimentacaoService movimentacaoService;

  @PostMapping
  public ResponseEntity<RespostaMovimentacaoDto> criarMovimentacao(@RequestBody CriarMovimentacaoDto criarMovimentacaoDto) {
    RespostaMovimentacaoDto resposta = movimentacaoService.criarMovimentacao(criarMovimentacaoDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
  }

  @GetMapping()
  public ResponseEntity<List<RespostaMovimentacaoDto>> buscarMovimentacoes() {
    List<RespostaMovimentacaoDto> movimentacoes = movimentacaoService.listarMovimentacao();
    return new ResponseEntity<>(movimentacoes, HttpStatus.OK);
  }

}

