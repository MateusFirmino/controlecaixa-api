package teste.mateus.controlecaixa.controller.dtos.relatorios;

import teste.mateus.controlecaixa.entity.Movimentacao;

import java.math.BigDecimal;

public record RespostaMovIntervaloDto(
  String descricao,
  BigDecimal valor
) {

  public static RespostaMovIntervaloDto toDto(
    Movimentacao movimentacao
  ) {
    return new RespostaMovIntervaloDto(
      movimentacao.getDescricao(),
      movimentacao.getValor()
    );
  }

}
