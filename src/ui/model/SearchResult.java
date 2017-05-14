package ui.model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ui.UI_Constants;

public class SearchResult implements UI_Constants {
	String videoId;
	String length;
	String playlistUrl;
	String fullTitle;
	String parsedTitle;
	PlaylistType listType=PlaylistType.singleSong;
	ImageIcon imageIcon=null;
	JLabel label;
	
	
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
	
	public void setImageIcon(ImageIcon image){
		imageIcon=image;
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
	
	public void  setJlabel(JLabel label){
		this.label=label;
	}
	
	public JLabel getLabel(){
		return label;
	}
	
}
