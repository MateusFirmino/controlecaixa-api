package teste.mateus.controlecaixa.controller.dtos.movimentacao;

import teste.mateus.controlecaixa.entity.Caixa;
import teste.mateus.controlecaixa.entity.Tipo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarMovimentacaoDto(LocalDate data, Tipo tipo, Caixa caixa, String descricao, BigDecimal valor ) {
}
