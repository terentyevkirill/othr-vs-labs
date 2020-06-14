package mapreduce;

import com.hazelcast.mapreduce.Collator;
import de.othr.vs.xml.Veranstaltung;

import java.util.*;

public class TippCollator implements Collator<Map.Entry<String, List<Veranstaltung>>, List<Veranstaltung>> {
    @Override
    public List<Veranstaltung> collate(Iterable<Map.Entry<String, List<Veranstaltung>>> values) {
        Set<Veranstaltung> events = new HashSet<>();
        values.forEach(e -> events.addAll(e.getValue()));
        List<Veranstaltung> result = new ArrayList<>(events);
        result.sort(Comparator.comparing(Veranstaltung::getStart));
        return result;
    }
}
