package entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

// hat kein PK -> kein Entität, sondern ein Value-object -> kein @XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Adresse {
    private String strasse;
    private String ort;

    public Adresse() {
    }

    public Adresse(String strasse, String ort) {
        this.strasse = strasse;
        this.ort = ort;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
