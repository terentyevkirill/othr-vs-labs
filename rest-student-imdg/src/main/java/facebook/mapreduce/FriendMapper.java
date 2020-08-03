package facebook.mapreduce;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import facebook.entity.User;
import org.javatuples.Pair;

import java.util.List;

public class FriendMapper implements Mapper<User, List<Integer>, Pair<Integer, Integer>, List<Integer>> {

    @Override
    public void map(User user, List<Integer> friends, Context<Pair<Integer, Integer>, List<Integer>> context) {
//        int userId = user.getUserId();
//        System.out.println("Mapper: " + userId);
//        context.emit(userId, friends);
    }
}
