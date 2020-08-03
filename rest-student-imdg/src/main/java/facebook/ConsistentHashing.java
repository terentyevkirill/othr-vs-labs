package facebook;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class ConsistentHashing {
    public static void main(String[] args) {
        String key = "ISBN-123-456-789-0";
        HashCode hash = Hashing.sha512().hashString(key, Charset.defaultCharset());
        System.out.println("KEY: " + key);
        System.out.println("HASH: " + hash.toString());
        System.out.println("HASH CLASS: " + hash.getClass().getName());
        for(int buckets = 1; buckets < 1000; buckets++) {
            int node = Hashing.consistentHash(hash, buckets);
            System.out.printf(" Bei %3d Buckets --> zugeteilte Node: %d %n", buckets, node);
        }
    }
}
