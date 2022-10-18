package IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
/**
 * Class that represents an byte decompressor that decompress the bytes in the inputStream to a byte array that represents an 2D maze
 */
public class MyDecompressorInputStream extends InputStream {
    InputStream in;
    /**
     * Class constructor
     * sets the InputStream attribute
     * @param in An InputStream
     */
    public MyDecompressorInputStream(InputStream in) {

        this.in = in;
    }

    /**
     * An helper function that convert an integer into binary representation
     * can add 0 to the beginning of the number if needed
     * @param num the number we want to turn to binary
     * @param size the size of the number in number of digits in it
     * @return the binary representation of the number
     */
    public String convertBinary(int num, int size) {
        String binary = Integer.toBinaryString(num);
        int sizeBinary = binary.length();
        String ret = "";
        for (int i = 0; i < size - sizeBinary; i++) {
            ret = ret + '0';
        }
        ret = ret + binary;
        return ret;
    }

    /**
     * Function that reads the bytes from the input stream in attribute
     * the function first reads the first 16 bytes which represent the maze size, and the positions
     * and then convert all bytes to binary number (radix 2) until we reach the -1 mark.
     * after -1 sign have a size binary number of the last chunk, followed by the -1 sign again.
     * after that the last chunk.
     * @param b Array of bytes represent a Maze that we are writing to
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException {
        byte[] a = in.readAllBytes();
        int indexA = 0; //indexA- the index we currently in a array.
        while (indexA < 16) { //the first 16 bytes is the meta data - maze size and the Positions
            b[indexA] = a[indexA];
            indexA++;
        }
        int indexB = indexA; //indexB- the index we currently in b array.
        while (a[indexA] != -1) { //Until we reach the -1 mark.
            if (indexA == a.length) return indexB; //There are no more numbers in A array.
            int integer = a[indexA++];
            String temp = convertBinary(integer, 7); //convert from integer to binary number with size 7.
            for (int i = 0; i < temp.length(); i++) { //Insert each char (from the string that holding the binary namber) to b array.
                int charInt = temp.charAt(i);
                if (charInt == 48) { //The char 48 in ascii is the number 0.
                    b[indexB] = 0;
                } else { //Else is the number 1.
                    b[indexB] = 1;
                }
                indexB++;
            }
        }
        indexA++; //Skips the mark -1
        int num = a[indexA++]; //This is the size of the last number.
        indexA++; //Skips the mark -1
        int integer = a[indexA++]; //This is the last number. It is written as an integer.

        String temp = convertBinary(integer, num); //convert from integer to binary number with size num.
        for (int i = 0; i < temp.length(); i++) { //Insert each char (from the string that holding the binary namber) to b array.
            int charInt = temp.charAt(i);
            if (charInt == 48) { //The char 48 in ascii is the number 0.
                b[indexB] = 0;
            } else { //Else is the number 1.
                b[indexB] = 1;
            }
            indexB++;
        }
        indexA++;
        return indexB;
    }
    @Override
    public int read() throws IOException {
        return 0;
    }
}
