package teste.mateus.controlecaixa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.mateus.controlecaixa.controller.dtos.CriarCaixaDto;
import teste.mateus.controlecaixa.controller.dtos.CriarMovimentacaoDto;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_caixas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Caixa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String descricao;

  private BigDecimal saldoInicial;

  public Caixa (CriarCaixaDto dto) {
    this.descricao = dto.descricao();
    this.saldoInicial = dto.saldoInicial();
  }

  public void adicionarValor (BigDecimal valor) {
    this.saldoInicial = this.saldoInicial.add(valor);
  }

  public void removerValor (BigDecimal valor) {
    verificaSaldoInsuficiente(valor);
    this.saldoInicial = this.saldoInicial.subtract(valor);
  }

  private void verificaSaldoInsuficiente(
    BigDecimal valor
  ) {
    if (this.saldoInicial.compareTo(valor) < 0) {
      throw new IllegalArgumentException("Saldo insuficiente no caixa para realizar a saída.");
    }
  }

}
