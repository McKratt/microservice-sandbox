package net.bakaar.sandbox.shared.domain.exception;

public class TechnicalException extends RuntimeException {
    public TechnicalException(Throwable t) {
        super(t);
    }
}
