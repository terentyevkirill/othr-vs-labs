package com.othr.vs.rmi.smarttv.server.entity;

import java.io.Serializable;
import java.util.Date;

public class Sendung implements Serializable {
    private String titel;
    private String beschreibung;
    private Date start = new Date(), ende = new Date();

    public Sendung(String titel, String beschreibung) {
        this.titel = titel;
        this.beschreibung = beschreibung;
    }

    public Sendung() {
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnde() {
        return ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    @Override
    public String toString() {
        return "Sendung{" +
                "titel='" + titel + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", start=" + start +
                ", ende=" + ende +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sendung)) return false;

        Sendung sendung = (Sendung) o;

        if (getTitel() != null ? !getTitel().equals(sendung.getTitel()) : sendung.getTitel() != null) return false;
        if (getBeschreibung() != null ? !getBeschreibung().equals(sendung.getBeschreibung()) : sendung.getBeschreibung() != null)
            return false;
        if (getStart() != null ? !getStart().equals(sendung.getStart()) : sendung.getStart() != null) return false;
        return getEnde() != null ? getEnde().equals(sendung.getEnde()) : sendung.getEnde() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitel() != null ? getTitel().hashCode() : 0;
        result = 31 * result + (getBeschreibung() != null ? getBeschreibung().hashCode() : 0);
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnde() != null ? getEnde().hashCode() : 0);
        return result;
    }
}
