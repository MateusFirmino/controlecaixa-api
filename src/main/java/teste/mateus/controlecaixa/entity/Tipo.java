package teste.mateus.controlecaixa.entity;

public enum Tipo {
  ENTRADA("E"), SAIDA("S");

  private final String valor;

  Tipo(String valor) {
    this.valor = valor;
  }

}
