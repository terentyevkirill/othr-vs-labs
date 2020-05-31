package entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

// hat kein PK -> kein Entität, sondern ein Value-object -> kein @XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Adresse {
    protected String strasse;
    protected String ort;

    public Adresse() {
    }

    public Adresse(String strasse, String ort) {
        this.strasse = strasse;
        this.ort = ort;
    }
}
