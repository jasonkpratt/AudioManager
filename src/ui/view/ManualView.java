package ui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.UI_Constants;
import ui.controller.ViewListener;

public class ManualView extends JPanel implements UI_Constants{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewListener viewListener;

	public JPanel buildUI(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int column=0;
		int row=0;
		
		
		//1st row
		row++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = column;
		c.gridy = row;
		JLabel linkFieldLabel =new JLabel("YouTube HTTP link");
		add(linkFieldLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		column++;
		c.gridx = column;
		c.gridy = row;
		JTextField linkField= new JTextField(20);
		add(linkField,c);
		
		//2nd row

		
		c.fill = GridBagConstraints.HORIZONTAL;
		column=3;
		c.gridx = column;
		c.gridy = row;
		JButton makeButton= new JButton("Get Media ");
		add(makeButton,c);
		
		makeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				viewListener.update(CREATE_SONG, linkField.getText());
				linkField.setText("");
			}
			
		});
		
		c.fill = GridBagConstraints.HORIZONTAL;
		column=4;
		c.gridx = column;
		c.gridy = row;
		JCheckBox audioOnlyButton= new JCheckBox(" Audio Only ");
		audioOnlyButton.setSelected(true);
		add(audioOnlyButton,c);
		
		audioOnlyButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				viewListener.update(SET_VIDEO_STATE, String.valueOf(!audioOnlyButton.isSelected()));
			}
			
		});

		return this;
	}
//***********************************************************************************************
	
	public void setListner(ViewListener listener) {
		
		this.viewListener=listener;
		
	}
	
}
