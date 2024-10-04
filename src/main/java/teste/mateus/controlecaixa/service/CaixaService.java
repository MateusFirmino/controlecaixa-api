package teste.mateus.controlecaixa.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import teste.mateus.controlecaixa.controller.dtos.caixa.CriarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.caixa.AtualizarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.caixa.RespostaCaixaDto;
import teste.mateus.controlecaixa.entity.Caixa;
import teste.mateus.controlecaixa.exception.BusinessException;
import teste.mateus.controlecaixa.exception.ResourceNotFoundException;
import teste.mateus.controlecaixa.repository.CaixaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CaixaService {

  private final CaixaRepository repository;

  public CaixaService(CaixaRepository repository) {
    this.repository = repository;
  }

  public RespostaCaixaDto criarCaixa(CriarCaixaDto createDto) {
    validaDescricao(createDto);
    Caixa novoCaixa = new Caixa(createDto);
    this.repository.save(novoCaixa);
    return RespostaCaixaDto.toDto(novoCaixa);
  }


  public List<RespostaCaixaDto> listarCaixas() {
    final var listaCaixa = this.repository.findAll();
    return listaCaixa.stream().map(RespostaCaixaDto::toDto).toList();
  }

  public Caixa buscarPorId(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com id: " + id));
  }

  //  public void buscarPorDescricao(String descricao) {
  //    return repository.findByDescricao(descricao)
  //      .orElseThrow(() -> new ResourceNotFoundException("Caixa" + descricao + " não encontrada com descrição: "));
  //  }

  private void validaDescricao(CriarCaixaDto createDto) {
    if (createDto.descricao() == null || createDto.descricao().trim().isEmpty()) {
      throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
    }
    Optional<Caixa> existingCustomer = repository.findCaixaByDescricao(createDto.descricao());
    if (existingCustomer.isPresent()) {
      throw new BusinessException("Descrição já em uso");
    }
  }

  public Caixa editar(
    Long id,
    AtualizarCaixaDto atualizarCaixaDto
  ) {
    Caixa novoCaixa = this.buscarPorId(id);
    novoCaixa.setDescricao(atualizarCaixaDto.descricao());
    return this.repository.save(novoCaixa);
  }

  public void deletar(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new ResourceNotFoundException("Caixa não encontrado com id: " + id);
    }
  }

}
