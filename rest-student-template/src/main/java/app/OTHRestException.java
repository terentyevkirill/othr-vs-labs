package app;

public class OTHRestException extends RuntimeException {

    private final int httpStatusCode;

    public OTHRestException(int httpStatusCode, String msg) {
        super(msg);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
