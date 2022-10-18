package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Solution class purpose to keep a solution.
 */
public class Solution implements Serializable {
    //attribute s- The solution path, list of states
    private ArrayList<AState> s;

    /**
     * Class solution constructor .
     * @param so This is an array list of AStates which represents the solution path.
     */
    public Solution(ArrayList<AState> so) {

        s= so;
    }

    /**
     * This method returns the solution as array list.
     * @return ArrayList This return a solution
     */
    public ArrayList<AState> getSolutionPath(){

        return s;
    }
}
