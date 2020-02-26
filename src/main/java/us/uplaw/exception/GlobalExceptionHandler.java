package us.uplaw.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * the global exception handler. Catches exceptions
 * of types {@link RestException} and transforms them into a serializable
 * {@link RestExceptionResponse} for the client to consume.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Global exception handler.
   */
  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler(RestException.class)
  public ResponseEntity<RestExceptionResponse> handleRestException(
          final RestException restException) {
    RestExceptionResponse restExceptionResponse;

    restExceptionResponse = RestExceptionResponse.builder().message(restException.getMessage())
            .status(restException.getHttpStatus()).build();
    return new ResponseEntity<>(restExceptionResponse, restException.getHttpStatus());
  }

}
