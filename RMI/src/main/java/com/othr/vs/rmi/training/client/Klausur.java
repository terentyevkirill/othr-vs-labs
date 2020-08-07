package com.othr.vs.rmi.training.client;

import com.othr.vs.rmi.training.server.api.KlausurIF;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Klausur implements KlausurIF {
    private int matrikelNr;
    private String studentenName;
    private String kursName;
    private Date datum;
    private String klausurInhalt;

    public Klausur(int matrikelNr, String studentenName, String kursName, String klausurInhalt) {
        this.matrikelNr = matrikelNr;
        this.studentenName = studentenName;
        this.kursName = kursName;
        this.klausurInhalt = klausurInhalt;
        this.datum = new Date();
    }

    @Override
    public int getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(int matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public String getStudentenName() {
        return studentenName;
    }

    public void setStudentenName(String studentenName) {
        this.studentenName = studentenName;
    }

    @Override
    public String getKursName() {
        return kursName;
    }

    public void setKursName(String kursName) {
        this.kursName = kursName;
    }

    @Override
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public String getKlausurInhalt() {
        return klausurInhalt;
    }

    @Override
    public String toPrint() throws RemoteException {
        return "Klausur{" +
                "kursName='" + kursName + '\'' +
                ", datum=" + new SimpleDateFormat("dd.MM.yyyy").format(datum) +
                '}';
    }

    public void setKlausurInhalt(String klausurInhalt) {
        this.klausurInhalt = klausurInhalt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Klausur)) return false;

        Klausur klausur = (Klausur) o;

        if (getMatrikelNr() != klausur.getMatrikelNr()) return false;
        if (!getKursName().equals(klausur.getKursName())) return false;
        return getDatum().equals(klausur.getDatum());
    }

    @Override
    public int hashCode() {
        int result = getMatrikelNr();
        result = 31 * result + getKursName().hashCode();
        result = 31 * result + getDatum().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Klausur{" +
                "matrikelNr=" + matrikelNr +
                ", studentenName='" + studentenName + '\'' +
                ", kursName='" + kursName + '\'' +
                ", datum=" + new SimpleDateFormat("dd.MM.yyyy").format(datum) +
                '}';
    }

}
