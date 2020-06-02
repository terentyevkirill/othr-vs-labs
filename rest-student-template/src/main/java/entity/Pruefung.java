package entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Pruefung {
    protected String pruefungId;
    protected String bezeichnung;
    protected int ects;

    public Pruefung() {
    }

    public Pruefung(String pruefungId, String bezeichnung, int ects) {
        this.pruefungId = pruefungId;
        this.bezeichnung = bezeichnung;
        this.ects = ects;
    }
}
