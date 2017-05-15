package ui.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.model.SearchResultData;

public class IconPanel extends JPanel {
	SearchResultData icon;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IconPanel(SearchResultData icon){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.icon=icon;
	}

	public void addLabel(){
		//checks to see if label was created previously. Primarily for icons which are currently downloading and
		//are being added to a new search view
		IconLabel iconLabel=null;
		if(icon.getLabel()==null){
			iconLabel=new IconLabel(icon);
			icon.setLabel(iconLabel);
		}
		else{
			iconLabel=icon.getLabel();
		}
		this.add(iconLabel);
	}


}
