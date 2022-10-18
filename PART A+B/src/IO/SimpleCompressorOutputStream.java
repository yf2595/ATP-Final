package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class that represents an byte compressor that compress an maze byte array using outputStream
 */
public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;
    /**
     * Class constructor
     * sets the outputStream attribute
     * @param o An outputStream
     */
    public SimpleCompressorOutputStream(OutputStream o) {

        out=o;
    }
    /**
     * An override function for the outputStream class
     * writes the param b using the out attribute
     * @param b
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
    /**
     * Function that writes the byte array parameter using the out.write function
     * the function first writes the first 16 bytes which represent the maze size, and the positions
     * and then count the number of 1 or 0 that comes consecutively
     * if reaches to 255 in some count, we will write 255, then 0 and then continue the count
     * @param bytes Array of bytes represent a Maze
     * @throws IOException
     */
    public void write(byte[] bytes) throws IOException {
        int index=0;
        while(index<16){ //the maze meta data - size, positions
            out.write(bytes[index]);
            index++;
        }
        int now=0; // the number we want to count - can be 0 or 1
        int count=0; //counter for the now integer
        if(bytes[index]==0){ // the first byte is 0
            now=0;
            count++;
            index++;
        }
        else{ // the first byte is 1
            out.write(0);
            now=1;
        }
        for(int i=index;i<bytes.length;i++){
            if(count==255 && bytes[i]==now){ //if we already counted 255 and we need to continue
                out.write(count);
                out.write(0);
                count=0;
            }
            if(bytes[i]==now){ // if we are in the middle of the count
                count++;
            }
            else{ // we saw a different byte
                out.write(count);
                now=bytes[i];
                count=1;
            }
        }
        out.write(count); // write the last count
        out.flush();
    }
}
