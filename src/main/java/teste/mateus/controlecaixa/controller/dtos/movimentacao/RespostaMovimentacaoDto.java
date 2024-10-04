package teste.mateus.controlecaixa.controller.dtos.movimentacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import teste.mateus.controlecaixa.controller.dtos.caixa.RespostaCaixaDto;
import teste.mateus.controlecaixa.entity.Movimentacao;
import teste.mateus.controlecaixa.entity.Tipo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RespostaMovimentacaoDto(
  @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data, Tipo tipo, RespostaCaixaDto caixa,
  String descricao,
  BigDecimal valor
) {

  public static RespostaMovimentacaoDto toDto(
    Movimentacao movimentacao
  ) {
    return new RespostaMovimentacaoDto(
      movimentacao.getData(),
      movimentacao.getTipo(),
      RespostaCaixaDto.toDto(movimentacao.getCaixa()),
      movimentacao.getDescricao(),
      movimentacao.getValor()
    );
  }

}
