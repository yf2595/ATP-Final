package algorithms.search;

import java.util.ArrayList;

/**
 * An interface for all searching algorithms
 */

public interface ISearchingAlgorithm {
    Solution solve(ISearchable s);
    String getName();
    int getNumberOfNodesEvaluated();
    ArrayList<AState> getRoots(ISearchable s);

}
