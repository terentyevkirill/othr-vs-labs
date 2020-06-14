package app;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import de.othr.vs.xml.Veranstaltung;

import java.util.List;

public class TippReducerFactory implements ReducerFactory<String, Veranstaltung, List<Veranstaltung>> {
    @Override
    public Reducer<Veranstaltung, List<Veranstaltung>> newReducer(String s) {
        return null;
    }
}
