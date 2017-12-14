package es.esy.playdotv.gui.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TheDesktopPane extends JDesktopPane
{
    
    BufferedImage backgroundImage;
    
    public TheDesktopPane()
    {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("\\res\\cyka.png"));
        } catch(IOException|IllegalArgumentException e)
        {
            System.err.println("Couldn't load background.");
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        if (backgroundImage!=null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    
}
