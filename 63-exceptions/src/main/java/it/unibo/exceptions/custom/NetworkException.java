package it.unibo.exceptions.custom;

public class NetworkException extends java.io.IOException {

    public NetworkException() {
        super("Network error: no response");
    }

    public NetworkException(String message) {
        super("Network error while sending message: " + message);
    }
}
