package teste.mateus.controlecaixa.exception;

import java.io.Serial;
import java.util.function.Supplier;

public class IllegalArgumentException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -2545309060355823897L;

  public IllegalArgumentException() {
    super();
  }

  public IllegalArgumentException(final String message) {
    super(message);
  }

  public IllegalArgumentException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  public static Supplier<IllegalArgumentException> error(final String message) {
    return () -> new IllegalArgumentException(message);
  }

}
