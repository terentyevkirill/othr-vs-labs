package com.othr.vs.rmi.smarttv.server.entity;

import java.io.Serializable;
import java.util.Date;

import static com.othr.vs.rmi.smarttv.server.entity.Codec.*;

public class Aufnahmewunsch implements Serializable {
    private Date start = new Date(), ende = new Date();
    private Codec codec = MPEG4;

    public Aufnahmewunsch(Date start, Date ende, Codec codec) {
        this.start = start;
        this.ende = ende;
        this.codec = codec;
    }

    public Aufnahmewunsch() {
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

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aufnahmewunsch)) return false;

        Aufnahmewunsch that = (Aufnahmewunsch) o;

        if (getStart() != null ? !getStart().equals(that.getStart()) : that.getStart() != null) return false;
        if (getEnde() != null ? !getEnde().equals(that.getEnde()) : that.getEnde() != null) return false;
        return getCodec() == that.getCodec();
    }

    @Override
    public int hashCode() {
        int result = getStart() != null ? getStart().hashCode() : 0;
        result = 31 * result + (getEnde() != null ? getEnde().hashCode() : 0);
        result = 31 * result + (getCodec() != null ? getCodec().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Aufnahmewunsch{" +
                "start=" + start +
                ", ende=" + ende +
                ", codec=" + codec +
                '}';
    }
}
