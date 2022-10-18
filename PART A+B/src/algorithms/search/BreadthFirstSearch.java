package algorithms.search;

import java.util.*;

/**
 * The BreadthFirstSearch class extend ASearchingAlgorithm and its purpose to search using BFS algorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm{

    /**
     * Class BreadthFirstSearch constructor .
     * in the constructor we set the queue to be a ArrayDeque queue.
     */
    public BreadthFirstSearch() {
        queue=new ArrayDeque<>();
    }
    /**
     * This method solve the problem and return a solution for any Object that implements ISearchable interface.
     * this solve uses breadth First Search algorithm.
     * some classes are override this function.
     * @param s This is the problem to solve.
     * @return solution This return a Solution object.
     */
    public Solution solve(ISearchable s) {
        if (s == null) {
            //illegal parameter.
            throw new NullPointerException("given Parameter is NULL");
        }
        AState start = s.getStartState();
        queue.add(start);
        AState curr = start;
        while (!(s.goalTest(curr))) {
            if (queue.isEmpty()) {
                break;
            }
            curr = queue.remove();
            nodes++;
            closed.add(curr);
            for (AState n : s.getAllSuccessors(curr)) {
                if (!closed.contains(n) && !queue.contains(n)) {
                    n.setFather(curr);
                    n.setCost(curr.getCost(n));
                    queue.add(n);
                }
            }
        }
        Solution so = new Solution(getRoots(s));
        return so;
    }

    /**
     * This method returns the search name's .
     * @return string This is the name of this search - "Breadth First Search".
     */
    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
