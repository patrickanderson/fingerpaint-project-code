package nl.tue.fingerpaint.client.gui.buttons;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button to initiate the removal of an initial distribution.
 * 
 * @author Group Fingerpaint
 */
public class RemoveInitDistButton extends Button implements ClickHandler {

	/**
	 * Reference to the model. Used to get the saved protocols for the current
	 * geometry.
	 */
	protected ApplicationState as;
	
	/**
	 * Construct a new button that can be used to initiate the removal of
	 * an initial distribution.
	 * 
	 * @param appState
	 *            Reference to the model, used to get the currently selected
	 *            geometry, which in turn is used in the local storage.
	 */
	public RemoveInitDistButton(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnRemoveInitDistButton());
		addClickHandler(this);
		this.as = appState;		
		this.getElement().setId("removeInitDistButton");
	}

	/**
	 * Creates a popup panel showing all saved initial distributions.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {		
		GuiState.removeResultsVerticalPanel.clear();
		
		final ArrayList<String> names = (ArrayList<String>) StorageManager.INSTANCE
				.getDistributions(as.getGeometryChoice());
		GuiState.initDistFlexTable.fillFlexTable(names, as.getGeometryChoice());
		
		GuiState.removeResultsVerticalPanel.addList(GuiState.initDistFlexTable);
		GuiState.removeResultsVerticalPanel.add(GuiState.closeResultsButton);

		GuiState.removeResultsPanel.center();
	}
}
