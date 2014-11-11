package com.lonkal.convexhullapp.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopButtonListener implements ActionListener {
	private GraphPanel graphPanel;
	
	public StopButtonListener(GraphPanel graphPanel) {
		this.graphPanel = graphPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		graphPanel.stop();
	}

}
