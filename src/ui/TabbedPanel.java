package ui;

import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane {

	public TabbedPanel buildUI(){
		SearchView searchView	=	new SearchView();
		TreeView treeView 		= new TreeView();
		CleanUpView cleanUpView=new CleanUpView();
		ConverterView converterView = new ConverterView();
		addTab("Search", searchView.buildUI());
		addTab("Tree", treeView);
		addTab("Clean Up", cleanUpView);
		addTab("Converter", converterView);	
		return this;
	}
	
}
