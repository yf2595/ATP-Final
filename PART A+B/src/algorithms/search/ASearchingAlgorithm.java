package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

/**
 * The ASearchingAlgorithm abstract class implements ISearchingAlgorithm.
 * This abstract class is a base of all searching algorithm.
 * attribute nodes is a count of all the nodes that this searching algorithm pass.
 * attribute cost is a cost of the route from the start to the goal.
 * attribute queue is a queue that use some of searching algorithm. and all searching algorithm has different queue type.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected int nodes;
    protected int cost;
    protected Queue<AState> queue;
    protected HashSet<AState> closed;

    /**
     * Class ASearchingAlgorithm constructor .
     * reset nodes and cost attributes.
     */
    public ASearchingAlgorithm() {
        this.nodes = 0;
        this.cost = 0;
        closed=new HashSet<>();
    }




    /**
     * This method returns the number Of Nodes Evaluated during the search.
     * @return int Number of nodes.
     */
    public int getNumberOfNodesEvaluated(){
        return nodes;
    }

    /**
     * This method returns a list Of Nodes in the solution way from start to goal.
     * @return ArrayList All the states in the solution path.
     */
    public ArrayList<AState> getRoots(ISearchable s){
        if(s==null){
            //illegal parameter.
            throw new NullPointerException("given Parameter is NULL");
        }
        ArrayList<AState> ret = new ArrayList<>();
        AState curr=s.getGoalState();
        AState old;
        ret.add(curr);
        while(curr != null){
            old=curr;
            curr=curr.getFather();
            if(curr != null) {
                ret.add(curr);
                cost += curr.getCost(old);
            }
        }
        ArrayList<AState> retFinal = new ArrayList<>();
        for(int i= ret.size();i>0;i--){
            retFinal.add(ret.get(i-1));
        }
        return retFinal;
    }

    /**
     * This method returns the cost Of  the solution way.
     * @return int The cost attribute
     */
    public int getCost() {
        return cost;
    }
}
