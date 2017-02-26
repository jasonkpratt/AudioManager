package ui.view;

import javax.swing.JTabbedPane;

import ui.controller.UIController;

public class TabbedPanel extends JTabbedPane {

	public TabbedPanel buildUI(){
		SearchView searchView	=	new SearchView();
		TreeView treeView 		= new TreeView();
		CleanUpView cleanUpView=new CleanUpView();
		ConverterView converterView = new ConverterView();
		
		UIController controller=new UIController();
		searchView.setListner(controller);
		
		
		addTab("Search", searchView.buildUI());
		addTab("Tree", treeView);
		addTab("Clean Up", cleanUpView);
		addTab("Converter", converterView);	
		return this;
	}
	
}
