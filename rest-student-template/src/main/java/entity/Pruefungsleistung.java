package entity;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pruefungsleistung {
    private int id;     // PK
    private String pruefungId;  // FK
    private int matrikelNr;     // FK
    private int versuch;
    private String note;

    public Pruefungsleistung() {
    }

    public Pruefungsleistung(String pruefungId, int matrikelNr, int versuch, String note) {
        this.pruefungId = pruefungId;
        this.matrikelNr = matrikelNr;
        this.versuch = versuch;
        this.note = note;
    }
    public Pruefungsleistung(int id, String pruefungId, int matrikelNr, int versuch, String note) {
        this(pruefungId, matrikelNr, versuch, note);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
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
