package facebook.mapreduce.words;

import com.hazelcast.mapreduce.Collator;
import org.javatuples.Pair;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

public class MinWordCollator implements Collator<Map.Entry<String, Integer>, Map.Entry<String, Integer>> {
    @Override
    public Map.Entry<String, Integer> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow(NoSuchElementException::new);
    }
}
