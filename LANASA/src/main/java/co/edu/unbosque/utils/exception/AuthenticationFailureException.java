package co.edu.unbosque.utils.exception;

public class AuthenticationFailureException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public AuthenticationFailureException(String mensaje) {
        super(mensaje);
    }
}
