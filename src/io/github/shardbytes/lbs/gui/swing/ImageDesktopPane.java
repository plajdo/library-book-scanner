package io.github.shardbytes.lbs.gui.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageDesktopPane extends JDesktopPane
{
	private static final long serialVersionUID = 1L;
	BufferedImage backgroundImage;
    
    public ImageDesktopPane()
    {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/background.jpg"));
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
