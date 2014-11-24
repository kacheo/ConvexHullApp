package com.lonkal.convexhullapp.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleRunButtonListener implements ActionListener {
	private GraphPanel graphPanel;

	public ToggleRunButtonListener(GraphPanel graphPanel) {
		this.graphPanel = graphPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (graphPanel.isRunning()) {
			graphPanel.stop();
		} else {
			graphPanel.resume();
		}
	}

}
