package entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

// hat kein PK -> kein Entität, sondern ein Value-object -> kein @XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Adresse {
    private String strasse;
    private String plz;
    private String ort;

    public Adresse() {
    }

    public Adresse(String strasse, String plz, String ort) {
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adresse)) return false;

        Adresse adresse = (Adresse) o;

        if (getStrasse() != null ? !getStrasse().equals(adresse.getStrasse()) : adresse.getStrasse() != null)
            return false;
        if (getPlz() != null ? !getPlz().equals(adresse.getPlz()) : adresse.getPlz() != null) return false;
        return getOrt() != null ? getOrt().equals(adresse.getOrt()) : adresse.getOrt() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strasse, plz, ort);
    }
}
