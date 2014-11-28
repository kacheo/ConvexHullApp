package com.lonkal.convexhullapp.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lonkal.convexhullapp.main.GraphPanel;

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
