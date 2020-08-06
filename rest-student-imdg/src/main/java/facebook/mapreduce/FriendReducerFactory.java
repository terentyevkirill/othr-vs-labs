package facebook.mapreduce;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import org.javatuples.Pair;

import java.util.*;

public class FriendReducerFactory implements ReducerFactory<Pair<Integer, Integer>, Set<Integer>, Set<Integer>> {

    @Override
    public Reducer<Set<Integer>, Set<Integer>> newReducer(Pair<Integer, Integer> pair) {

        return new FriendReducer(pair);
    }

    private static class FriendReducer extends Reducer<Set<Integer>, Set<Integer>> {
        private final Set<Integer> commonFriends = new HashSet<>();
        private final Pair<Integer, Integer> pair;

        public FriendReducer(Pair<Integer, Integer> pair) {
            this.pair = pair;
        }

        @Override
        public void reduce(Set<Integer> friends) {
            if (commonFriends.isEmpty())
                commonFriends.addAll(friends);
            else
                commonFriends.retainAll(friends);
        }

        @Override
        public Set<Integer> finalizeReduce() {
            System.out.println("Common friends of " + pair + ": " + Arrays.toString(commonFriends.toArray()));
            return commonFriends;
        }
    }
}
