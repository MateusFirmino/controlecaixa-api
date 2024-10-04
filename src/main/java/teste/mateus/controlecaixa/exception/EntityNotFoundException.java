package teste.mateus.controlecaixa.exception;

import java.io.Serial;
import java.util.function.Supplier;

public class EntityNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 3754102367420708961L;

  public EntityNotFoundException() {
    super();
  }

  public EntityNotFoundException(final String message) {
    super(message);
  }

  public EntityNotFoundException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  public static Supplier<EntityNotFoundException> error(final String message) {
    return () -> new EntityNotFoundException(message);
  }

}
