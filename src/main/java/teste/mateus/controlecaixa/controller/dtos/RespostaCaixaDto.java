package teste.mateus.controlecaixa.controller.dtos;

import teste.mateus.controlecaixa.entity.Caixa;

import java.math.BigDecimal;

public record RespostaCaixaDto(String descricao, BigDecimal saldoInicial) {

  public static RespostaCaixaDto toDto (Caixa caixa) {
    return new RespostaCaixaDto(
      caixa.getDescricao(),
      caixa.getSaldoInicial()
    );
  }
}
