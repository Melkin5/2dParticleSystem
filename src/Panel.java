import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;

public class Panel extends JPanel {
    public Grid[][] grid=new Grid[10][20];
    public ArrayList<Particle> particles;
    public Mouse m;
    private Random rand = new Random();
public int screenWidth, screenHeight;
    public Panel() {
        super();
        populateArray();
        this.particles = new ArrayList<Particle>();
        particles.add(new Particle(0,100,0.05,0,2));
        particles.add(new Particle(1030,100,0,0,-2));
        Dimension screenSize = new Dimension(1920, 1080);
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        this.m = new Mouse(this);
        this.addMouseListener(m);
    }
    public void populateArray() {
        int cellWidth = 1900 / grid[0].length; // Width of each cell (20 cells along x-axis)
        int cellHeight = 1030 / grid.length;  // Height of each cell (10 cells along y-axis)

        for (int i = 0; i < grid.length; i++) { // Iterate over rows (y-axis)
            for (int j = 0; j < grid[i].length; j++) { // Iterate over columns (x-axis)
                int x = j * cellWidth; // Top-left x-coordinate of the cell
                int y = i * cellHeight; // Top-left y-coordinate of the cell
                grid[i][j] = new Grid(x, y);
            }
        }
    }
    public void addParticle(int x, int y) {
        particles.add(new Particle(x,y,rand.nextDouble(-0.01,0.01),rand.nextDouble(0,180),rand.nextDouble(-0.5,0.5)));

    }
    public void addParticleToCell(Particle p) {
        if (p.x<0||p.y<0){
            return;
        }
        int x=p.x/1900;int y=p.y/1030;
        grid[x][y].parts.add(p);
        p.g=grid[x][y];
    }
    public void updateGridPos(){
        if (particles.size()==0) {
            return;
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).g==null){
                addParticleToCell(particles.get(i));
                return;
            }
            if (particles.get(i).x>particles.get(i).g.x||particles.get(i).x<particles.get(i).g.x||particles.get(i).y>particles.get(i).g.y||particles.get(i).y<particles.get(i).g.y){
                removeParticleFromCell(particles.get(i));
                addParticleToCell(particles.get(i));
            }
        }
    }
    public void removeParticleFromCell(Particle p) {
        if (p.g!=null) {
            p.g.parts.remove(p);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            // Calculate the top-left corner of the bounding box
            int topLeftX = p.x - p.sizeX / 2;
            int topLeftY = p.y - p.sizeY / 2;

// Draw the oval with the adjusted top-left corner
            g2d.fillOval(topLeftX, topLeftY, p.sizeX, p.sizeY);
        }
        for (int i=0;i<grid.length;i++) {
            for (int j=0;j<grid[i].length;j++) {
                g2d.drawRect(grid[i][j].x, grid[i][j].y, grid[i][j].sizeX, grid[i][j].sizeY);
            }
        }
    }

}
