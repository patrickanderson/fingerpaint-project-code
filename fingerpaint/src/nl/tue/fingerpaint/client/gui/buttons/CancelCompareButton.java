package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.Set;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * Button that can be used to cancel the comparing.
 * 
 * @author Group Fingerpaint
 */
public class CancelCompareButton extends FastButton implements PressHandler {

	/**
	 * SelectionModel of the {@link CellList} that is used to make a selection
	 * of results to compare. Used to deselect items when this button is
	 * clicked.
	 */
	protected final MultiSelectionModel<String> selectionModel;

	/**
	 * Construct a new button that can be used to cancel comparing two results.
	 * 
	 * @param selectionModel
	 *            SelectionModel of the {@link CellList} that is used to make a
	 *            selection of results to compare. Used to deselect items when
	 *            this button is clicked.
	 */
	public CancelCompareButton(final MultiSelectionModel<String> selectionModel) {
		super(FingerpaintConstants.INSTANCE.btnCancel());
		this.selectionModel = selectionModel;
		addPressHandler(this);
		addStyleName("panelButton");
		ensureDebugId("cancelCompareButton");
	}

	/**
	 * Unselects all selected items and hides the popup.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		Set<String> selected = selectionModel.getSelectedSet();
		for (String s : selected) {
			selectionModel.setSelected(s, false);
		}
		GuiState.compareSelectPopupPanel.hide();
	}

}
