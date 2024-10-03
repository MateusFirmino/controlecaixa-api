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

}
