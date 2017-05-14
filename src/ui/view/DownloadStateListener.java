package ui.view;

public interface DownloadStateListener {
	
	void downloadUpdate(String id, int downloadPercent);
	void downloadComplete(String id);

}
