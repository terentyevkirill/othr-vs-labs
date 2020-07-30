package library.service;

import library.app.LibraryRestException;
import library.entity.Client;

import javax.ws.rs.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("clients")
public class ClientService {
    static final Map<Long, Client> clients = new HashMap<>();

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    public Collection<Client> getAllClients() {
        return clients.values();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Client getClientById(@PathParam("id") long id) {
        if (clients.containsKey(id)) {
            return clients.get(id);
        } else {
            throw new LibraryRestException(404, "Nothing found for client ID " + id);
        }
    }

    @POST
    @Path("")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Client addClient(Client client) {
        if (!clients.containsKey(client.getClientId())) {
            clients.put(client.getClientId(), client);
            return client;
        } else {
            throw new LibraryRestException(409, "Client with ID " + client.getClientId() + " already exists");
        }
    }

    protected static Map<Long, Client> getClients() {
        return clients;
    }
}
