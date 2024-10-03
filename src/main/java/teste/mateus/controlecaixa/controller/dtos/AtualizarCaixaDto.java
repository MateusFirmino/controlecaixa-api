package teste.mateus.controlecaixa.controller.dtos;

import java.math.BigDecimal;

public record AtualizarCaixaDto(String descricao, BigDecimal saldoInicial) {
}
