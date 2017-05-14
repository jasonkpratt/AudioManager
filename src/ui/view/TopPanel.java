package ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TopPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image img;

	public JPanel buildUI(){
		//this.setBackground(Color.black);
		img = null;
	//	try {
		  //  img = ImageIO.read(new File("resource/stock.jpg"));
		//} catch (IOException e) {
	////		e.printStackTrace();
	//	}
		TabbedPanel tabbedPanel=new TabbedPanel();
		add(tabbedPanel.buildUI());
		return this;
	}
	
////	@Override
 // protected void paintComponent(Graphics g) {

 //   super.paintComponent(g);
      //  g.drawImage(img, 0, 0, null);
//}
	//*********************************************************************
}
