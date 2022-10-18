package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface that represents client strategy
 * each client strategy has to implement the clientStrategy function
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}

