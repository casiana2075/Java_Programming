package org.example;

public class InvalidCatalogException extends RuntimeException {
    public InvalidCatalogException(RuntimeException ex) {
        super("Invalid catalog.", ex);
    }

}
