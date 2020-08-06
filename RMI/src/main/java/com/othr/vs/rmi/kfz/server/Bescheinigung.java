package com.othr.vs.rmi.kfz.server;

import com.othr.vs.rmi.kfz.server.api.BescheinigungIF;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bescheinigung implements BescheinigungIF {
    private final int bescheinigungsId;
    private final String besitzerSteuerId;
    private final String autokennzeichnen;
    private final Date anmeldungsDatum;
    private final String anmerkungen;
    private final String verantwortlich;

    public Bescheinigung(int bescheinigungsId, String besitzerSteuerId, String autokennzeichnen, String anmerkungen, String verantwortlich) {
        this.bescheinigungsId = bescheinigungsId;
        this.besitzerSteuerId = besitzerSteuerId;
        this.autokennzeichnen = autokennzeichnen;
        this.anmeldungsDatum = new Date();
        this.anmerkungen = anmerkungen;
        this.verantwortlich = verantwortlich;
    }

    @Override
    public int getBescheinigungsId() {
        return bescheinigungsId;
    }

    @Override
    public String getBesitzerSteuerId() {
        return besitzerSteuerId;
    }

    @Override
    public String getAutokennzeichnen() {
        return autokennzeichnen;
    }

    @Override
    public Date getAnmeldungsDatum() {
        return anmeldungsDatum;
    }

    public String getAnmerkungen() {
        return anmerkungen;
    }

    public String getVerantwortlich() {
        return verantwortlich;
    }

    @Override
    public String toPrint() throws RemoteException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return "Bescheinigung{" +
                "bescheinigungsId=" + bescheinigungsId +
                ", besitzerSteuerId='" + besitzerSteuerId + '\'' +
                ", autokennzeichnen='" + autokennzeichnen + '\'' +
                ", anmeldungsDatum=" + sdf.format(anmeldungsDatum) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bescheinigung)) return false;

        Bescheinigung that = (Bescheinigung) o;

        return getBescheinigungsId() == that.getBescheinigungsId();
    }

    @Override
    public int hashCode() {
        return getBescheinigungsId();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return "Bescheinigung{" +
                "bescheinigungsId=" + bescheinigungsId +
                ", besitzerSteuerId='" + besitzerSteuerId + '\'' +
                ", autokennzeichnen='" + autokennzeichnen + '\'' +
                ", anmeldungsDatum=" + sdf.format(anmeldungsDatum) +
                ", anmerkungen='" + anmerkungen + '\'' +
                ", verantwortlich='" + verantwortlich + '\'' +
                '}';
    }
}
