package ui;

import javax.swing.JPanel;

public class TopPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanel buildUI(){
		
		TabbedPanel tabbedPanel=new TabbedPanel();
		add(tabbedPanel.buildUI());
		return this;
	}
	//*********************************************************************
}
