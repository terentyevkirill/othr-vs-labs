package facebook.app;

public class RestException extends RuntimeException {

    private final int httpStatusCode;

    public RestException(int httpStatusCode, String msg) {
        super(msg);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
