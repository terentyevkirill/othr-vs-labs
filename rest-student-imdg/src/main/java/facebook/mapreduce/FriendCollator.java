package facebook.mapreduce;

import com.hazelcast.mapreduce.Collator;
import org.javatuples.Pair;

import java.util.*;

public class FriendCollator implements Collator<Map.Entry<Pair<Integer, Integer>, List<Integer>>, List<Integer>> {

    @Override
    public List<Integer> collate(Iterable<Map.Entry<Pair<Integer, Integer>, List<Integer>>> values) {
        List<Integer> commonFriends = new ArrayList<>();
        values.forEach(e -> {
            System.out.println("Common friends of " + e.getKey().getValue0() + " and " + e.getKey().getValue1() + ":");
            System.out.println(Arrays.toString(e.getValue().toArray()));
            commonFriends.addAll(e.getValue());
        });


        return commonFriends;
    }
}
