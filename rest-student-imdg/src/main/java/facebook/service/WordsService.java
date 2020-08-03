package facebook.service;

import app.OTHRestException;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import facebook.app.RestException;
import facebook.mapreduce.words.MaxWordCollator;
import facebook.mapreduce.words.MinWordCollator;
import facebook.mapreduce.words.WordMapper;
import facebook.mapreduce.words.WordReducerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static facebook.app.Server.WORDS_LIST_NAME;
import static facebook.app.Server.hazelcast;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("words")
public class WordsService {

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    public Map<String, Integer> getWordsCount() {
        System.out.println("getWordsCount");
        IList<String> words = hazelcast.getList(WORDS_LIST_NAME);
        populateList(words);

        JobTracker jobTracker = hazelcast.getJobTracker("default");
        KeyValueSource<String, String> source = KeyValueSource.fromList(words);
        Job<String, String> job = jobTracker.newJob(source);
        //
        JobCompletableFuture<Map<String, Integer>> jobCompletableFuture = job
                .mapper(new WordMapper())
                .reducer(new WordReducerFactory())
                .submit();
        try {
            return jobCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RestException(500, e.getMessage());
        } finally {
            words.clear();
        }
    }

    @GET
    @Path("/{strategy}")
    @Produces(APPLICATION_JSON)
    public Map.Entry<String, Integer> getMinOrMaxWordCount(@PathParam("strategy") String strategy) {
        boolean isMax;
        if (strategy.equalsIgnoreCase("min"))
            isMax = false;
        else if (strategy.equalsIgnoreCase("max"))
            isMax = true;
        else throw new OTHRestException(404, "Nothing found");

        System.out.println("getMaxWordCount");
        IList<String> words = hazelcast.getList(WORDS_LIST_NAME);
        populateList(words);

        JobTracker jobTracker = hazelcast.getJobTracker("default");
        KeyValueSource<String, String> source = KeyValueSource.fromList(words);
        Job<String, String> job = jobTracker.newJob(source);
        //
        JobCompletableFuture<Map.Entry<String, Integer>> jobCompletableFuture = job
                .mapper(new WordMapper())
                .reducer(new WordReducerFactory())
                .submit(isMax ? new MaxWordCollator() : new MinWordCollator());
        try {
            return jobCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RestException(500, e.getMessage());
        } finally {
            words.clear();
        }
    }

    private void populateList(IList<String> words) {
        words.add("Hello");
        words.add("Hello");
        words.add("World");
        words.add("How");
        words.add("are");
        words.add("are");
        words.add("you");
        words.add("?");
        words.add("?");
        words.add("?");
    }

}
