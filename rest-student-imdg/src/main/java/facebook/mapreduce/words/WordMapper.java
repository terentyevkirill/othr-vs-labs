package facebook.mapreduce.words;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class WordMapper implements Mapper<String, String, String, Integer> {
    @Override
    public void map(String keyIn, String valueIn, Context<String, Integer> context) {
        context.emit(valueIn, 1);
    }
}
