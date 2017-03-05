package ui.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import executable.Audio;
import ffmpeg.AudioManager;
import ui.UI_Constants;
import ui.controller.SearchEvent.Type;

public class UIController extends ObjectQueue implements ViewListener, UI_Constants {

	
	boolean videoState=false;
	public UIController(){
		startConsuming();
	}
	//*******************************************************************************************
	
	@Override
	public void update(String eventName, String data) {
		if(eventName.equals(CREATE_SONG)){
			queueObject(new SearchEvent(Type.create,data));
		}
		
		if(eventName.equals(SEARCH_MEDIA)){
			queueObject(new SearchEvent(Type.search,data));
		}
		
		if (eventName.equals(SET_VIDEO_STATE)){
			videoState=Boolean.valueOf(data);
		}
	}
	//*******************************************************************************************
	
	@Override
	protected void processObject(Object object) {
		if(object==null) return;
		if(object instanceof SearchEvent ){
			processEvent((SearchEvent) object);
		}
	}
	//*******************************************************************************************
	
	private void processEvent(SearchEvent event) {
		switch(event.getType()){
			
		case create:
			List<String> files=downloadSong(event);
			String audioFile = null;
			AudioManager audioMAnager=new AudioManager();
			//select audio file to convert
			//todo allow video to convert if needed
			if(files.size()==1) audioFile=files.get(0);
			else 
				for(String fileName:files){	
				if(fileName.contains(".audio")) audioFile=fileName;
			}

			//audio manager converts from mp4 to mp3
			audioMAnager.createAudio(audioFile);
			
			//delete mp4 from directory
			deleteFromDirectory(audioFile);
			break;
			
		case search:
			//use utube api to find songs
			break;
		}		
	}
	
	//***************************************************************************************************
	
	private void deleteFromDirectory(String file) {
		Path filePath=Paths.get(file);
		try {
			Files.delete(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//*******************************************************************************************
	
	private List<String> downloadSong(SearchEvent event) {
		String [] inputArguments=new String [3];
		inputArguments[0]=event.getEventString();
		inputArguments[1]=TEMP_DOWNLOAD_PATH;
		inputArguments[2]=String.valueOf(videoState);
		Audio audio=new Audio();
		audio.main(inputArguments);
		List<String> files=audio.getFileNames();
		System.out.println("Files size is "+files.size());
		return files;
	}
//*******************************************************************************************************
	
}
