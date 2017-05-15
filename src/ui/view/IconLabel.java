package ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ui.model.SearchResultData;

public class IconLabel extends JLabel implements MouseMotionListener, MouseListener {
	int borderThickness=3;
	boolean hooverButton=false;
	SearchResultData icon;
	private boolean hooverOverPlayButton=false;
	IconLabelListener listener;
	private boolean currentlyPlaying=false;
	private boolean downloading=false;
	private int percentDownload=0;
	private boolean downloadComplete=false;
	
	public enum LabelEvent{
		PLAY, DOWNLOAD
	}
	
	public IconLabel(SearchResultData icon) {
		// TODO Auto-generated constructor stub
		super(icon.getImageIcon());
		this.icon=icon;
		setBorder(BorderFactory.createEmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
		this.setToolTipText("download");
		addMouseListener(this);
		addMouseMotionListener(this);	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isMousePositionPlayButton(e)){
			if(!currentlyPlaying){
				//paint current playing icon playButton to green
				currentlyPlaying=true;
				updatePlayButtonColor(Color.green);
				updateListener(LabelEvent.PLAY);
			}
		}

		else if(!downloading){
			downloading=true;
			this.setBorder(BorderFactory.createLineBorder(Color.GREEN,borderThickness ));
			updateListener(LabelEvent.DOWNLOAD);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if(!downloading){	
				this.setBorder(BorderFactory.createLineBorder(Color.CYAN,borderThickness ));
		}	
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		//remove border if not currently downloading
		if(!downloading){
			this.setBorder(BorderFactory.createEmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
		}
		//return to white playbutton if exited and not playing
		if(hooverOverPlayButton&&!currentlyPlaying){
			updatePlayButtonColor(Color.white);;
			hooverOverPlayButton=false;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		//if play button is green due to video being the last selected
		//leave it green otherwise if entering playbutton area turn gray
		//otherwise check to see if leaving playButton area if so turn white
		if(!currentlyPlaying){
			
			if(isMousePositionPlayButton(e)){
				updatePlayButtonColor(Color.gray);
				hooverOverPlayButton=true;
			}
			else{
				if(hooverOverPlayButton){
					updatePlayButtonColor(Color.white);
					hooverOverPlayButton=false;
				}
			}	
		}		
	}

	private boolean isMousePositionPlayButton(MouseEvent e) {
		if(e.getX()>79&&e.getY()<26)
			return true;
		return false;
	}
	
	public void setLabelState(LabelEvent type){
		//notifies label of state change
		if(type==LabelEvent.PLAY){
			currentlyPlaying=false;
			updatePlayButtonColor(Color.WHITE);
		}
		if(type==LabelEvent.DOWNLOAD){
			downloading=false;
			percentDownload=0;
			setBorder(BorderFactory.createEmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
			downloadComplete=true;
		}
		
	}
	
	public void setLabelListener(IconLabelListener listener){
		this.listener=listener;
	}
	
	private void updateListener(LabelEvent type){
		listener.updateIconLabelState(type, icon);
	}
	
	void updatePlayButtonColor(Color color){
		icon.drawPlayButton(color);
		repaint();
		revalidate();
	}
	
	public void updateDownload(int percentDownload){
		this.percentDownload=percentDownload;
		repaint();
		revalidate();
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		if(downloading){	
			g.setColor(Color.green);
			g.drawString("Downloading...", 20, 50);
		
			if(percentDownload>0){
			String downloadPercent=String.valueOf(percentDownload)+"%";		
			g.drawString(downloadPercent, 60, 70);
			}
		}
		if(downloadComplete){
			g.setColor(Color.GREEN);
			g.drawString("Download Complete", 10, 50);
		}
	}

	
}
