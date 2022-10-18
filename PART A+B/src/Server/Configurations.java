package Server;
import java.io.*;
import java.util.Properties;

/**
 * Class that represents an configuration class
 * the class is designed by the Singelton design pattern
 * the class uses the file config.properties
 */
public class Configurations {
    private  static Configurations instance=null; // the Singelton instance
    private Properties properties;
    private OutputStream os;
    private InputStream is;

    /**
     * Private constructor , we want to create only 1 instance using the getInstance static function
     * creates new Properties object, initialize the input and output streams using the file directory
     */
    private Configurations() {
        properties = new Properties();
        File file = new File("config.properties");
        try {
            os = new FileOutputStream(file);
            is=Configurations.class.getClassLoader().getResourceAsStream(("config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static function for the creation of the Configuration instance if not exist yet
     * make sure only 1 instance exist
     * @return the Configuration instance
     */
    public static Configurations getInstance(){
        if(instance==null){
            instance=new Configurations();
        }
        return instance;
    }

    /**
     * Retunrs the Searching algorithm in the config file
     * @return String, the name of the searching Algorithm we use
     */
    public String getSearchingAlgorithm() {
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("mazeSearchingAlgorithm");
    }
    /**
     * Sets the searchingAlgorithm in the config file
     * @param searchingAlgorithm String represents the searching Algorithm we will use in the solveServerStrategy
     */
    public void setSearchingAlgorithm(String searchingAlgorithm) {
        try {
            properties.setProperty("mazeSearchingAlgorithm", searchingAlgorithm);
            properties.store(os, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(properties);

    }
    /**
     * Retunrs the number of threads in the config file
     * @return int, the number of the threads we will use in the server threadPool
     */
    public int getNumberOfThreads() {
        int ret=0;
        try {
            properties.load(is);
            ret=Integer.parseInt(properties.getProperty("threadPoolSize"));
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Sets the number of threads string in the config file
     * @param numberOfThreads String represents an integer which is number of thread that will be in the threadPool
     */
    public void setNumberOfThreads(String numberOfThreads) {
        try {
            properties.setProperty("threadPoolSize", numberOfThreads );
            properties.store(os, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(properties);
    }
    /**
     * Retunrs the maze generation algorithm in the config file
     * @return String, the name of the maze generation algorithm we use
     */
    public String getGeneratingAlgorithm() {
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("mazeGeneratingAlgorithm");
    }
    /**
     * Sets the maze generations algorithm in the config file
     * @param generateAlgo String represents the maze generations algorithm we will use in the generateServerStrategy
     */
    public void setGeneratingAlgorithm(String generateAlgo) {
        try {
            properties.setProperty("mazeGeneratingAlgorithm", generateAlgo );
            properties.store(os, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(properties);
    }

}





