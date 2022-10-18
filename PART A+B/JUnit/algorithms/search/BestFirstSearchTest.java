package algorithms.search;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.SearchableMaze3D;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    BestFirstSearch bfs;

    @Test
    void solveNull() {
        bfs=new BestFirstSearch();
        assertThrows(NullPointerException.class,()->bfs.solve(null));
    }
    @Test
    void solve3D() {
        bfs=new BestFirstSearch();
        IMaze3DGenerator mg = new MyMaze3DGenerator();
        Maze3D maze = mg.generate(20, 50,40);
        SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);
        assertNotNull(bfs.solve(searchableMaze));

    }
    @Test
    void CheckPathLength(){
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(1000, 1000);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        bfs = new BestFirstSearch();
        BreadthFirstSearch breadth= new BreadthFirstSearch();
        Solution s1= bfs.solve(searchableMaze);
        Solution s2=breadth.solve(searchableMaze);
        assertTrue(s2.getSolutionPath().size() <= s1.getSolutionPath().size());
    }
    @Test
    void checkTime(){
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(1000, 1000);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        bfs=new BestFirstSearch();
        assertTimeout(Duration.ofMinutes(1),()->bfs.solve(searchableMaze));
    }
    @Test
    void checkCost(){
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(1000, 1000);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        bfs = new BestFirstSearch();
        BreadthFirstSearch breadth= new BreadthFirstSearch();
        bfs.solve(searchableMaze);
        breadth.solve(searchableMaze);
        assertTrue(bfs.getCost() <= breadth.getCost());
    }
    @Test
    void checkTime3D(){
        IMaze3DGenerator mg = new MyMaze3DGenerator();
        Maze3D maze = mg.generate(100, 100,100);
        SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);
        bfs=new BestFirstSearch();
        assertTimeout(Duration.ofMinutes(1),()->bfs.solve(searchableMaze));
    }
    @Test
    void checkSolveNotNull(){
        bfs=new BestFirstSearch();
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(20, 50);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        assertNotNull(bfs.solve(searchableMaze));
    }
    @Test
    void getName() {
        bfs=new BestFirstSearch();
        assertEquals("Best First Search", bfs.getName());
    }
    @Test
    void getRoots(){
        bfs=new BestFirstSearch();
        assertThrows(NullPointerException.class,()->bfs.getRoots(null));
    }
    @Test
    void getNumberOfNodesEvaluatedZero(){
        bfs=new BestFirstSearch();
        assertEquals(0,bfs.getNumberOfNodesEvaluated());
    }
    @Test
    void getNumberOfNodesEvaluatedBiggerThenZero(){
        bfs=new BestFirstSearch();
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(2, 2);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        bfs.solve(searchableMaze);
        assertNotEquals(1,bfs.getNumberOfNodesEvaluated());
    }
}