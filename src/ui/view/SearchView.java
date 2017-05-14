package ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import ui.UI_Constants;
import ui.controller.ViewListener;
import ui.model.SearchResult;

public class SearchView extends JPanel implements UI_Constants, DownloadStateListener{	
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ViewListener viewListener;
		GridBagConstraints gridbagConstraints = new GridBagConstraints();
		JFrame previewFrame=null;
		private JPanel imageGalleryPanel=null;;
		private JPanel nowPlaying=null;
		private SearchResult previousPlayedIcon=null;
		boolean hooverPlayButton=false;
		private HashMap <String, SearchResult> downloadMap=new HashMap<String, SearchResult>();
		int borderThickness=3;
		char [] downloadPercent=new char[2];


		public JPanel buildUI(){
			this.setBorder(BorderFactory.createEtchedBorder());
		//	this.setBackground(new Color(0,0,0,0));
			if(previewFrame==null)
				previewFrame=createPreviewFrame();
			int column=0;
			int row=0;
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JButton searchButton= new JButton("Search ");
			JPanel topPanel=new JPanel();
			topPanel.setLayout(new GridBagLayout());
			
			//1st row
			gridbagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridbagConstraints.gridx = column;
			gridbagConstraints.gridy = row;
			JLabel searchFieldLabel =new JLabel("Media Search");
			topPanel.add(searchFieldLabel,gridbagConstraints);
			
			gridbagConstraints.fill = GridBagConstraints.HORIZONTAL;
			column++;
			gridbagConstraints.gridx = column;
			gridbagConstraints.gridy = row;
			JTextField searchField= new JTextField(20);
			topPanel.add(searchField,gridbagConstraints);
			searchField.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode()==KeyEvent.VK_ENTER){
						searchButton.doClick();
					}
					// TODO Auto-generated method stub
					
				}
			});
			column++;
			//row++;
			gridbagConstraints.gridx = column;
			//c.gridy = row;

			topPanel.add(searchButton,gridbagConstraints);
			searchButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					imageGalleryPanel.removeAll();
					viewListener.update(SEARCH_MEDIA, searchField.getText());
				}				
			});
			add(topPanel);
			imageGalleryPanel=new JPanel();
			//imageGallery.setBackground(new Color(200,200,200,0));
			JScrollPane scrollPane =new JScrollPane(imageGalleryPanel){
 
				private static final long serialVersionUID = 1L;

				@Override
	      public Dimension getPreferredSize() {
	          return new Dimension(950, 500);
	      }
			};

			add(scrollPane);
			return this;	
	}
//***********************************************************************************************
	
	public void setListner(ViewListener listener) {
		
		this.viewListener=listener;
		
	}
