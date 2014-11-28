package com.lonkal.convexhullapp.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lonkal.convexhullapp.main.GraphPanel;

public class RandomButtonListener implements ActionListener {
	private GraphPanel graphPanel;

	public RandomButtonListener(GraphPanel gp) {
		graphPanel = gp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		graphPanel.addRandomPoint();
	}
}