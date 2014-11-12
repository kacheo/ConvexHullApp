package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;

public class ComputeCHButton implements ActionListener {

	private GraphPanel graphPanel;

	public ComputeCHButton(GraphPanel gp) {
		graphPanel = gp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		graphPanel.start();
	}

	public void changeCHMethod() {
		// to change the method
	}

}
