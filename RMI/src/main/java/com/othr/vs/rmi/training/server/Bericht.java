package com.othr.vs.rmi.training.server;

import com.othr.vs.rmi.training.server.api.BerichtIF;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bericht implements BerichtIF {
    private int matrikelNr;
    private String kursName;
    private Date klausurDatum;
    private Date berichtDatum;
    private float note;
    private String prueferName;

    public Bericht(int matrikelNr, String kursName, Date klausurDatum, float note, String prueferName) {
        this.matrikelNr = matrikelNr;
        this.kursName = kursName;
        this.klausurDatum = klausurDatum;
        this.note = note;
        this.prueferName = prueferName;
        this.berichtDatum = new Date();
    }

    @Override
    public int getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(int matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    @Override
    public String getKursName() {
        return kursName;
    }

    public void setKursName(String kursName) {
        this.kursName = kursName;
    }

    @Override
    public Date getKlausurDatum() {
        return klausurDatum;
    }

    public void setKlausurDatum(Date klausurDatum) {
        this.klausurDatum = klausurDatum;
    }

    @Override
    public Date getBerichtDatum() {
        return berichtDatum;
    }

    public void setBerichtDatum(Date berichtDatum) {
        this.berichtDatum = berichtDatum;
    }

    @Override
    public float getNote() {
        return note;
    }

    @Override
    public String toPrint() throws RemoteException {
        return "Bericht{" +
                "matrikelNr=" + matrikelNr +
                ", kursNr='" + kursName + '\'' +
                ", klausurDatum=" + new SimpleDateFormat("dd.MM.yyyy").format(klausurDatum) +
                ", berichtDatum=" + new SimpleDateFormat("dd.MM.yyyy").format(berichtDatum) +
                ", note=" + note +
                '}';
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getPrueferName() {
        return prueferName;
    }

    public void setPrueferName(String prueferName) {
        this.prueferName = prueferName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bericht)) return false;

        Bericht bericht = (Bericht) o;

        if (getMatrikelNr() != bericht.getMatrikelNr()) return false;
        if (!getKursName().equals(bericht.getKursName())) return false;
        return getKlausurDatum().equals(bericht.getKlausurDatum());
    }

    @Override
    public int hashCode() {
        int result = getMatrikelNr();
        result = 31 * result + getKursName().hashCode();
        result = 31 * result + getKlausurDatum().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Bericht{" +
                "matrikelNr=" + matrikelNr +
                ", kursNr='" + kursName + '\'' +
                ", klausurDatum=" + new SimpleDateFormat("dd.MM.yyyy").format(klausurDatum) +
                ", berichtDatum=" + new SimpleDateFormat("dd.MM.yyyy").format(berichtDatum) +
                ", note=" + note +
                ", prueferName='" + prueferName + '\'' +
                '}';
    }
}
