package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface that represents server strategy
 * each server strategy has to implement the applyStrategy function
 */
public interface IServerStrategy {
    void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException;

}
