package algorithms.search;

import java.util.ArrayList;

/**
 * The purpose of ISearchable interface is to make something a searchable.
 */
public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    ArrayList<AState> getAllSuccessors(AState s);
    boolean goalTest(AState s);

}
