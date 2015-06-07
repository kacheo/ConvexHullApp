package com.lonkal.convexhullapp.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lonkal.convexhullapp.main.GraphPanel;

public class ComputeCHButtonListener implements ActionListener {

	private GraphPanel graphPanel;

	public ComputeCHButtonListener(GraphPanel gp) {
		graphPanel = gp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		graphPanel.start();
	}
}
