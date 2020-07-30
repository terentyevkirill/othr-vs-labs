package library.app;

public class LibraryRestException extends RuntimeException {
    private final int httpStatusCode;

    public LibraryRestException(int httpStatusCode, String msg) {
        super(msg);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
