package com.lonkal.convexhullapp.main;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConvexHullApp {

	public JFrame mainFrame;
	
	public static void main(String[] args) {
		initializeGUI();
	}
	
	private static void initializeGUI() {
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("ConvexHullApp");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setVisible(true);
		mainFrame.setSize(800, 800);
		
		GraphPanel graphPanel = new GraphPanel();
		mainFrame.add(graphPanel, BorderLayout.CENTER);
		
		JPanel toolbarPanel = new JPanel();
		JButton addRandomButton = new JButton("Random");
		JButton clearButton = new JButton("Clear");
		JButton computeCHButton = new JButton("Convex Hull");
		
		addRandomButton.addActionListener(new RandomButtonListener(graphPanel));
		clearButton.addActionListener(new ClearButtonListener(graphPanel));
		computeCHButton.addActionListener(new ComputeCHButton(graphPanel));
		
		toolbarPanel.add(computeCHButton);
		toolbarPanel.add(addRandomButton);
		toolbarPanel.add(clearButton);
		
		mainFrame.add(toolbarPanel, BorderLayout.SOUTH);
	}
}
