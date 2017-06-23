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
import ui.model.SearchResultData;
import ui.view.IconLabel.LabelEvent;

public class SearchView extends JPanel implements UI_Constants, DownloadStateListener, IconLabelListener{	
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ViewListener viewListener;
		GridBagConstraints gridbagConstraints = new GridBagConstraints();
		JFrame previewFrame=null;
		private JPanel imageGalleryPanel=null;;
		private JPanel youTubePanel=null;
		private SearchResultData previousPlayedIcon=null;
		//boolean hooverPlayButton=false;
		private HashMap <String, SearchResultData> downloadMap=new HashMap<String, SearchResultData>();
		//int borderThickness=3;
		char [] downloadPercent=new char[2];


		public JPanel buildUI(){
			this.setBorder(BorderFactory.createEtchedBorder());
		//	this.setBackground(new Color(0,0,0,0));
			if(previewFrame==null)
				previewFrame=createPreviewFrame();
			int column=0;
			int row=0;
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JButton searchButton= new JButton("Get it!");
			JPanel topPanel=new JPanel();
			topPanel.setLayout(new GridBagLayout());
			
			//1st row
			gridbagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridbagConstraints.gridx = column;
			gridbagConstraints.gridy = row;
			JLabel searchFieldLabel =new JLabel("Media Search: ");
			topPanel.add(searchFieldLabel,gridbagConstraints);
			
			gridbagConstraints.fill = GridBagConstraints.HORIZONTAL;
			column++;
			gridbagConstraints.gridx = column;
			gridbagConstraints.gridy = row;
			JTextField searchField= new JTextField(20);
			searchField.setToolTipText("Search for artists,bands, videos...");
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
	
	public void updateImageScrollBar(List<SearchResultData> list){
		//System.out.println("Updating panel");

		int column=0;
		int row=0;
		imageGalleryPanel.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.insets = new Insets(20,30,0,0);
		
		//if a download is occurring, add icon to image gallery
		for(SearchResultData icon:downloadMap.values()){
			list.add(0,icon);
		}
		for(SearchResultData icon:list){
			c1.gridx = column;
			c1.gridy = row;
			IconPanel iconPanel=new IconPanel(icon);
			iconPanel.addLabel();
			icon.getLabel().setLabelListener(this);
			int length=icon.getParsedTitle().length();
			if(length>23)
				length=23;
			JLabel titleLabel=new JLabel(icon.getParsedTitle().substring(0, length));
			titleLabel.setToolTipText(icon.getFullTitle());
			//titleLabel.setText(icon.getTitle());
			//iconPanel.add(iconLabel);
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

								if(youTubePanel!=null)
									previewFrame.remove(youTubePanel);
								
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
//***********************************************************************************************

	//todo these methods below should be in a state manager class
	@Override
	public void downloadComplete(String id) {
		downloadMap.get(id).getLabel().setLabelState(LabelEvent.DOWNLOAD);
		downloadMap.remove(id);
	}

	@Override
	public void downloadUpdate(String id, int percentDownload) {
		downloadMap.get(id).getLabel().updateDownload(percentDownload);
	}
	
	@Override
	public void updateIconLabelState(LabelEvent type, SearchResultData icon) {
		if(type==LabelEvent.PLAY){
			if(previousPlayedIcon!=null)
				previousPlayedIcon.getLabel().setLabelState(LabelEvent.PLAY);
			previousPlayedIcon=icon;
			//checks to see if another video was playing, if true remove it
			if(youTubePanel!=null)
				previewFrame.remove(youTubePanel);
			if(!previewFrame.isShowing())
				previewFrame.setVisible(true);

			youTubePanel=getBrowserPanel(icon.getVideoId());

			previewFrame.getContentPane().add(youTubePanel, BorderLayout.CENTER);

		}
		if(type==LabelEvent.DOWNLOAD){
			String path="https://www.youtube.com/watch?v="+icon.getVideoId();
			System.out.println("path is "+path);
			viewListener.update(CREATE_SONG, path);
			downloadMap.put(path, icon);	
		}
	
	}
	
}
