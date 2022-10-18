package Model;

import java.io.Serializable;

/**
 * Class represents a pair Name,Score.
 * used in victory Table.
 */
public class NameAndScore implements Serializable {
    public String Name;
    public Integer Score;

    public NameAndScore(String name, Integer score){
        Name=name;
        Score=score;
    }

    public NameAndScore(NameAndScore copy){
        Name=copy.Name;
        Score=copy.Score;
    }

    /**
     * retunes the Score.
     * @return integer score.
     */
    public Integer getScore() {
        return Score;
    }

    /**
     * override of the object to string method.
     * @return String.
     */
    public String toString(){
        return("Name: " + Name + "  score: " + Score + "\n");
    }

}
