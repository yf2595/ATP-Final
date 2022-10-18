package Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Class that represents a media player instance that used in the model class.
 */
public class MyMusic {
    MediaPlayer mp;
    Media m;
    String path;

    /**
     * Class constructor.
     * initialize new media instance using the path.
     * initialize new media player instance.
     * @param s the path to the sound file.
     */
    public MyMusic(String s){
        path=new File((s)).getAbsolutePath();
        try {
            m=new Media(getClass().getResource(s).toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mp= new MediaPlayer(m);
    }

    /**
     * play the media player.
     */
    public void play(){
        mp.play();
    }
    /**
     * stop the media player.
     */
    public void stop(){
        mp.stop();
    }
    /**
     * pause the media player.
     */
    public void pause(){mp.pause();}

}
