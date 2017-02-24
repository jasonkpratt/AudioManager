package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchView extends JPanel {

	public JPanel buildUI(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int column=0;
		int row=0;
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
		c.fill = GridBagConstraints.HORIZONTAL;
		column=0;
		row++;
		c.gridx = column;
		c.gridy = row;
		JButton searchButton= new JButton("Search ");
		add(searchButton,c);
		return this;
	}
}
