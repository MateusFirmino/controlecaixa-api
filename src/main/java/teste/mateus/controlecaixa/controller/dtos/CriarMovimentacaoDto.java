package teste.mateus.controlecaixa.controller.dtos;

import teste.mateus.controlecaixa.entity.Caixa;
import teste.mateus.controlecaixa.entity.Tipo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CriarMovimentacaoDto(LocalDateTime data, Tipo tipo, Caixa caixa, String descricao, BigDecimal valor ) {
}
