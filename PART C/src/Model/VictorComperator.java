package Model;


import java.io.Serializable;
import java.util.Comparator;

/**
 * A Class used to compare between 2 name and score pairs.
 * used in victory Table.
 */
public class VictorComperator implements Comparator<NameAndScore> , Serializable {
    public VictorComperator() {
    }

    /**
     * compare between 2 name,score pairs.
     * the comperhansion is based on the score.
     * @param o1 first Name,Score pair.
     * @param o2 second Name,Score pair.
     * @return int .
     */
    @Override
    public int compare(NameAndScore o1, NameAndScore o2) {
        if(o1.getScore() < o2.getScore()){
            return -1;
        }
        else{
            return o1.getScore() > o2.getScore() ? 1 : 0;
        }
    }
}
