package dk.michaelbui.ezbud_server.infrastructure;

public class InfrastructureException extends RuntimeException {
    public InfrastructureException() {
    }

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
