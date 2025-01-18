import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Mouse 
 */
public class Mouse implements MouseListener {
    public Panel p;
    public Mouse(Panel p) {
       this.p=p;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
         p.addParticle(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used
    }


}
