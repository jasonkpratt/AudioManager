package ui.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ui.controller.UIController;

public class TabbedPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static TabbedPanel thePanel=null;
	
	private TabbedPanel(){
		super(LEFT);
		
	}
	
	public static TabbedPanel getInstance(){
		if(thePanel==null){
			thePanel=new TabbedPanel();
		}
		return thePanel;
	}

	public TabbedPanel buildUI(){
	
		SearchView searchView	=	new SearchView();
		TreeView treeView 		= new TreeView();
		CleanUpView cleanUpView=new CleanUpView();
		ConverterView converterView = new ConverterView();
		ManualView manualView=new ManualView();
		
		UIController controller=new UIController(searchView,treeView,cleanUpView,converterView,manualView);
		controller.addDownloadStateListeners(searchView);
		searchView.setListner(controller);
		manualView.setListner(controller);
		
		ImageIcon optionsIcon=createImageIcon("/options.png");
		ImageIcon downloadIcon=createImageIcon("/download.jpg");
		ImageIcon playlistIcon=createImageIcon("/playlist.jpg");
		ImageIcon directoryIcon=createImageIcon("/directory.png");
		ImageIcon manualIcon=createImageIcon("/manual.jpg");
		ImageIcon convertIcon=createImageIcon("/convert.png");
		ImageIcon cleanIcon=createImageIcon("/clean.jpg");
	
		addTab("", downloadIcon,searchView.buildUI(),"Download media");
		addTab("",manualIcon, manualView.buildUI(),"Manual enter link to download.");
		addTab("",directoryIcon, treeView,"View music directory");
		addTab("",cleanIcon, cleanUpView,"Clean music directory, remove duplicates, renames directory...");
		addTab("", convertIcon,converterView,"Convert media format.");	
		addTab("",playlistIcon,null,"History of downloaded songs");
		addTab(null,optionsIcon, null,"Set up application");
		return this;
	}
	
//********************************************************************************************************** 
	
  /** Returns an ImageIcon, or null if the path was invalid. */
  protected ImageIcon createImageIcon(String path) {
      java.net.URL imgURL = TabbedPanel.class.getResource(path);
      if (imgURL != null) {
      	ImageIcon icon=new ImageIcon(imgURL);
          return new ImageIcon(getScaledImage(icon.getImage(), 100,65));
      } else {
          System.err.println("Couldn't find file: " + path);
          return null;
      }
  }
  
//********************************************************************************************************** 
	
		private Image getScaledImage(Image srcImg, int w, int h){
      BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2 = resizedImg.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.drawImage(srcImg, 0, 0, w, h, null);
      g2.dispose();
      return resizedImg;
  }
//********************************************************************************************************** 	
	
}
