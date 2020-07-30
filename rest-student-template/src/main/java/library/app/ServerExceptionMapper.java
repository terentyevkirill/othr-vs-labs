package library.app;

import app.OTHRestException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<Throwable> {

    // Klasse fängt alle internen Fehler, gibt sie
    // auf der Server-Konsole aus und sendet sie auch an den Client

    @Override
    public Response toResponse(Throwable t) {
        if (t instanceof LibraryRestException) {
            LibraryRestException exception = (LibraryRestException) t;
            return Response.status(exception.getHttpStatusCode())
                    .type(MediaType.TEXT_PLAIN)
                    .entity(exception.getMessage())
                    .build();
        } else {
            t.printStackTrace();
            return Response.serverError()
                    .entity(t.getMessage())
                    .build();
        }
    }
}
