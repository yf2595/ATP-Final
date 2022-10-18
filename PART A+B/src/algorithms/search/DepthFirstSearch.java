package algorithms.search;


import java.util.HashSet;
import java.util.Stack;
/**
 * The DepthFirstSearch class extend ASearchingAlgorithm and its purpose to search using DFS algorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm{
    HashSet<AState> open;
    /**
     * Class DepthFirstSearch constructor .
     */
    public DepthFirstSearch() {
        nodes=0;
        closed= new HashSet<>();
        open=new HashSet<>();
    }

    /**
     * Override function
     * This method solve the problem and returns a solution.
     * this solve using the Depth First Search algorithm.
     * @param s This is the searchable problem we wish to solve.
     * @return solution This returns an solution object
     */

    @Override
    public Solution solve(ISearchable s){
        if(s==null){
            //illegal parameter.
            throw new NullPointerException("given Parameter is NULL");
        }
        AState start=s.getStartState();
        Stack <AState> stack = new Stack<>(); //the first stack
        stack.push(start);
        open.add(start);
        AState temp;
        while(!stack.isEmpty()){
            nodes++;
            temp = stack.pop();
            open.remove(temp);
            if(s.goalTest(temp)){
                break;
            }
            if(!closed.contains(temp)){
                closed.add(temp);
                for(AState n:s.getAllSuccessors(temp)){
                    if(!open.contains(n) &&!closed.contains(n) ){
                        n.setFather(temp);
                        stack.push(n);
                        open.add(n);
                    }
                }
            }
        }
        Solution so= new Solution(getRoots(s));
        return so;
    }

    /**
     * This method is return the name of the search.
     * @return string This is a name of this search - "Depth First Search".
     */
    @Override
    public String getName() {
        return "Depth First Search";
    }
}
