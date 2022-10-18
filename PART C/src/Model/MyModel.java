package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import algorithms.mazeGenerators.Maze;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.search.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Model class which represent the business logic of our GUI program.
 * this class is observed by the ViewModel
 * this class generating the mazes and solving them using specific servers.
 * this class moves the character, and responsible for save and load operations.
 * this class responsible for the music.
 */
public class MyModel extends Observable implements IModel{

    private Maze maze;
    private int rowChar;
    private int colChar;
    private int rows;
    private int cols;
    private Solution sol;
    private MyMusic ending;
    private MyMusic mainSound;
    private MyMusic start;
    private MyMusic HAHAHA;
    private boolean soundOn;
    private Victors victors;
    Server mazeGeneratingServer;
    Server solveSearchProblemServer;
    private Integer score;
    private final Logger log= LogManager.getLogger(MyModel.class);

    /**
     * Class constructor.
     * initialize and statring the servers for generating and solving the mazes.
     * initialize the music and score components .
     */
    public MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        maze = null;
        rowChar =0;
        colChar =0;
        sol=null;
        soundOn=true;
        score=0;
        ending=new MyMusic("/Clips/Pikachu ending.mp3");

        start=new MyMusic("/Clips/Pikachu Sound.mp3");

        mainSound=new MyMusic("/Clips/Pokemon Theme Song.mp3");

        HAHAHA =new  MyMusic("/Clips/Evil-Laugh.mp3");

