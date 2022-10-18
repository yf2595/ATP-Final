package Model;


import algorithms.mazeGenerators.Maze;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 * Class represents the victory table.
 * implement as a priority queue with name,score pairs.
 * every maze has its own victory table.
 */
public class Victors implements Serializable {
    private Queue<NameAndScore> listVictory;
    private Maze maze;

    /**
     * Class constructor.
     * initialize a new priority queue.
     * sets the maze attribute.
     * @param m the Maze.
     */
    public Victors (Maze m){
        maze = m;
        this.listVictory = new PriorityQueue(new VictorComperator());
    }

    /**
     * create a new name,score pair and add it to the queue.
     * @param name players name.
     * @param score palyers score.
     */
    public void addVictor(String name, Integer score){
        NameAndScore victor = new NameAndScore(name,score);
        listVictory.add(victor);
    }

    /**
     * returns the attribute maze.
     * @return Maze instance maze.
     */
    public Maze getMaze() {
        return maze;
    }
    /**
     * set the attribute maze.
     * @param maze Maze instance.
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * objects toString ovveride.
     * @return String.
     */
    public String toString(){
        int number =1;
        Queue<NameAndScore> temp = new PriorityQueue(new VictorComperator());
        String ret = "rows: " + maze.getRows() +"\n" + "columns: " + maze.getColumns() + "\n\n";
        while(!listVictory.isEmpty()){
            NameAndScore NAS = new NameAndScore(listVictory.poll());
            ret = ret + number++ + ". " + NAS + "\n";
            temp.add(NAS);
        }
        listVictory=temp;
        return ret;
    }

}