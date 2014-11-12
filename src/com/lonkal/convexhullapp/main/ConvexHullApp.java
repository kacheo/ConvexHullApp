package com.lonkal.convexhullapp.main;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class ConvexHullApp {

	public JFrame mainFrame;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	public static JTextPane numCounterPane;
	
	public static void main(String[] args) {
		initializeGUI();
	}

	private static void initializeGUI() {
		// MAIN PANEL's settings
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("ConvexHullApp");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setVisible(true);
		mainFrame.setSize(WIDTH, HEIGHT);

		final GraphPanel graphPanel = new GraphPanel();
		graphPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				graphPanel.addPoint(e.getX(), e.getY());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mainFrame.add(graphPanel, BorderLayout.CENTER);

		// NORTH PANEL
		JPanel topPanel = new JPanel();
		JTextPane numCounterStaticPane = new JTextPane(); //Holds the step
		numCounterStaticPane.setText("Step Number: ");
		numCounterPane = new JTextPane();
		numCounterPane.setText("0");
		topPanel.add(numCounterStaticPane);
		topPanel.add(numCounterPane);
		
		// SOUTH PANEL
		JPanel toolbarPanel = new JPanel();
		JButton addRandomButton = new JButton("Generate Random Points");
		JButton clearButton = new JButton("Clear");
		JButton computeCHButton = new JButton("Compute Convex Hull");
		JButton stopButton = new JButton("Stop");
		JButton resumeButton = new JButton("Resume");
		
		addRandomButton.addActionListener(new RandomButtonListener(graphPanel));
		clearButton.addActionListener(new ClearButtonListener(graphPanel));
		computeCHButton.addActionListener(new ComputeCHButton(graphPanel));
		stopButton.addActionListener(new StopButtonListener(graphPanel));
		resumeButton.addActionListener(new ResumeButtonListener(graphPanel));
		
		toolbarPanel.add(computeCHButton);
		toolbarPanel.add(addRandomButton);
		toolbarPanel.add(clearButton);
		toolbarPanel.add(resumeButton);
		toolbarPanel.add(stopButton);

		// MAIN PANEL
		mainFrame.add(toolbarPanel, BorderLayout.SOUTH);
		mainFrame.add(topPanel, BorderLayout.NORTH);
	}
}
