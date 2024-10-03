package teste.mateus.controlecaixa.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import teste.mateus.controlecaixa.controller.dtos.CriarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.AtualizarCaixaDto;
import teste.mateus.controlecaixa.entity.Caixa;
import teste.mateus.controlecaixa.repository.CaixaRepository;

import java.util.List;

@Service
public class CaixaService {

  private final CaixaRepository repository;

  public CaixaService(CaixaRepository repository) {
    this.repository = repository;
  }

  public Caixa criarCaixa(CriarCaixaDto createDto) {
    Caixa novoCaixa = new Caixa(createDto);
    this.repository.save(novoCaixa);
    return novoCaixa;
  }

  public List<Caixa> listarCaixas() {
    return this.repository.findAll();
  }

  public Caixa editar(Long id, AtualizarCaixaDto atualizarCaixaDto) {
    return repository.findById(id).map(caixa -> {
      caixa.setDescricao(atualizarCaixaDto.descricao());
      caixa.setSaldoInicial(atualizarCaixaDto.saldoInicial());
      return repository.save(caixa);
    }).orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com id: " + id));
  }

  public void deletar(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new EntityNotFoundException("Caixa não encontrada com id: " + id);
    }
  }

}
