package com.othr.vs.rmi.frueherkennung.server.api;

import java.io.Serializable;
import java.util.Date;

public class Roentgenbild implements Serializable {
    private Date aufnahmeVon;
    private transient String patientenName;
    private byte[] rawData;

    public Roentgenbild(String patientenName) {
        this();
        this.patientenName = patientenName;
    }

    public Roentgenbild() {
        this.aufnahmeVon = new Date();
        this.rawData = "Some picture".getBytes();
    }

    public Date getAufnahmeVon() {
        return aufnahmeVon;
    }

    public void setAufnahmeVon(Date aufnahmeVon) {
        this.aufnahmeVon = aufnahmeVon;
    }

    public String getPatientenName() {
        return patientenName;
    }

    public void setPatientenName(String patientenName) {
        this.patientenName = patientenName;
    }

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    @Override
    public String toString() {
        return "Roentgenbild{" +
                "aufnahmeVon=" + aufnahmeVon +
                ", patientenName='" + patientenName + '\'' +
                ", rawData=" + rawData.length +
                "bytes}";
    }
}
