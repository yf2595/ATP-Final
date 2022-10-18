package algorithms.search;

import java.util.HashMap;

/**
 * The AState abstract class.
 * This abstract class is a base for all states.
 * attribute value is the value of this state.
 * attribute father is a ancestor Of this state.
 * attribute neighbors is a hashmap of neighbors when the AState is the neighbor and the
 * integer is the cost of the way between this state to this neighbor.
 */
public abstract class AState {
    private int cost;
    private AState father;
    private HashMap<AState, Integer> neighbors;

    /**
     * This is a constructor AState.
     */
    public AState() {
        cost=0;
        neighbors=new HashMap<AState, Integer>(0);

    }

    /**
     * This method adding neighbor to this state.
     * param i is the cost of way between this state to this neighbor.
     * param n is the neighbor state.
     */
    public void addNeighbors(int i, AState n) {
        this.neighbors.put(n,i);
    }



    /**
     * This method sets the father of this state.
     * param father is the new father.
     */
    public void setFather(AState father) {
        this.father = father;
    }



    /**
     * This method returns the father state of this state.
     * @return boolean This returns attribute father.
     */
    public AState getFather() {
        return father;
    }

    /**
     * This method returns the way cost between this state to neighbor n.
     * param n is a neighbor state.
     * @return boolean This returns the cost.
     */
    public int getCost(AState n){
        return neighbors.get(n);
    }

    public int getCost(){return cost;}

    public void setCost(int c){ cost=c;}
    /**
     * This method returns this state map of neighbor's .
     * @return HashMap This returns neighbors.
     */
    public HashMap<AState, Integer> getNeighbors() {
        return neighbors;
    }


    /**
     * This method check equals between this state to parameter o.
     * @return boolean This returns true/false - if this state and parameter o are similar.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return this == o;

    }

}
