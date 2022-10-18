package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
/**
 * Class that implement a strategy that allowing the server to solve a maze that he recive from the client
 * the server will send back to the client the maze solution
 * the server will save the mazes and their solutions in the hard disk
 * the search Algorithm is given by the configuration singelton class
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy{

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        try{
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        //read the maze from the client
        Maze m = (Maze) fromClient.readObject();
        //turn the maze into byte Array
        byte[] toByte = m.toByteArray();
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Solution sol=null;
        ASearchingAlgorithm search;
        //sets the search algorithm according to config
        if (Configurations.getInstance().getSearchingAlgorithm().equals("BreadthFirstSearch")){
            search = new BreadthFirstSearch();
        }
        else if(Configurations.getInstance().getSearchingAlgorithm().equals("DepthFirstSearch")){
            search = new DepthFirstSearch();
        }
        else{
            search = new BestFirstSearch();
        }
        //find the solution for this maze with the current search in the directory
        String prefix = "solution "+search.getName()+" "+ m.mazeAsString();
        File dir = new File(tempDirectoryPath);
        File[] candidates = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith(prefix);
            }
        });
        //if we found a file that matches our search
        if(candidates.length>0){
            FileInputStream fileInput = new FileInputStream(candidates[0].getName());
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            //read the solution
            Object o=objectInput.readObject();
            sol=(Solution) o;
        }
        //we didnt saw a maze like this before
        else {
            //solve the maze
            ISearchable searchableMaze = new SearchableMaze(m);
            sol = search.solve(searchableMaze);
            //write the solution to the disk
            File temp = File.createTempFile("solution "+search.getName()+" "+ m.mazeAsString(),"tmp",new File(tempDirectoryPath));
            FileOutputStream file = new FileOutputStream(temp.getName());
            ObjectOutputStream objectOutput = new ObjectOutputStream(file);
            objectOutput.writeObject(sol);
            objectOutput.close();
       }
        //send the solution to the client
        toClient.flush();
        toClient.writeObject(sol);
        toClient.close();
        }
        catch (IOException e){
         e.printStackTrace();
        }
    }
}
