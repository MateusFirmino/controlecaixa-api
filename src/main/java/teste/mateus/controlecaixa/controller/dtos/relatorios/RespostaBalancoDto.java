package teste.mateus.controlecaixa.controller.dtos.relatorios;

import java.math.BigDecimal;

public record RespostaBalancoDto(BigDecimal entrada, BigDecimal saida, BigDecimal saldo) {


  public static RespostaBalancoDto toDto(
    BigDecimal totalEntradas,
    BigDecimal totalSaidas,
    BigDecimal saldo
  ) {
    return new RespostaBalancoDto(
      totalEntradas,
      totalSaidas,
      saldo
    );
  }

}
