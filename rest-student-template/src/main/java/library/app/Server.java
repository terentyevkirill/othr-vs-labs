package library.app;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import library.service.BookService;
import library.service.ClientService;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static final String DATABASE = "jdbc:mysql://localhost:3306/vs-library";
    public static final String USER = "root";
    public static final String PASSWORD = "1234";

    public static void main(String[] args) throws IOException {
        ResourceConfig config = new ResourceConfig();
        config.register(BookService.class);
        config.register(ClientService.class);
        config.register(ServerExceptionMapper.class);

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(config, HttpHandler.class);

        server.createContext("/library/restapi", handler);
        server.start();
    }
}
