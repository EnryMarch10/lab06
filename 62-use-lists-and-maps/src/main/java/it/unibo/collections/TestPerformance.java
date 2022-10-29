package it.unibo.collections;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * Example performance measuring. Use this class as working example of how to
 * measure the time necessary to perform operations on data structures.
 */
public final class TestPerformance {

    private static final int ELEMS = 1_000_000;

    private TestPerformance() { }

    public static void TestInsertingBeginningList(final List<Integer> list, final int elems) {
        long time = System.nanoTime();
        for (int i = 1; i <= elems; i++) {
            list.add(0, i);
        }
        time = System.nanoTime() - time;
        System.out.println( "Adding " + elems + " ints to head of " + list.getClass() + " took " + time + "ns (" + TimeUnit.NANOSECONDS.toMillis(time) + "ms)" );
    }

    public static void TestReadingMiddleList(final List<Integer> list, final int elems) {
        long time = System.nanoTime();
        for (int i = 0; i < elems; i++) {
            list.get(list.size()/2);
        }
        time = System.nanoTime() - time;
        System.out.println( "Reading " + elems + " ints int he middle of " + list.getClass() + " took " + time + "ns (" + TimeUnit.NANOSECONDS.toMillis(time) + "ms)" );
    }

    /**
     * @param s
     *            ignored
     */
    public static void main(final String... s) {
        /*
         * Set up the data structures
         */
        final Set<String> set = new TreeSet<>();
        /*
         * Prepare a variable for measuring time
         */
        long time = System.nanoTime();
        /*
         * Run the benchmark
         */
        for (int i = 1; i <= ELEMS; i++) {
            set.add(Integer.toString(i));
        }
        /*
         * Compute the time and print result
         */
        time = System.nanoTime() - time;
        final var millis = TimeUnit.NANOSECONDS.toMillis(time);
        System.out.println(// NOPMD
            "Converting "
                + set.size()
                + " ints to String and inserting them in a Set took "
                + time
                + "ns ("
                + millis
                + "ms)"
        );
    }
}
