package energy_converter;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

class Panel extends JPanel
{
    public BufferedImage image;

    public Panel(int width,int height) {
        super();
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }

    public void draw()
     {
        this.repaint();
    }
}