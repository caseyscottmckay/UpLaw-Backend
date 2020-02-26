package us.uplaw.exception;

import org.springframework.http.HttpStatus;

public enum ServiceException implements IError {

  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Not Authorized"),
  USER_CONFLICT(HttpStatus.CONFLICT, "User already exists."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
  INVALID_INPUT_ERROR(HttpStatus.BAD_REQUEST, "Invalid input"),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal service error"),
  EXTERNAL_SERVICE_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "request timed out");

  ServiceException(final HttpStatus httpStatus, final String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  private HttpStatus httpStatus;

  private String message;

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
