package teste.mateus.controlecaixa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.mateus.controlecaixa.controller.dtos.CriarMovimentacaoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movimentacoes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movimentacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "UTC")
  private LocalDateTime data;

  @Enumerated(EnumType.STRING)
  private Tipo tipo;

  @ManyToOne
  @JoinColumn(name = "caixa_id")
  private Caixa caixa;

  private String descricao;

  @Column(nullable = false)
  private BigDecimal valor;

  public Movimentacao(CriarMovimentacaoDto movimentacaoDto) {
    this.data = LocalDateTime.now();
    this.caixa = movimentacaoDto.caixa();
    this.descricao = movimentacaoDto.descricao();
    this.valor = movimentacaoDto.valor();
    this.tipo = movimentacaoDto.tipo();
  }


}
