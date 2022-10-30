package it.unibo.generics.graph;

import it.unibo.generics.graph.api.Graph;
import it.unibo.generics.graph.impl.GraphImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public final class UseGraph {

    private UseGraph() {
    }

    /**
     * @param args
     *            ignored
     */
    public static void main(final String... args) {
        /*
         * Test your graph implementation(s) by calling testGraph
         */
        testGraph(new GraphImpl<>());
    }

    private static void testGraph(final Graph<String> graph) {
        /* NODES SECTION */
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("d");
        graph.addNode("e");
        graph.addNode("g");
        graph.addNode("h");
        graph.addNode("i");
        graph.addNode("l");
        graph.addNode("y");
        graph.addNode("z");
        // unused in ARCHS SECTION
        graph.addNode("m");
        graph.addNode("n");
        graph.addNode("o");
        // Should not add, becouse already present
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("l");
        graph.addNode("l");
        /* ARCHS SECTION */
        graph.addEdge("a", "b");
        graph.addEdge("a", "y");
        graph.addEdge("b", "g");
        graph.addEdge("b", "y");
        graph.addEdge("b", "z");
        graph.addEdge("b", "i");
        graph.addEdge("b", "l");
        graph.addEdge("b", "h");
        graph.addEdge("d", "e");
        graph.addEdge("e", "a");
        graph.addEdge("g", "y");
        graph.addEdge("h", "g");
        graph.addEdge("h", "y");
        graph.addEdge("i", "z");
        graph.addEdge("l", "z");
        graph.addEdge("y", "d");
        graph.addEdge("z", "d");
        graph.addEdge("z", "y");
        graph.addEdge("z", "a");
        // Should not add these
        graph.addEdge("a", "b");
        graph.addEdge("a", "y");
        graph.addEdge("l", "r");
        graph.addEdge("l", "s");
        graph.addEdge("l", "t");
        /*
         * Should be ["a","b","c","d","e"], in any order
         */
        assertIsAnyOf(graph.nodeSet(), Set.of(splitOnWhiteSpace("a b z d e y g h i l m n o")));
        /*
         * ["d","a"], in any order
         */
        assertIsAnyOf(graph.linkedNodes("z"), Set.of(splitOnWhiteSpace("a d y")));
        /*
         * Either the path b,c,a or b,c,d,e,a
         */
        assertIsAnyOf(
            graph.getPath("b", "a"),
            Arrays.asList(splitOnWhiteSpace("b z a")),
            Arrays.asList(splitOnWhiteSpace("b z d e a")),
            Arrays.asList(splitOnWhiteSpace("b z y d e a")),
            Arrays.asList(splitOnWhiteSpace("b i z a")),
            Arrays.asList(splitOnWhiteSpace("b i z d e a")),
            Arrays.asList(splitOnWhiteSpace("b i z y d e a")),
            Arrays.asList(splitOnWhiteSpace("b l z a")),
            Arrays.asList(splitOnWhiteSpace("b l z d e a")),
            Arrays.asList(splitOnWhiteSpace("b l z y d e a")),
            Arrays.asList(splitOnWhiteSpace("b y d e a")),
            Arrays.asList(splitOnWhiteSpace("b h y d e a")),
            Arrays.asList(splitOnWhiteSpace("b g y d e a")),
            Arrays.asList(splitOnWhiteSpace("b h g y d e a"))
        );
    }

    private static void assertIsAnyOf(final Object actual, final Object... valid) {
        for (final var target: Objects.requireNonNull(valid)) {
            if (Objects.equals(target, actual)) {
                System.out.println("OK: " + actual + " matches " + target); // NOPMD
                return;
            }
        }
        throw new AssertionError("None of " + Arrays.asList(valid) + " matches " + actual);
    }

    private static String[] splitOnWhiteSpace(final String target) {
        return target.split("\\s+");
    }
}
