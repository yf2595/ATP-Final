package algorithms.search;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * The BestFirstSearch class extend ASearchingAlgorithm
 * Its purpose is to search the most low cost solution.
 */
public class BestFirstSearch extends BreadthFirstSearch {

    /**
     * This is a constructor BestFirstSearch.
     * in the constructor we initialize the queue to be a priority queue.
     */
    public BestFirstSearch() {
        queue=new PriorityQueue<>(new StateComperator());
    }

    /**
     * This method returns the search name.
     * @return string This is a name of this search - "Best First Search".
     */
    @Override
    public String getName() {
        return "Best First Search";
    }
}
