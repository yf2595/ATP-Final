package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class represnets an Simple decompressor that using an inputStream to write a sequence of bytes that represents a maze
 */
public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;

    /**
     * Class constructor
     * @param in An input stream
     */
    public SimpleDecompressorInputStream(InputStream in) {

        this.in = in;
    }
    /**
     * Function that reads the bytes from the input stream in attribute
     * the function first reads the first 16 bytes which represent the maze size, and the positions
     * and then write 0 or 1 as a sequence according to the current byte we read
     * @param b Array of bytes represent a Maze that we are writing to
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException {
        byte[] a=in.readAllBytes();
        int index=0;
        while(index<16){ //the first 16 bytes is the meta data - maze size and the Positions
            b[index]=a[index];
            index++;
        }
        byte curr=0; // the byte we will write to the b param
        int count=index; //continue from where we finished the meta data while loop
        for(int i=index;i< a.length;i++){
            int num=a[i]; //the current byte we read from the input stream
            if(num<0){
                num = 256+num; // change from byte to unsigned byte
            }
            for(int j=0;j<num;j++){
                b[count++]=curr; //write the number of times the curr var is repeating
            }
            //change curr from 0 to 1
            if(curr==0){
                curr=1;
            }
            //change curr from 1 to 0
            else{
                curr=0;
            }
        }
        //return the number of bytes we read from the input stream
        return count;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
