package ui.controller;

public class SearchEvent{
	public enum Type{
		search, create
	}
	//two events related to search, searching for a media link
	//and adding the media to the users library
	
	private Type type=null;
	private String eventString=null;


	public SearchEvent(Type typeEvent, String input){
		
		type=typeEvent;
		eventString=input;
	}
//********************************************************************************************************************
	
	public Type getType(){
		
		return type;
	}
//********************************************************************************************************************
	
	public String getEventString(){
		
		return eventString;
	}
//********************************************************************************************************************
	
}
