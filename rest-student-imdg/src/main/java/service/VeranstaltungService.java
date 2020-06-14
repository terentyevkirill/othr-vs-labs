package service;

import app.TippCollator;
import app.TippMapper;
import app.TippReducerFactory;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;
import de.othr.vs.xml.Veranstaltung;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static app.Server.hazelcast;
import static app.Server.VERANSTALTUNGEN_MAP_NAME;

@Path("studentaffairs")
public class VeranstaltungService {

    @GET
    @Path("/events/all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Veranstaltung> getAllEvents() {
        System.out.println("VeranstaltungService: getAllEventIds()");
        IMap<String, Veranstaltung> events = hazelcast.getMap(VERANSTALTUNGEN_MAP_NAME);
        return events.values();
    }

    @POST
    @Path("/events")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String addEvent(Veranstaltung v) {
        System.out.println("VeranstaltungService: addEvent()");
        // Hinzufügen der Veranstaltung in eine passende Hazelcast-In-Memory-Datenstruktur
        // Ressource-Pfad sollte sein: /restapi/events/
        // Zugriff auf Hazelcast-Node-Instanz über statisches Attribut der Klasse Server: Server.hazelcast
        IMap<String, Veranstaltung> events = hazelcast.getMap(VERANSTALTUNGEN_MAP_NAME);
        events.put(v.getId(), v);
        return v.getId();
    }

    @GET
    @Path("/events/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Veranstaltung getEvent(@PathParam("id") String id) {
        System.out.println("VeranstaltungService: getEvent()");
        // Auslesen einer Veranstaltung aus der Hazelcast-In-Memory-Datenstruktur
        // Ressource-Pfad sollte sein: /restapi/events/{veranstaltungs_id}
        // Zugriff auf Hazelcast-Node-Instanz über statisches Attribut der Klasse Server: Server.hazelcast
        IMap<String, Veranstaltung> events = hazelcast.getMap(VERANSTALTUNGEN_MAP_NAME);
        return events.get(id);
    }


    //Beispielpfad: restapi/events?search=Regensburg+Party+Studierendenheim
    @GET
    @Path("/events")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Veranstaltung> getEventsByQuery(@QueryParam("search") String tippSuchwoerter) throws ExecutionException, InterruptedException {
        System.out.println("VeranstaltungService: getEventsByQuery()");
        if (tippSuchwoerter != null && !tippSuchwoerter.trim().isEmpty()) {
            String[] suchwoerter = tippSuchwoerter.trim().split(" ");
            System.out.println("Suchwoerter: " + Arrays.toString(suchwoerter));
            // Vorbereiten der Methode und Rückgabe einer oder mehrerer Dummy-Veranstaltung(en)
            // Diese Methode wird in einer späteren Übung mit Hilfe einer Map-Reduce-Abfrage entsprechende Veranstaltungen suchen
            // Beispielhafter Ressource-Pfad: /restapi/veranstaltungen?search=Regensburg+Party+Studierendenheim
            // Zugriff auf Hazelcast-Node-Instanz über statisches Attribut der Klasse Server: Server.hazelcast
            // über Hazelcast-MapReduce-Algorithmus nach Veranstaltungen suchen,
            // die im Titel oder in der Beschreibung mind. eines der Suchwoerter enthalten
            IMap<String, Veranstaltung> map = hazelcast.getMap(VERANSTALTUNGEN_MAP_NAME);
            KeyValueSource<String, Veranstaltung> source = KeyValueSource.fromMap(map);

            JobTracker jobTracker = hazelcast.getJobTracker("default");
            Job<String, Veranstaltung> job = jobTracker.newJob(source);
            return job
                    .mapper(new TippMapper(suchwoerter))
                    .reducer(new TippReducerFactory())
                    .submit(new TippCollator())
                    .get();
        } else {
            return getAllEvents();
        }
    }
}
