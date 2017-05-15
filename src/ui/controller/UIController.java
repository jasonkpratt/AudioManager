package ui.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.axet.vget.info.VideoFileInfo;

import executable.Audio;
import executable.DownloadListener;
import ffmpeg.AudioManager;
import ui.UI_Constants;
import ui.controller.SearchEvent.Type;
import ui.model.SoundEffect;
import ui.model.YouTubeParser;
import ui.view.CleanUpView;
import ui.view.ConverterView;
import ui.view.DownloadStateListener;
import ui.view.ManualView;
import ui.view.SearchView;
import ui.view.TreeView;

public class UIController extends ObjectQueue implements ViewListener, UI_Constants, DownloadListener {

	AudioManager audioMAnager=new AudioManager();
	YouTubeParser uTubeParser=new YouTubeParser();
	SearchView searchView;
	TreeView treeView;
	CleanUpView cleanUpView;
	ConverterView converterView;
	ManualView manualView;
	boolean videoState=false;
	Audio downloadManager=new Audio();
	private boolean playMusic=false;
	private List<String> songsCurrentlyDownloaded=new ArrayList<>();
	private List<DownloadStateListener> downloadStateListeners=new ArrayList<>();
	
	public UIController(SearchView searchView, TreeView treeView, CleanUpView cleanUpView, ConverterView converterView, ManualView manualView){
		this.manualView=manualView;
		this.searchView=searchView;
		this.treeView=treeView;
		this.cleanUpView=cleanUpView;
		this.converterView=converterView;
		downloadManager.setDownloadListener(this);
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
		
		if(eventName.equals(LISTEN_SONG))
			queueObject(new SearchEvent(Type.listen,data));
		
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
			if(songsCurrentlyDownloaded.contains(event.getEventString()))
				break;
 			songsCurrentlyDownloaded.add(event.getEventString());
			 Thread thread = new Thread(new Runnable() {
         public void run() {
    			downloadSong(event);
         }
       });
			thread.start();
			break;
			
		case search:
			createSearchUI(event.getEventString());
			break;
			
		case listen:
			listenToMusic(event);
		}		
	}

	//***************************************************************************************************
	
	private void listenToMusic(SearchEvent event) {
		playMusic=true;
		downloadSong(event);
		
	}
	
//***************************************************************************************************
		
	private void createSearchUI(String searchString) {
		
		try {
			searchView.updateImageScrollBar(uTubeParser.startParser(searchString));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	private void downloadSong(SearchEvent event) {
		String [] inputArguments=new String [3];
		inputArguments[0]=event.getEventString();
		inputArguments[1]=TEMP_DOWNLOAD_PATH;
		inputArguments[2]=String.valueOf(videoState);
		downloadManager.main(inputArguments);
	}
//*******************************************************************************************************

	public void addDownloadStateListeners(DownloadStateListener listener){
		downloadStateListeners.add(listener);
	}
//*******************************************************************************************************
	
	@Override
	public void updateDownloadListener(VideoFileInfo videoFile, String id ) {
		//todo update download progress bar
		if(!videoFile.getContentType().contains("video")){
			for(DownloadStateListener listener:downloadStateListeners){
				float downloadPercent= (videoFile.getCount() / (float) videoFile.getLength());
				int percent=((int)(downloadPercent*100));
				System.out.println(" \nState is : "+videoFile.getState()+" id: "+id+" with percent "+percent);
				listener.downloadUpdate(id, percent);
			}
		}
	}
//*******************************************************************************************************
	
	private void playAudio(String audioFile) {
	// TODO Auto-generated method stub
	
	}
	
	public void shutdown(){
		//todo remove listeners
	}

	@Override
	public void downLoadComplete(String id) {
		for(DownloadStateListener listener:downloadStateListeners){
			System.out.println(" download complete for id "+id);
			listener.downloadComplete(id);
			List<String> files=downloadManager.getFileNames();
			System.out.println("Files size is "+files.size());
			String audioFile = null;
			
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
			songsCurrentlyDownloaded.remove(id);
			SoundEffect.DownloadComplete.play();
		}	
	}
	
}
