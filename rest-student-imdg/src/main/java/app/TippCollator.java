package app;

import com.hazelcast.mapreduce.Collator;
import de.othr.vs.xml.Veranstaltung;

import java.util.List;
import java.util.Map;

public class TippCollator implements Collator<Map.Entry<String, List<Veranstaltung>>, List<Veranstaltung>> {
    @Override
    public List<Veranstaltung> collate(Iterable<Map.Entry<String, List<Veranstaltung>>> iterable) {
        return null;
    }
}
