package service;

import app.Server;
import de.othr.vs.xml.Veranstaltung;

import javax.ws.rs.*;
import java.util.List;

@Path("studentaffairs")
public class VeranstaltungService {

    @POST
    @Path("/events")
    public String addEvent(Veranstaltung v) {

        // TODO: Hinzufügen der Veranstaltung in eine passende Hazelcast-In-Memory-Datenstruktur
        // Ressource-Pfad sollte sein: /restapi/events/
        // Zugriff auf Hazelcast-Node-Instanz über statisches Attribut der Klasse Server: Server.hazelcast

        throw new IllegalStateException("Methode addEvent muss erst noch implementiert werden");

    }

    @GET
    @Path("/events/{id}")
    public Veranstaltung getEvent(@PathParam("id") String id) {

        // TODO: Auslesen einer Veranstaltung aus der Hazelcast-In-Memory-Datenstruktur
        // Ressource-Pfad sollte sein: /restapi/events/{veranstaltungs_id}
        // Zugriff auf Hazelcast-Node-Instanz über statisches Attribut der Klasse Server: Server.hazelcast

        throw new IllegalStateException("Methode getEvent muss erst noch implementiert werden");

    }


    //Beispielpfad: restapi/events?search=Regensburg+Party+Studierendenheim
    @GET
    @Path("/events")
    public List<Veranstaltung> getEventsByQuery(@QueryParam("search") String tippSuchwoerter) {

        // TODO: Vorbereiten der Methode und Rückgabe einer oder mehrerer Dummy-Veranstaltung(en)
        // Diese Methode wird in einer späteren Übung mit Hilfe einer Map-Reduce-Abfrage entsprechende Veranstaltungen suchen
        // Beispielhafter Ressource-Pfad: /restapi/veranstaltungen?search=Regensburg+Party+Studierendenheim
        // Zugriff auf Hazelcast-Node-Instanz über statisches Attribut der Klasse Server: Server.hazelcast


        // über Hazelcast-MapReduce-Algorithmus nach Veranstaltungen suchen,
        // die im Titel oder in der Beschreibung mind. eines der Suchwoerter enthalten


        throw new IllegalStateException("Methode getEventsByQuery muss erst noch implementiert werden");

    }
}
