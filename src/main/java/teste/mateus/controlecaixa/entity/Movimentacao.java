package teste.mateus.controlecaixa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movimentacoes")
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


}
