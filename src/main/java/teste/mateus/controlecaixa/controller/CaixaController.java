package teste.mateus.controlecaixa.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import teste.mateus.controlecaixa.controller.dtos.caixa.AtualizarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.caixa.CriarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.caixa.RespostaCaixaDto;
import teste.mateus.controlecaixa.controller.response.ApiResponse;
import teste.mateus.controlecaixa.service.CaixaService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/caixa")
public class CaixaController {

  private final CaixaService caixaService;

  @PostMapping
  public ResponseEntity<ApiResponse<RespostaCaixaDto>> criarCaixa(@RequestBody CriarCaixaDto caixa) {
    final var resposta = this.caixaService.criarCaixa(caixa);
    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(resposta.id())
      .toUri();
    return ResponseEntity.created(location).body(ApiResponse.of(resposta));
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<List<RespostaCaixaDto>>> buscarCaixas() {
    List<RespostaCaixaDto> caixas = caixaService.listarCaixas();
    return ResponseEntity.ok(ApiResponse.of(caixas));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> editarCaixa(
    @PathVariable final Long id,
    @RequestBody final AtualizarCaixaDto caixa
  ) {
    caixaService.editar(id, caixa);
    return ResponseEntity.ok(ApiResponse.empty());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deletarCaixa(@PathVariable Long id) {
    caixaService.deletar(id);
    return ResponseEntity.ok(ApiResponse.empty());
  }

}
