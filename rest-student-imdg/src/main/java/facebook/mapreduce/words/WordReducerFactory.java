package facebook.mapreduce.words;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class WordReducerFactory implements ReducerFactory<String, Integer, Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new WordReducer();
    }

    private static class WordReducer extends Reducer<Integer, Integer> {

        private final AtomicInteger collector = new AtomicInteger(0);
        @Override
        public void reduce(Integer integer) {
            this.collector.set(this.collector.get() + integer);
        }

        @Override
        public Integer finalizeReduce() {
            return this.collector.get();
        }
    }
}
