package ui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.UI_Constants;
import ui.controller.UIController;
import ui.controller.ViewListener;

public class SearchView extends JPanel implements UI_Constants{

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
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = column;
		c.gridy = row;
		JLabel searchFieldLabel =new JLabel("Media Search");
		add(searchFieldLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		column++;
		c.gridx = column;
		c.gridy = row;
		JTextField searchField= new JTextField(20);
		add(searchField,c);
		
		//2nd row
		row++;
		column=0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = column;
		c.gridy = row;
		JLabel linkFieldLabel =new JLabel("HTTP link");
		add(linkFieldLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		column++;
		c.gridx = column;
		c.gridy = row;
		JTextField linkField= new JTextField(20);
		add(linkField,c);
		
		//3rd row
		c.fill = GridBagConstraints.HORIZONTAL;
		column=0;
		row++;
		c.gridx = column;
		c.gridy = row;
		JButton searchButton= new JButton("Search ");
		add(searchButton,c);
		searchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				viewListener.update(SEARCH_MEDIA, searchField.getText());
			}
			
		});
		
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
			}
			
		});
	
		return this;
	}

	public void setListner(ViewListener listener) {
		
		this.viewListener=listener;
		
	}
	
	
}
