package teste.mateus.controlecaixa.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import teste.mateus.controlecaixa.controller.dtos.AtualizarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.CriarCaixaDto;
import teste.mateus.controlecaixa.entity.Caixa;
import teste.mateus.controlecaixa.service.CaixaService;

import java.util.List;

@RestController
@RequestMapping("/caixa")
public class CaixaController {

  @Autowired
  private CaixaService caixaService;

  @PostMapping
  public ResponseEntity<Caixa> criarCaixa(@RequestBody CriarCaixaDto caixa) {
    Caixa novaCaixa = caixaService.criarCaixa(caixa);
    return new ResponseEntity<>(novaCaixa, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<Caixa>> buscarCaixas() {
    List<Caixa> caixas = caixaService.listarCaixas();
    return new ResponseEntity<>(caixas, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Caixa> editarCaixa(
    @PathVariable Long id,
    @RequestBody AtualizarCaixaDto caixa
  ) {
    Caixa caixaAtualizada = caixaService.editar(id, caixa);
    return ResponseEntity.ok(caixaAtualizada);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarCaixa(@PathVariable Long id) {
    caixaService.deletar(id);
    return ResponseEntity.noContent().build();
  }

}
