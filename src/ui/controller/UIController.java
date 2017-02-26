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

	public UIController(){
		startConsuming();
	}
	
	@Override
	public void update(String eventName, String data) {
		if(eventName.equals(CREATE_SONG)){
			queueObject(new SearchEvent(Type.create,data));
		}
		
		if(eventName.equals(SEARCH_MEDIA)){
			queueObject(new SearchEvent(Type.search,data));
		}
	}

	@Override
	protected void processObject(Object object) {
		if(object==null) return;
		if(object instanceof SearchEvent ){
			processEvent((SearchEvent) object);
		}
		
	}

	private void processEvent(SearchEvent event) {
		switch(event.getType()){
			
		case create:
			String [] inputArguments=new String [2];
			inputArguments[0]=event.getEventString();
			inputArguments[1]=TEMP_DOWNLOAD_PATH;
			Audio audio=new Audio();
			audio.main(inputArguments);
			List<String> files=audio.getFileNames();
			System.out.println("Files size is "+files.size());
			String audioFile = null;
			if(files.size()==1) audioFile=files.get(0);
			else 
				for(String fileName:files){	
				System.out.println("file name "+fileName );
				if(fileName.contains(".audio")) audioFile=fileName;
			}
			AudioManager audioMAnager=new AudioManager();
			audioMAnager.createAudio(audioFile);
			for(String fileName:files){
				Path filePath=Paths.get(fileName);
				try {
					Files.delete(filePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
		case search:
			//use utube api to find songs
			break;
		}
		
	}

}
