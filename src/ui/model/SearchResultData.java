package ui.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ui.UI_Constants;
import ui.view.IconLabel;

public class SearchResultData implements UI_Constants {
	String videoId;
	String length;
	String playlistUrl;
	String fullTitle;
	String parsedTitle;
	PlaylistType listType=PlaylistType.singleSong;
	ImageIcon imageIcon=null;
	IconLabel label=null;
	Graphics g;
	
	
	public void setVideoId(String videolink){
		videoId=videolink;		
	}

	public String getVideoId(){
		return videoId;
	}
	
	public void setData(String data){
		if(data.matches(REGEX_VIDEO_LENGTH))
			length=data;
		else{
			length="playlist";
			playlistUrl=data;
			listType=PlaylistType.userList;
		}
	}
	
	public PlaylistType getPlaylistType(){
		return listType;
	}
	
	public String getAudioLength(){
		return length;
	}
	
	public void setLength(String len){
		length=len;
	}
	
	public String getPlaylistUrl(){
		return playlistUrl;
	}
	
	public void setTop100List(){
		listType=PlaylistType.top100;
	}
	
	public void setAlbumList(){
		listType=PlaylistType.album;
	}
	
	public void setImageIconPath(URL imgURL){
		ImageIcon image = createImageIcon(imgURL);
		setImageIcon(image);
	}
	
	public void setImageIcon(ImageIcon image){
		imageIcon=image;
   //draw time stamp and playButton with black rectangles as background
		g = imageIcon.getImage().getGraphics();
    g.setFont(g.getFont().deriveFont(10f));
    g.setColor(Color.BLACK); 
    g.fillRect(0, 80, 35,15 );
    g.fillRoundRect(80, 0, 45,30, 10, 10);
    drawPlayButton(Color.white);
    g.setColor(Color.LIGHT_GRAY);
    g.drawString(length, 4, 90);
	}
	
	public ImageIcon getImageIcon(){
		return imageIcon;
	}
	
	public void setfullTitle(String name){
		fullTitle=name;
	}
	
	public String getFullTitle(){
		return fullTitle;
	}
	
	public void setParsedTitle(String name){
		parsedTitle=name;
	}
	
	public String getParsedTitle(){
		return parsedTitle;
	}
	
	public void  setLabel(IconLabel label){
		this.label=label;
	}
	
	public IconLabel getLabel(){
		return label;
	}
	
//***********************************************************************************************
	public void drawPlayButton(Color color){
    g.setColor(color);
    int[] xPoints={98,98,112};
    int[] yPoints={10,20,15};
    g.fillPolygon(xPoints, yPoints, 3);  
	}
	
//********************************************************************************************************** 	
	
	/**
	 * Creates an ImageIcon if the path is valid.
	 * @param String - resource path
	 * @param String - description of the file
	 */
	protected ImageIcon createImageIcon(URL imgURL) {

		try{
			ImageIcon icon=new ImageIcon(imgURL);
			return new ImageIcon(getScaledImage(icon.getImage(), 128,96));
			//return icon;
		}
		catch(Exception ex){		
			//System.out.println("YOU tube image is null");
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