        mainSound.play();
        log.info("opened GUI");
        log.info("Playing music");
        log.info("Maze Generating server started listening for clients at port 5400");
        log.info("Solving maze server started listening for clients at port 5401");
    }
    /**
     * Asssing the observer o to class Observers list.
     */
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    /**
     * Saving the maze to the directory chosen by the user.
     * save the maze as maze file with .maze suffix.
     * using log instance to record the saving.
     */
    public void save() throws IOException {
        //get the path and then save
        Stage newStage = new Stage();
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Maze");
        fc.setInitialFileName("myMaze");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Maze files(.maze)", ".maze");
        fc.getExtensionFilters().add(extensionFilter);
        File toSave = fc.showSaveDialog(newStage);
        if(toSave != null){
            FileOutputStream fileOutputStream = new FileOutputStream(toSave.getPath());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.flush();
            objectOutputStream.writeObject(maze);
            log.info("maze saved to "+toSave.getPath());
            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();
        }
    }
    /**
     * Loading the maze from the directory chosen by the user.
     * using log instance to record the loading.
     */
    public void load() throws IOException, ClassNotFoundException {
        //get the path and then load and draw
        Stage newStage = new Stage();
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)","*.maze"));
        File chosen = fc.showOpenDialog(newStage);
        if(chosen != null){
            loadGame(chosen.getPath());
        }
        else {
            log.error("unable to load the maze.");
            throw new IOException();

        }
    }

    /**
     * Loading the maze from the directory chosen by the user using ObjectStream.
     * using log instance to record the loading.
     * setting the new maze and his solution and updating the observes.
     * starts the music from beginning.
     */
    public void loadGame(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInput = new FileInputStream(path);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        Object o = objectInput.readObject();
        Maze mazeLoad = (Maze) o;
        objectInput.close();
        fileInput.close();
        setMaze(mazeLoad);
        solveMaze();
        log.info("maze loaded from "+path);
        setChanged();
        notifyObservers("maze loaded");
        MusicPlay();

    }
    /**
     * Returns the victory table if player saved his score.
     * @return Victors object.
     */
    public Victors getVictors() {
        if(victors!=null) {
            return victors;
        }
        else {
            return null;
        }
    }
    /**
     * Saving the player score into the victory table.
     * @param :string name - the players name.
     * @param :int scoreSave - players score.
     * the functions check if there is a victory table with the same maze.
     * if exist then saving the players info.
     * else, creating a new victory table with the maze and saving the player info/
     */
    public void saveScore(String name, int scoreSave) throws IOException, ClassNotFoundException {
        byte[] toByte = maze.toByteArray();
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        String prefix = "VictorTables " + Arrays.toString(toByte).hashCode();
        File dir = new File(tempDirectoryPath);
        File[] candidates = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().startsWith(prefix);
            }
        });
        if (candidates.length > 0) {
            String path = candidates[0].getPath();
            FileInputStream fileInput = null;
            fileInput = new FileInputStream(path);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Object o = objectInput.readObject();
            Victors v = (Victors) o;
            v.addVictor(name, scoreSave);
            objectInput.close();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.reset();
            objectOutputStream.flush();
            objectOutputStream.writeObject(v);
            objectOutputStream.flush();
            objectOutputStream.close();
            log.info("saved score for "+name+".");
            victors = v;

        }
        else{
            Victors v = new Victors(maze);
            v.addVictor(name,scoreSave);
            File temp = File.createTempFile(prefix, ".victor", dir);
            FileOutputStream file = new FileOutputStream(new File(dir, temp.getName()));
            ObjectOutputStream objectOutput = new ObjectOutputStream(file);
            objectOutput.writeObject(v);
            file.close();
            objectOutput.close();
            log.info("saved score for "+name+".");
            victors = v;

        }
        setChanged();
        notifyObservers("score saved");
    }

    /**
     * return the current score from the game.
     * @return Integer - the current score from the game.
     */
    public Integer getScore()
    {
        return score;
    }

    /**
     * resets the current score to 0.
     * notify all observes.
     */
    public void ResetScore() {
        this.score = 0;
        setChanged();
        notifyObservers("score changed");
    }

    /**
     * updating players location and score according to the movement he done.
     * the function checks if the move is valid.
     * @param direction int variable to describe the movement.
     */
    public void updateCharacterLocation(int direction)
    {
        switch(direction)
        {
            case 1: //Down-Left
                if(rowChar!=rows-1 && colChar !=0) {
                    if (maze.getCellvalue(rowChar + 1, colChar - 1) == 0) {
                        if(maze.getCellvalue(rowChar+1,colChar)==0 || maze.getCellvalue(rowChar,colChar-1)==0) {
                            rowChar++;
                            colChar--;
                            setChanged();
                            notifyObservers("char moved");
                            score=score+3;
                            setChanged();
                            notifyObservers("score changed");
                        }
                    }
                }
                break;

            case 2: //Down
                if(rowChar!=rows-1){
                    if(maze.getCellvalue(rowChar+1,colChar)==0){
                        rowChar++;
                        setChanged();
                        notifyObservers("char moved");
                        score=score+2;
                        setChanged();
                        notifyObservers("score changed");
                    }
                }
                break;

            case 3: //Down-Right
                if(rowChar!=rows-1 && colChar !=cols-1){
                    if(maze.getCellvalue(rowChar+1,colChar+1)==0){
                        if(maze.getCellvalue(rowChar+1,colChar)==0 || maze.getCellvalue(rowChar,colChar+1)==0){
                            colChar++;
                            rowChar++;
                            setChanged();
                            notifyObservers("char moved");
                            score=score+3;
                            setChanged();
                            notifyObservers("score changed");
                        }

                    }
                }
                break;
            case 4: //Left
                if(colChar!=0){
                    if(maze.getCellvalue(rowChar,colChar-1)==0){
                        colChar--;
                        setChanged();
                        notifyObservers("char moved");
                        score=score+2;
                        setChanged();
                        notifyObservers("score changed");
                    }
                }
                break;
            case 6: //Right
                if(colChar!=cols-1){
                    if(maze.getCellvalue(rowChar,colChar+1)==0) {
                        colChar++;
                        setChanged();
                        notifyObservers("char moved");
                        score = score+2;
                        setChanged();
                        notifyObservers("score changed");
                    }
                }
                break;

            case 7: //Up-Left
                if(rowChar!=0 && colChar!=0){
                    if(maze.getCellvalue(rowChar-1,colChar-1)==0){
                        if(maze.getCellvalue(rowChar-1,colChar)==0 || maze.getCellvalue(rowChar,colChar-1)==0) {
                            rowChar--;
                            colChar--;
                            setChanged();
                            notifyObservers("char moved");
                            score=score+3;
                            setChanged();
                            notifyObservers("score changed");
                        }
                    }
                }
                break;
            case 8: //Up
                if(rowChar!=0){
                    if(maze.getCellvalue(rowChar-1,colChar)==0){
                        rowChar--;
                        setChanged();
                        notifyObservers("char moved");
                        score=score+2;
                        setChanged();
                        notifyObservers("score changed");
                    }
                }
                break;
            case 9: //Up-Right
                if(rowChar !=0 && colChar!= cols-1){
                    if(maze.getCellvalue(rowChar-1,colChar+1)==0){
                        if(maze.getCellvalue(rowChar-1,colChar)==0 || maze.getCellvalue(rowChar,colChar+1)==0) {
                            colChar++;
                            rowChar--;
                            setChanged();
                            notifyObservers("char moved");
                            score=score+3;
                            setChanged();
                            notifyObservers("score changed");
                        }
                    }
                }
                break;
        }
        if(maze.getGoalPosition().getRowIndex() == rowChar && maze.getGoalPosition().getColumnIndex() == colChar){
            setChanged();
            notifyObservers("finish");
        }

    }

    /**
     * returns the character row index.
     * @return int rowChar - players row index.
     */
    public int getRowChar() {
        return rowChar;
    }
    /**
     * returns the character column index.
     * @return int colChar - players column index.
     */
    public int getColChar() {
        return colChar;
    }

    /**
     * play the show solution sound.
     * stops the main sound and resume playing after HAHAHA sound finish.
     */
    public void playHAHAHA(){
        if(soundOn){
            mainSound.pause();
            HAHAHA.play();
            HAHAHA.mp.setOnEndOfMedia(() -> {
                HAHAHA.stop();
                mainSound.play();
            });
        }
    }

    /**
     * initialize a new client instance that requests solbing the current maze using the solving maze server.
     * using log instance to record the client request and the server work.
     * @throws IOException
     */
    @Override
    public void solveMaze() throws IOException{
        Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
            public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                try {
                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                    toServer.flush();
                    toServer.writeObject(maze);
                    toServer.flush();
                    sol = (Solution)fromServer.readObject();
                } catch (Exception e) {
                    sol=null;
                } }});
        client.communicateWithServer();
        if(sol==null){
            log.error("MazeSolvingServer was Unable to provide solution for maze{"+maze.getRows()+","+maze.getColumns()+"} requested by client:"+InetAddress.getByName("127.0.0.1").toString() +" at port: 5401");
            throw new IOException();
        }
        log.info("MazeSolvingServer found solution for maze{"+maze.getRows()+","+maze.getColumns()+"} requested by client:"+InetAddress.getByName("127.0.0.1").toString() +" at port: 5401");
        setChanged();
        notifyObservers("maze solved");
    }

    /**
     * function that activates when player finished the maze.
     * playing solved music.
     * using the log instance to record the this operation.
     */
    public void finished() {
        log.info("Player finished maze{"+maze.getRows()+","+maze.getColumns()+"}");
        if(soundOn){
            mainSound.pause();
            ending.play();
            ending.mp.setOnEndOfMedia(() -> {
                ending.stop();
                mainSound.play();
            });
        }
    }

    /**
     * playing the start sound if soundOn property is true.
     */
    public void MusicPlay(){
        if(soundOn){
            mainSound.pause();
            start.play();
            start.mp.setOnEndOfMedia(() -> {
                start.stop();
                mainSound.play();
            });
        }
        if(soundOn){
            start.play();
        }
    }
    /**
     * changing the soundOn property.
     * if true - music will play or resume if stopped before.
     * else - will stop.
     */
    public void soundProperties(){
        if(soundOn){
            soundOn=false;
            ending.stop();
            start.stop();
            HAHAHA.stop();
            mainSound.pause();
            log.info("Stopped the music");
        }
        else{
            soundOn=true;
            mainSound.play();
            log.info("Started music");

        }
    }

    /**
     * returns the current maze solution.
     * @return maze solution.
     */
    @Override
    public Solution getSolution() {
        return this.sol;
    }

    /**
     * setting maze properties if new maze was generated or loaded.
     * @param m - the new Maze instance.
     */
    public void setMaze(Maze m){
        maze=m;
        rows=m.getRows();
        cols=m.getColumns();
        rowChar =m.getStartPosition().getRowIndex();
        colChar =m.getStartPosition().getColumnIndex();
        sol=null;
    }

    /**
     * close all the servers if user request to exit the gui.
     */
    public void closeServers(){
        log.debug("Servers closed");
        solveSearchProblemServer.stop();
        mazeGeneratingServer.stop();
        log.debug("GUI closed");
        setChanged();
        notifyObservers("servers closed");
    }

    /**
     * initialize a new client instance that requests to generate a new maze instance with the row and column.
     * using log instance to record the client request and the server work.
     * @param row - numbers of rows in the new maze.
     * @param col - number of columns in the new maze.
     * @throws IOException
     */
    public void generateMaze(int row, int col) throws IOException
    {
        Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
            public void clientStrategy(InputStream inFromServer, OutputStream outToServer)  {
                try{
                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                toServer.flush();
                int[] mazeDimensions = new int[]{row, col};
                toServer.writeObject(mazeDimensions);
                toServer.flush();
                byte[] compressedMaze = (byte[])fromServer.readObject();
                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                byte[] decompressedMaze = new byte[1000000];
                is.read(decompressedMaze);
                maze = new Maze(decompressedMaze);
                }
                catch (Exception e){
                    maze=null;
                }
            }});
        client.communicateWithServer();
        if(maze!=null){
            rowChar=maze.getStartPosition().getRowIndex();
            colChar=maze.getStartPosition().getColumnIndex();
            rows=maze.getRows();
            cols=maze.getColumns();
            log.info("MazeGeneratingServer generates maze{"+row+","+col+"} for client:"+InetAddress.getByName("127.0.0.1").toString() +" at port: 5400");
            setChanged();
            notifyObservers("generate maze");
        }
        else{
            log.error("Server was Unable to generate maze{"+row+","+col+"} for client:"+InetAddress.getByName("127.0.0.1").toString() +" at port: 5400");
            throw new IOException();
        }
    }

    /**
     * return the maze.
     * @return Maze.
     */
    public Maze getMaze() {
        return maze;
    }

}
