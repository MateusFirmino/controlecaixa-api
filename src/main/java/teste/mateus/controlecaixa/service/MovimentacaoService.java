package teste.mateus.controlecaixa.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teste.mateus.controlecaixa.controller.dtos.CriarMovimentacaoDto;
import teste.mateus.controlecaixa.controller.dtos.RespostaMovimentacaoDto;
import teste.mateus.controlecaixa.entity.Movimentacao;
import teste.mateus.controlecaixa.repository.CaixaRepository;
import teste.mateus.controlecaixa.repository.MovimentacaoRepository;

import java.util.List;
import java.util.stream.Collectors;

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

  public List<RespostaMovimentacaoDto> listarMovimentacao() {
    return movimentacaoRepository.findAll().stream()
      .map(RespostaMovimentacaoDto::toDto)
      .collect(Collectors.toList());
  }


}
