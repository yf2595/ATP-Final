package algorithms.maze3D;

/**
 * The AMaze3DGenerator abstract class implements IMaze3DGenerator.
 * This abstract class is a base of all 3D maze generator.
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator{
    public long measureAlgorithmTimeMillis(int depth, int row, int column){
        long s=System.currentTimeMillis();
        generate(depth,row,column);
        long e=System.currentTimeMillis();
        return e-s;
    }
}
