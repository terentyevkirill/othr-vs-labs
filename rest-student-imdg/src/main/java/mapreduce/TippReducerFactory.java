package mapreduce;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import de.othr.vs.xml.Veranstaltung;

import java.util.ArrayList;
import java.util.List;

public class TippReducerFactory implements ReducerFactory<String, Veranstaltung, List<Veranstaltung>> {
    @Override
    public Reducer<Veranstaltung, List<Veranstaltung>> newReducer(String suchwort) {
        return new TippReducer();
    }

    private static class TippReducer extends Reducer<Veranstaltung, List<Veranstaltung>> {
        private volatile List<Veranstaltung> sammler = new ArrayList<>();

        @Override
        public void reduce(Veranstaltung veranstaltung) {
            if (!sammler.contains(veranstaltung)) {
                sammler.add(veranstaltung);
            }
        }

        @Override
        public List<Veranstaltung> finalizeReduce() {
            return sammler;
        }
    }


}
