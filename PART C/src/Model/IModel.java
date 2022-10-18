package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.IOException;
import java.util.Observer;

public interface IModel{
    void generateMaze(int row, int col) throws IOException;
    Maze getMaze();
    void updateCharacterLocation(int direction);
    int getRowChar();
    void finished();
    int getColChar();
    void setMaze(Maze m);
    void closeServers();
    void solveMaze() throws IOException;
    void playHAHAHA();
    Solution getSolution();
    void MusicPlay();
    void soundProperties();
    void load()throws IOException, ClassNotFoundException;
    void save() throws IOException;
    Integer getScore();
    void ResetScore();
    void saveScore(String name, int scoreSave) throws IOException, ClassNotFoundException;
    void assignObserver(Observer o);
    Victors getVictors();
}
