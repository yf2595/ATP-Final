package Server;
import Server.IServerStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class represents a Server
 * each server has a strategy and threadPool
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;

    /**
     * Class constructor, initializing a new ThreadPool and updated all the attributes
     * @param port integer, servers port
     * @param listeningIntervalMS integer, server listing interval in milliseconds
     * @param strategy the server strategy
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        stop = false;
        threadPool= Executors.newFixedThreadPool(Configurations.getInstance().getNumberOfThreads());//using the configuration singelton instance

    }

    /**
     * Creates a new thread that runs the server
     * Starts the thread
     */
    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    /**
     * Function that starts a new socket, accept new client and send it to the threadPool
     * only if attribute stop is true the server will now accept new clients
     * if attribute stop is true we will close the threadPool after the threadPool threads will be terminated
     */
    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop) { // accept new clients and sent them to the threadPool
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.execute(()-> handleClient(clientSocket));
                }
                catch (SocketTimeoutException e) {
                }
            }
            threadPool.shutdown(); // close the threadPool in a safe way
            try {
                threadPool.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverSocket.close(); // close the server
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Signal to the server to stop - no new client and close the threadPool if all the thread has benn terminated
     */
    public void stop() {

        stop = true;
    }

    /**
     * Applying the server strategy with the clients sockets input and output streams
     * @param clientSocket the Client socket
     */
    public void handleClient(Socket clientSocket) {
        try {
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();
            strategy.applyStrategy(inFromClient, outToClient);
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
