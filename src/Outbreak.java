
import java.util.*;

/**
 * Mission 5. Dynamic programming on the tree of infections. Root the tree in person 0.
 * Input.Tree.neighbours() gives the neighbours of every person. The number of people is at most about 100, so a recursive DFS is fine here.
 */
public class Outbreak {

    /**
     * Choose people to test so that for every link (somebody infected somebody) at least one of the two is tested,
     * and the total cost of testing is as small as possible. cost[v] is the price of testing person v.
     * Returns tested[v] = true if person v is tested.
     *
     * State for every person v (looking only at v and everything below it): the smallest cost when v IS tested, and
     * the smallest cost when v is NOT tested. What must then be true about the children of v in each case?
     * Do not forget to rebuild the answer (who exactly is tested), not only the cost: the picture needs it.
     * Main calls this twice: with all costs equal to 1 (the line "Tests needed") and with the real costs ("Cheapest testing").
     */
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        // TODO Mission 5a
        long[] yes = new long[n];
        long[] no = new long[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        calculate(0, -1, nb, cost, yes, no, parent);

        boolean[] tested = new boolean[n];

        boolean rootTested = yes[0] <= no[0];

        rebuild(0, rootTested, nb, parent, yes, no, tested);

        return tested;
    }

    private static void calculate(int v, int p, int[][] nb, int[] cost,
                                  long[] yes, long[] no, int[] parent) {
        parent[v] = p;

        yes[v] = cost[v];
        no[v] = 0;

        for (int child : nb[v]) {
            if (child == p) {
                continue;
            }

            calculate(child, v, nb, cost, yes, no, parent);

            yes[v] += Math.min(yes[child], no[child]);
            no[v] += yes[child];
        }
    }

    private static void rebuild(int v, boolean isTested, int[][] nb, int[] parent,
                                long[] yes, long[] no, boolean[] tested) {
        tested[v] = isTested;

        for (int child : nb[v]) {
            if (child == parent[v]) {
                continue;
            }

            boolean childTested;

            if (!isTested) {
                childTested = true;
            } else {
                childTested = yes[child] <= no[child];
            }

            rebuild(child, childTested, nb, parent, yes, no, tested);
        }
    }

    /**
     * The number of links on the longest chain of infections, that is the longest path in the tree (counted in links).
     * A path goes up from one person and down to another; for every person you need the two longest ways down.
     * Example: a line 0-1-2-3 has chain 3; a star with centre 0 and leaves 1, 2, 3 has chain 2.
     */
    public static int longestChain(int n, int[][] nb) {
        // TODO Mission 5b
        int[] result = new int[1];

        findLongest(0, -1, nb, result);

        return result[0];
    }

    private static int findLongest(int v, int p, int[][] nb, int[] result) {
        int first = 0;
        int second = 0;

        for (int child : nb[v]) {
            if (child == p) {
                continue;
            }

            int length = findLongest(child, v, nb, result) + 1;

            if (length > first) {
                second = first;
                first = length;
            } else if (length > second) {
                second = length;
            }
        }

        result[0] = Math.max(result[0], first + second);

        return first;
    }
}
