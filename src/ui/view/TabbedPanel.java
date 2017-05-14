package ui.view;

import javax.swing.JTabbedPane;

import ui.controller.UIController;

public class TabbedPanel extends JTabbedPane {

	public TabbedPanel buildUI(){
		SearchView searchView	=	new SearchView();
		TreeView treeView 		= new TreeView();
		CleanUpView cleanUpView=new CleanUpView();
		ConverterView converterView = new ConverterView();
		ManualView manualView=new ManualView();
		
		UIController controller=new UIController(searchView,treeView,cleanUpView,converterView,manualView);
		controller.addDownloadStateListeners(searchView);
		searchView.setListner(controller);
		manualView.setListner(controller);
	
		addTab("Search", searchView.buildUI());
		addTab("Manual", manualView.buildUI());
		addTab("Tree", treeView);
		addTab("Clean Up", cleanUpView);
		addTab("Converter", converterView);	
		addTab("History", null);
		addTab("Awards", null);
		addTab("Favs", null);
		addTab("Lyrics",null);
		addTab("Guitar Tab", null);
		return this;
	}
	
}
