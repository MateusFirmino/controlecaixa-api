package teste.mateus.controlecaixa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.mateus.controlecaixa.controller.dtos.movimentacao.CriarMovimentacaoDto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
  @Column(name = "data")
  private LocalDate data;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo")
  private Tipo tipo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "caixa_id", nullable = false)
  private Caixa caixa;

  @Column(name = "descricao",nullable = false)
  private String descricao;

  @Column(name = "valor",nullable = false)
  private BigDecimal valor;

  public Movimentacao(CriarMovimentacaoDto movimentacaoDto) {
    this.data = LocalDate.now();
    this.caixa = movimentacaoDto.caixa();
    this.descricao = movimentacaoDto.descricao();
    this.valor = movimentacaoDto.valor();
    this.tipo = movimentacaoDto.tipo();
  }



}
