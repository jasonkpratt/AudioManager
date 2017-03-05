package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ui.view.TopPanel;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainFrame(String name){
		super(name);
	}
	//*********************************************************************
	
	void initializeFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//UIManager.put("nimbusBase", Color.BLACK);
		try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	        if ("Nimbus".equals(info.getName())) {
	            UIManager.setLookAndFeel(info.getClassName());
	            break;
	        }
	    }
	} catch (Exception e) {
	    // If Nimbus is not available, you can set the GUI to another look and feel.
	}

		setSize(300,300);
		this.setLocationRelativeTo(null);
		TopPanel topPanel=new TopPanel();
		this.getContentPane().add(topPanel.buildUI(), BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}
	//*********************************************************************
	
	public static void main(String[] args){
		MainFrame frame=new MainFrame("Audio Manager");
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				
				frame.initializeFrame();		
			}		
		});
		
	}
	//*********************************************************************

}
