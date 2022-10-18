package IO;

import algorithms.mazeGenerators.Maze;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Class that represents an byte compressor that compress an maze byte array using outputStream
 */
public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;

    /**
     * Class constructor
     * sets the outputStream attribute
     * @param o An outputStream
     */
    public MyCompressorOutputStream(OutputStream o) {
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
     * Every chunk of 7 (binary) numbers convert to byte (0-125) by convert to 2 radix.
     * @param bytes
     * @throws IOException
     */
    public void write(byte[] bytes) throws IOException {
        int index=0;
        int integer;
        while(index<16){ //index 0-15 is the size of the maze and the positions
            out.write(bytes[index]);
            index++;
        }
        int length = bytes.length;
        int count = 0;
        while(length - 16 - count >= 7){
            String binaryString = "";
            for(int i=0; i<7; i++){ //Collects 7 numbers and inserts into a string
                binaryString = binaryString + bytes[index++];
                count++;
            }
            integer = Integer.parseInt(binaryString,2); //Convert the string (chunk of 7 binary numbers) to decimal.
            out.write((byte)integer);
        }
        if(length-16-count > 0){ //Checks if there are any more numbers left
            out.write(-1); // A sign after it will write the amount of numbers left.
            out.write(length-16-count); // The amount of numbers left.
            out.write(-1); // A sign that we have finished writing the amount of numbers left.
            String binaryString = "";
            int countTemp = count;
            for(int i=0; i<length-16-countTemp; i++){ //Collects all numbers that left and inserts into a string
                binaryString = binaryString + bytes[index++];
                count++;
            }
            integer = Integer.parseInt(binaryString,2); //Convert the string (chunk of all binary numbers that left numbers) to decimal.
            out.write((byte)integer);

        }
        out.flush();


    }
}
