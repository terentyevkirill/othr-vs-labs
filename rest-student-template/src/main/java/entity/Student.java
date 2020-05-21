package entity;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement     // <student>...
@XmlAccessorType(XmlAccessType.PROPERTY) // bound get/set methods to XML
public class Student {

    @XmlAttribute   // <student matrikelNr="1">...
    private int matrikelNr;
    private String vorname;
    private String nachname;

    // Default-Konstruktor zwingend notwendig
    public Student() {}

    public Student(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Student(int matrikelNr, String vorname, String nachname) {
        this(vorname, nachname);
        this.matrikelNr = matrikelNr;
    }


    public int getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(int matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return matrikelNr == student.matrikelNr;
    }

    @Override
    public int hashCode() {

        return Objects.hash(matrikelNr);
    }
}
