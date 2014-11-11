package com.lonkal.convexhullapp.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResumeButtonListener implements ActionListener {
	private GraphPanel graphPanel;

	public ResumeButtonListener(GraphPanel graphPanel) {
		this.graphPanel = graphPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		graphPanel.resume();
	}

}
