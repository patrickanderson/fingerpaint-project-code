package nl.tue.fingerpaint.client.gui.labels;

import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Label;

/**
 * Header for the protocol representation.
 * 
 * @author Group Fingerpaint
 */
public class ProtocolLabel extends Label {

	/**
	 * Construct the {@link ProtocolLabel}.
	 */
	public ProtocolLabel() {
		super(FingerpaintConstants.INSTANCE.lblProtocol());
		ensureDebugId("labelProtocolLabel");
		getElement().setClassName(FingerpaintConstants.INSTANCE.classMenuTitleLabel());
	}
	
}
