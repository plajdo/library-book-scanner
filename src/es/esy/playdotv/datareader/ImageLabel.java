package es.esy.playdotv.datareader;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ImageLabel extends JLabel{
	private ImageIcon img = null;
	
	public ImageLabel(ImageIcon img){
		super();
		this.img = img;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

}
