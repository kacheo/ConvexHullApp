package com.lonkal.convexhullapp.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The "main" class that calls the main method and initializes everything
 * 
 * @author Kal
 *
 */
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
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setResizable(false);

		final GraphPanel graphPanel = new GraphPanel();
		graphPanel.setPreferredSize(new Dimension(WIDTH,WIDTH));
		graphPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				graphPanel.addPoint(e.getX(), e.getY());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});
		mainFrame.add(graphPanel, BorderLayout.CENTER);
		Color mainFrameBgColor = mainFrame.getBackground();

		// NORTH PANEL
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel numCounterHolderPane = new JPanel();
		JTextPane numCounterStaticPane = new JTextPane(); // Holds the step
		numCounterStaticPane.setText("Step Number: ");
		numCounterStaticPane.setBackground(mainFrameBgColor);

		numCounterPane = new JTextPane();
		numCounterPane.setText("0");
		numCounterPane.setBackground(mainFrameBgColor);
		
		numCounterHolderPane.add(numCounterStaticPane);
		numCounterHolderPane.add(numCounterPane);
	
		JPanel speedBarHolderPane = new JPanel();
		final JTextPane numStepPerSecondPane = new JTextPane();
		numStepPerSecondPane.setBackground(mainFrameBgColor);
		numStepPerSecondPane.setText(GraphPanel.DEFAULT_DELAY_MS
				+ " ms between steps");

		// SLIDER
		final JSlider stepSpeedSlider = new JSlider(JSlider.HORIZONTAL,
				GraphPanel.MIN_DELAY_MS, GraphPanel.MAX_DELAY_MS,
				GraphPanel.DEFAULT_DELAY_MS);
		stepSpeedSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				graphPanel.setSpeed(stepSpeedSlider.getValue());
				int delayPerStep = stepSpeedSlider.getValue();
				if (delayPerStep == 0) {
					numStepPerSecondPane.setText("No Delay!");
					return;
				}
				numStepPerSecondPane
						.setText(delayPerStep + " ms between steps");
			}

		});
		stepSpeedSlider.setMajorTickSpacing(100);
		stepSpeedSlider.setMinorTickSpacing(20);
		stepSpeedSlider.setPaintTicks(true);
		stepSpeedSlider.setPaintLabels(true);

		speedBarHolderPane.add(stepSpeedSlider);
		speedBarHolderPane.add(numStepPerSecondPane);

		JPanel aboutPanel = new JPanel();
		JButton aboutButton = new JButton("About");
		aboutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, CHAppSettings.ABOUT_APP_MESSAGE,
						"About this application",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		aboutPanel.add(aboutButton);

		topPanel.add(aboutPanel, BorderLayout.CENTER);
		topPanel.add(numCounterHolderPane, BorderLayout.WEST);
		topPanel.add(speedBarHolderPane, BorderLayout.EAST);

		// SOUTH PANEL
		JPanel toolbarPanel = new JPanel();
		final JComboBox<String> chAlgoSelector = new JComboBox<String>(
				CHAppSettings.CH_ALGORITHMS_LIST);
		JButton addRandomButton = new JButton("Generate Random Points");
		JButton clearButton = new JButton("Clear");
		JButton computeCHButton = new JButton("Compute Convex Hull");
		JButton stopButton = new JButton("Stop");
		JButton resumeButton = new JButton("Resume");

		chAlgoSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphPanel.setCHAlgorithm(chAlgoSelector.getSelectedItem()
						.toString());
			}

		});
		addRandomButton.addActionListener(new RandomButtonListener(graphPanel));
		clearButton.addActionListener(new ClearButtonListener(graphPanel));
		computeCHButton.addActionListener(new ComputeCHButton(graphPanel));
		stopButton.addActionListener(new StopButtonListener(graphPanel));
		resumeButton.addActionListener(new ResumeButtonListener(graphPanel));

		toolbarPanel.add(chAlgoSelector);
		toolbarPanel.add(computeCHButton);
		toolbarPanel.add(addRandomButton);
		toolbarPanel.add(clearButton);
		toolbarPanel.add(resumeButton);
		toolbarPanel.add(stopButton);

		// MAIN PANEL
		mainFrame.add(toolbarPanel, BorderLayout.SOUTH);
		mainFrame.add(topPanel, BorderLayout.NORTH);

		mainFrame.setVisible(true);
	}
}
