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

import com.lonkal.convexhullapp.listeners.ClearButtonListener;
import com.lonkal.convexhullapp.listeners.ComputeCHButtonListener;
import com.lonkal.convexhullapp.listeners.RandomButtonListener;
import com.lonkal.convexhullapp.listeners.ToggleRunButtonListener;

/**
 * The "main" class that calls the main method and initializes everything
 * 
 * @author Kal
 *
 */
public class ConvexHullApp {

	public JFrame mainFrame;

	public static final int WIDTH = 900;
	public static final int HEIGHT = 800;

	public static JTextPane numCounterPane;
	private static JTextPane stepDescriptionPane;
	
	public static void main(String[] args) {
		initializeGUI();
	}

	private static void initializeGUI() {
		// MAIN PANEL's settings
		final JFrame mainFrame = new JFrame();
		mainFrame.setTitle("ConvexHullApp");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setResizable(false);

		// Use this for later, when we create the other panels to match the
		// background.
		Color mainFrameBgColor = mainFrame.getBackground();

		// Center Panel
		final GraphPanel graphPanel = new GraphPanel();
		graphPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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

		// NORTH PANEL
		final JPanel topPanel = new JPanel(new BorderLayout());
		final JPanel numCounterHolderPane = new JPanel();
		final JTextPane numCounterStaticPane = new JTextPane(); // Holds the
																// step
		numCounterStaticPane.setText(CHAppSettings.STEP_NUMBER);
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
				+ CHAppSettings.STEP_METRICS);

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

		// Step Description Pane
		stepDescriptionPane = new JTextPane();
		stepDescriptionPane.setText("---");
		stepDescriptionPane.setBackground(mainFrameBgColor);
		topPanel.add(stepDescriptionPane, BorderLayout.CENTER);
		topPanel.add(numCounterHolderPane, BorderLayout.WEST);
		topPanel.add(speedBarHolderPane, BorderLayout.EAST);

		// SOUTH PANEL
		final JPanel toolbarPanel = new JPanel();
		final JComboBox<String> chAlgoSelector = new JComboBox<String>(
				CHAppSettings.CH_ALGORITHMS_LIST);

		final JButton aboutButton = new JButton("About");
		final JButton addRandomButton = new JButton("Generate Random Points");
		final JButton clearButton = new JButton("Clear");
		final JButton computeCHButton = new JButton("Compute Convex Hull");
		final JButton resumeButton = new JButton("Run/Stop");

		chAlgoSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphPanel.setCHAlgorithm(chAlgoSelector.getSelectedItem()
						.toString());
			}

		});

		aboutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						CHAppSettings.ABOUT_APP_MESSAGE,
						"About this application",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		addRandomButton.addActionListener(new RandomButtonListener(graphPanel));
		clearButton.addActionListener(new ClearButtonListener(graphPanel));
		computeCHButton.addActionListener(new ComputeCHButtonListener(
				graphPanel));
		resumeButton.addActionListener(new ToggleRunButtonListener(graphPanel));

		toolbarPanel.add(chAlgoSelector);
		toolbarPanel.add(computeCHButton);
		toolbarPanel.add(addRandomButton);
		toolbarPanel.add(clearButton);
		toolbarPanel.add(resumeButton);
		toolbarPanel.add(aboutButton);

		// MAIN PANEL
		mainFrame.add(toolbarPanel, BorderLayout.NORTH);
		mainFrame.add(topPanel, BorderLayout.SOUTH);

		mainFrame.setVisible(true);
	}
	
	public static void log(String s) {
		if (stepDescriptionPane != null)
			stepDescriptionPane.setText(s);
	}

}
