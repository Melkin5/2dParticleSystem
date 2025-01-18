import java.util.ArrayList;

/**
 * @author Matthew Elkin
 * This class acts as a grid square which contains an array of particles within certain coordinate bounds
 * in order to reduce computational load for collison detection
 */
public class Grid {
    public int x,y;//top left
    //top right =(x+sizeX,y)
    //bottom left =(x,y+sizeY) //bottom right =(x+sizeX.y+sizeY)
    public final int sizeX=95;
    public final int sizeY=103;
    public ArrayList<Particle> parts;
    public Grid(int x,int y) {
        this.parts=new ArrayList<Particle>();
        this.x=x;
        this.y=y;
    }
}
