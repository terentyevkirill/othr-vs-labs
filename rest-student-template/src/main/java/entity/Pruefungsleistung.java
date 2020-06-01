package entity;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Pruefungsleistung {
    private String pruefungId;
    private int matrikelNr;
    private int versuch;
    private float note;

    public Pruefungsleistung() {
    }

    public Pruefungsleistung(String pruefungId, int matrikelNr, int versuch, float note) {
        this.pruefungId = pruefungId;
        this.matrikelNr = matrikelNr;
        this.versuch = versuch;
        this.note = note;
    }

    public String getPruefungId() {
        return pruefungId;
    }

    public void setPruefungId(String pruefungId) {
        this.pruefungId = pruefungId;
    }

    public int getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(int matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public int getVersuch() {
        return versuch;
    }

    public void setVersuch(int versuch) {
        this.versuch = versuch;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pruefungsleistung)) return false;

        Pruefungsleistung that = (Pruefungsleistung) o;

        if (getMatrikelNr() != that.getMatrikelNr()) return false;
        if (getVersuch() != that.getVersuch()) return false;
        return getPruefungId() != null ? getPruefungId().equals(that.getPruefungId()) : that.getPruefungId() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pruefungId, matrikelNr, versuch);
    }
}
