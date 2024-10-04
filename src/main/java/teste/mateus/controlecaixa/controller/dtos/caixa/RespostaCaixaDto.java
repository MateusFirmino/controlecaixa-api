package teste.mateus.controlecaixa.controller.dtos.caixa;

import teste.mateus.controlecaixa.entity.Caixa;

import java.math.BigDecimal;

public record RespostaCaixaDto(Long id,String descricao, BigDecimal saldoInicial) {

  public static RespostaCaixaDto toDto (Caixa caixa) {
    return new RespostaCaixaDto(
      caixa.getId(),
      caixa.getDescricao(),
      caixa.getSaldoInicial()
    );
  }
}
