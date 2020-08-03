package facebook.mapreduce;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendReducerFactory implements ReducerFactory<Pair<Integer, Integer>, List<Integer>, List<Integer>> {

    @Override
    public Reducer<List<Integer>, List<Integer>> newReducer(Pair<Integer, Integer> objects) {
        return new FriendReducer();
    }

    private static class FriendReducer extends Reducer<List<Integer>, List<Integer>> {
        private final List<Integer> commonFriends = new ArrayList<>();

        @Override
        public void reduce(List<Integer> integers) {
            commonFriends.retainAll(integers);
        }

        @Override
        public List<Integer> finalizeReduce() {
            System.out.println("Reducer: " + Arrays.toString(commonFriends.toArray()));
            return commonFriends;
        }
    }
}
