package teste.mateus.controlecaixa.controller.dtos;

import java.math.BigDecimal;

public record CriarCaixaDto(String descricao, BigDecimal saldoInicial) {
}
