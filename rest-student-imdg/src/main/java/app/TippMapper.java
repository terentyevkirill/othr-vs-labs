package app;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import de.othr.vs.xml.Veranstaltung;

import java.util.Calendar;
import java.util.Date;

public class TippMapper implements Mapper<String, Veranstaltung, String, Veranstaltung> {
    private final String[] suchwoerter;

    public TippMapper(String[] suchwoerter) {
        this.suchwoerter = suchwoerter;
    }

    @Override
    public void map(String id, Veranstaltung veranstaltung, Context<String, Veranstaltung> context) {
        for (String wort : suchwoerter) {
            if (veranstaltung.getTitel().toLowerCase().contains(wort.toLowerCase())
                    || veranstaltung.getBeschreibung().toLowerCase().contains(wort.toLowerCase())) {
                if (veranstaltung.getEnde().after(getYesterdayForDate(new Date()))) {
                    context.emit(wort, veranstaltung);
                }
            }
        }
    }
    private Date getYesterdayForDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