//***********************************************************************************************
	
	public void updateImageScrollBar(List<SearchResult> list){
		//System.out.println("Updating panel");

		int column=0;
		int row=0;
		imageGalleryPanel.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.insets = new Insets(20,30,0,0);
		for(SearchResult icon:list){
			c1.gridx = column;
			c1.gridy = row;
			JPanel iconPanel=new JPanel();
			iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.Y_AXIS));	
	    Graphics g = icon.getImageIcon().getImage().getGraphics();
	    g.setFont(g.getFont().deriveFont(10f));
	    g.setColor(Color.BLACK); 
	    g.fillRect(0, 80, 35,15 );
	    g.fillRoundRect(80, 0, 45,30, 10, 10);
	    drawPlayButton(g,Color.white);
	    //URL play=this.getClass().getClassLoader().getResource("playButton.jpg");
	    //Image image = null;
		//	try {
		//		image = ImageIO.read(play);
		//	} catch (IOException e) {
				// TODO Auto-generated catch block
	////			e.printStackTrace();
	//		} 
	//    g.drawImage(image, 95,5, 35, 35, null);
	    g.setColor(Color.LIGHT_GRAY);
	    g.drawString(icon.getAudioLength(), 4, 90);
	    g.dispose();
			JLabel iconLabel=new JLabel(icon.getImageIcon());
			iconLabel.setBorder(BorderFactory.createEmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
	    
			iconLabel.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent e) {
					if(previousPlayedIcon!=icon){
						if(isMousePositionPlayButton(e)){
							Graphics g;
							g = icon.getImageIcon().getImage().getGraphics();
							drawPlayButton(g, Color.gray);
							repaint();
							revalidate();
							g.dispose();
							hooverPlayButton=true;
						}
						else{
							if(hooverPlayButton){
								Graphics g;
								g = icon.getImageIcon().getImage().getGraphics();
								drawPlayButton(g, Color.white);
								repaint();
								revalidate();
								g.dispose();
								hooverPlayButton=true;
							}
						}	
					}
					
				}
				private boolean isMousePositionPlayButton(MouseEvent e) {
					if(e.getX()>79&&e.getY()<26)
						return true;
					return false;
				}
				
				
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			iconLabel.addMouseListener(new MouseListener() {
				
	    	boolean downloading=false;
	    	
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
			
					iconLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if(iconLabel.getName()!="downloading"){
						iconLabel.setBorder(BorderFactory.createEmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
					}
					
					if(hooverPlayButton&&previousPlayedIcon!=icon){
						Graphics g;
						g = icon.getImageIcon().getImage().getGraphics();
						drawPlayButton(g, Color.white);
						repaint();
						revalidate();
						g.dispose();
						hooverPlayButton=false;
					}

				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
						if(iconLabel.getName()!="downloading"){
							iconLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
							iconLabel.setToolTipText("download");
							iconLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN,borderThickness ));
						}		
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(isMousePositionPlayButton(e)){
						Graphics g;
						if(previousPlayedIcon!=null){
							g = previousPlayedIcon.getImageIcon().getImage().getGraphics();
							drawPlayButton(g, Color.white);
							g.dispose();
						}

						previousPlayedIcon=icon;
						g = icon.getImageIcon().getImage().getGraphics();
						drawPlayButton(g, Color.green);
						repaint();
						revalidate();
						g.dispose();
						if(nowPlaying!=null)
							previewFrame.remove(nowPlaying);
						if(!previewFrame.isShowing())
							previewFrame.setVisible(true);
						nowPlaying=getBrowserPanel(icon.getVideoId());
						previewFrame.getContentPane().add(nowPlaying, BorderLayout.CENTER);
			
					}

					else if(iconLabel.getName()!="downloading"){
						iconLabel.setName("downloading");
						iconLabel.setBorder(BorderFactory.createLineBorder(Color.ORANGE,borderThickness ));
						//downloading=true;
						String path="https://www.youtube.com/watch?v="+icon.getVideoId();
						System.out.println("path is "+path);
						viewListener.update(CREATE_SONG, path);
						downloadMap.put(path, icon);
						icon.setJlabel(iconLabel);
					}
				}
				private boolean isMousePositionPlayButton(MouseEvent e) {
					if(e.getX()>79&&e.getY()<26)
						return true;
					return false;
				}
			});

			int length=icon.getParsedTitle().length();
			if(length>23)
				length=23;
			JLabel titleLabel=new JLabel(icon.getParsedTitle().substring(0, length));
			titleLabel.setToolTipText(icon.getFullTitle());
			//titleLabel.setText(icon.getTitle());
			iconPanel.add(iconLabel);
			iconPanel.add(titleLabel);
			imageGalleryPanel.add(iconPanel,c1);
			column++;
			if(column>4){
				row++;
				column=0;
			}
		}

		repaint();
		revalidate();
	}
	
//***********************************************************************************************
	private void drawPlayButton(Graphics g, Color color){
    g.setColor(color);
    int[] xPoints={98,98,112};
    int[] yPoints={10,20,15};
    g.fillPolygon(xPoints, yPoints, 3);
    
	}
	
//***********************************************************************************************
	JFrame createPreviewFrame(){
    NativeInterface.open();
    JFrame frame = new JFrame("YouTube Viewer");
    
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
        		frame.addWindowListener(new WindowListener() {
							
							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowIconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowDeiconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowClosing(WindowEvent e) {

								if(nowPlaying!=null)
									previewFrame.remove(nowPlaying);
								
							}
							
							@Override
							public void windowClosed(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
						});
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            //frame.getContentPane().add(getBrowserPanel(), BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setLocationByPlatform(true);
            frame.setVisible(false);
        }
    });
    NativeInterface.runEventPump();
    // don't forget to properly close native components
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        @Override
        public void run() {
            NativeInterface.close();
        }
    }));
    return frame;
	}
//***********************************************************************************************
	public static JPanel getBrowserPanel(String videoId) {
    JPanel webBrowserPanel = new JPanel(new BorderLayout());
    JWebBrowser webBrowser = new JWebBrowser();
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
    webBrowser.setBarsVisible(false);
    webBrowser.navigate("https://www.youtube.com/watch?v="+videoId);
    return webBrowserPanel;
	}


	@Override
	public void downloadComplete(String id) {
		downloadMap.get(id).getLabel().setBorder(BorderFactory.createEmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
		downloadMap.get(id).getLabel().setName("");
	}

	@Override
	public void downloadUpdate(String id, int percentDownload) {
		Graphics g=downloadMap.get(id).getImageIcon().getImage().getGraphics();
		System.out.println("Updating download graphics "+percentDownload+" graphics is null: "+(g==null));
		String downloadPercent=String.valueOf(percentDownload);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(downloadPercent, 80, 90);
		repaint();
		revalidate();
    g.dispose();

		
	}
	
}
