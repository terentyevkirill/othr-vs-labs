package facebook.mapreduce;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import org.javatuples.Pair;

import java.util.Set;
import java.util.stream.Stream;

public class FriendMapper implements Mapper<Integer, Set<Integer>, Pair<Integer, Integer>, Set<Integer>> {

    @Override
    public void map(Integer keyIn, Set<Integer> valuesIn, Context<Pair<Integer, Integer>, Set<Integer>> context) {
        valuesIn.forEach(valueIn -> context.emit(Pair.fromArray(Stream.of(keyIn, valueIn).sorted().toArray(Integer[]::new)), valuesIn));
    }
}
