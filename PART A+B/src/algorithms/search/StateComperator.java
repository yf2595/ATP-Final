package algorithms.search;
import java.util.*;

/**
 * The StateComparator class implements Comparator.
 * Its purpose is to be a comparator in the PriorityQueue in BestFirstSearch class.
 * This class compares between 2 AState costs.
 */
public class StateComperator implements Comparator<AState> {

    /**
     * This method compares between two AStates costs.
     * @param s1 This is the first parameter to compare method.
     * @param s2 This is the second parameter to compare method.
     */

    public int compare(AState s1, AState s2) {
        if(s1.getCost() < s2.getCost()){
            return -1;
        }
        else if(s1.getCost() > s2.getCost()){
            return 1;
        }
        return 0;
    }

}