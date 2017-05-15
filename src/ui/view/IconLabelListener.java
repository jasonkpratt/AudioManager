package ui.view;

import ui.model.SearchResultData;
import ui.view.IconLabel.LabelEvent;

public interface IconLabelListener {

	void updateIconLabelState(LabelEvent type, SearchResultData icon);
}
