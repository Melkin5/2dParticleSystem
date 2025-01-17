import java.util.ArrayList;

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
