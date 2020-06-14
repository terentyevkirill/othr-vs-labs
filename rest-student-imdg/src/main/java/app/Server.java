package app;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import service.PruefungsleistungService;
import service.StudentService;
import service.VeranstaltungService;

import javax.swing.*;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class Server {

    public static final String DB_CONNECTION = "jdbc:mysql://im-vm-011/vs-08";
    public static final String DB_USERNAME = "vs-08";
    public static final String DB_PASSWORD = "vs-08-pw";
    public static final String VERANSTALTUNGEN_MAP_NAME = "veranstaltungen";
    public static final String STUDENTS_MAP_NAME = "students";

    public static HazelcastInstance hazelcast;

    public static void main(String[] args) throws IOException {
        // I N - M E M O R Y - D A T A - G R I D
        // Create hazelcast instance (make this process join the hazelcast data grid as a node)
        Config hazelcastConfig = new Config();
        NetworkConfig networkConfig = hazelcastConfig.getNetworkConfig();
        networkConfig.setPortAutoIncrement(true);
        networkConfig.getInterfaces().setEnabled(true); // and set to "true"
        // For OTH Cip-Pools please set to 172.*.*.* ...
        // For your home network set to 192.168.*.* or 10.*.*.* or whatever is your local IP range
        networkConfig.getInterfaces().setInterfaces(Arrays.asList("192.*.*.*"));
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(true);
        joinConfig.getMulticastConfig().setMulticastGroup("224.2.2.3");
        joinConfig.getMulticastConfig().setMulticastPort(54327);
        // Create new hazelcast node
        hazelcast = Hazelcast.newHazelcastInstance(hazelcastConfig);

        // W E B S E R V E R   ( R E S T f u l   R E S O U R C E N )
        // Create configuration object for webserver instance
        ResourceConfig restWebserverConfig = new ResourceConfig();
        // Register REST-resources (i.e. service classes) with the webserver
        restWebserverConfig.register(ServerExceptionMapper.class);
        restWebserverConfig.register(StudentService.class);
        restWebserverConfig.register(PruefungsleistungService.class);
        restWebserverConfig.register(VeranstaltungService.class);

        // W E B S E R V E R   ( P O R T  +  C O N T E X T - P F A D )
        // Create webserver instance and start it
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(restWebserverConfig, HttpHandler.class);
        // Context is part of the URI directly after  http://domain.tld:port/
        server.createContext("/restapi", handler);
        server.start();


        // Show dialogue in order to prevent premature ending of server(s)
        JOptionPane.showMessageDialog(null, "Stop server...");
        server.stop(0);
        hazelcast.shutdown();

    }
}
