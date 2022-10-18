package Server;

import IO.MyCompressorOutputStream;
import IO.SimpleCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.*;

/**
 * Class that implement a strategy that allowing the server to generate a maze according to the clients
 * size and send back to the client a compressed byte array that represents a maze
 */
public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            //read the object from the client
            int[] size = (int[]) fromClient.readObject();
            //generate the maze according to the client data
            AMazeGenerator mazeGenerator;
            if (Configurations.getInstance().getGeneratingAlgorithm().equals("EmptyMazeGenerator")){
                mazeGenerator = new EmptyMazeGenerator();
            }
            else if(Configurations.getInstance().getGeneratingAlgorithm().equals("SimpleMazeGenerator")){
                mazeGenerator = new SimpleMazeGenerator();
            }
            else{
                mazeGenerator = new MyMazeGenerator();
            }
            Maze maze = mazeGenerator.generate(size[0], size[1]);

            //compress the maze using myCompressor
            ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream();
            OutputStream out = new MyCompressorOutputStream(byteArrOS);
            out.write(maze.toByteArray());
            out.flush();

            //send the compressed maze to the client
            toClient.writeObject(byteArrOS.toByteArray());
            toClient.flush();
            out.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
