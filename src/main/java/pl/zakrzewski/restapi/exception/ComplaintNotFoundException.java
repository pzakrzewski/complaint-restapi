package pl.zakrzewski.restapi.exception;

public class ComplaintNotFoundException extends RuntimeException {

    public ComplaintNotFoundException(String message) {
        super(message);
    }

}
