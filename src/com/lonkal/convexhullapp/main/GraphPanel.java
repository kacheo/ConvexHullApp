package com.lonkal.convexhullapp.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GraphPanel extends JPanel {

	public final static int DEFAULT_DELAY_MS = 5;
	public final static int MIN_DELAY_MS = 0;
	public final static int MAX_DELAY_MS = 500;

	private final static int BUFFER_BOUNDS_PX = 5;

	private LinkedList<Point> pointList;
	private LinkedList<Point> convexHullList;
	private LinkedList<Point> stepPointList;

	private Random random = new Random();
	private Line step;

	private String chAlgorithm = CHAppSettings.CH_JARVIS_MARCH_NAME;

	// We change the actionlistener only, so timer is a final object
	private CGActionListener cgActionListener = new CGActionListener();
	private final Timer taskTimer = new Timer(DEFAULT_DELAY_MS,
			cgActionListener);

	private class CGActionListener implements ActionListener {
		private ConvexHullAlgo convexHullAlgo;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!convexHullAlgo.isDone()) {
				int numStep = convexHullAlgo.getCurrentStep();
				ConvexHullApp.numCounterPane.setText(Integer.toString(numStep));
				step = convexHullAlgo.getCurrentStepLine();
				stepPointList = convexHullAlgo.getCurrentStepPoints();
				convexHullAlgo.step();
				setConvexHullList(convexHullAlgo.getConvexHullList());
			} else {
				step = null; // remove remaining steps
				stepPointList = null;
				stop();
			}
			repaint();
		}

		public void setConvexHullAlgo(ConvexHullAlgo cha) {
			convexHullAlgo = cha;

		}
	}

	public GraphPanel() {
		convexHullList = new LinkedList<Point>();
		pointList = new LinkedList<Point>();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, ConvexHullApp.HEIGHT, ConvexHullApp.WIDTH);

		// Draw the points
		for (Point p : pointList) {
			g.setColor(new Color(0, 0, 0));
			g.drawRect((int) p.getX() - 1, (int) p.getY() - 1, 2, 2);

		}

		// Draw a step
		if (step != null) {
			g.setColor(new Color(0, 100, 255));
			g.drawLine(step.getPoint1().x, step.getPoint1().y,
					step.getPoint2().x, step.getPoint2().y);
		}
		
		// Draw step points
		if (stepPointList != null) {
			g.setColor(new Color(0, 100, 255));
			for (int i = 0; i < stepPointList.size() - 1; i++) {
				g.drawLine(stepPointList.get(i).x, stepPointList.get(i).y,
						stepPointList.get(i + 1).x,
						stepPointList.get(i + 1).y);
			}
		}

		g.setColor(new Color(161, 0, 0));
		// Draw convex hull
		if (convexHullList != null) {
			for (int i = 0; i < convexHullList.size() - 1; i++) {
				g.drawLine(convexHullList.get(i).x, convexHullList.get(i).y,
						convexHullList.get(i + 1).x,
						convexHullList.get(i + 1).y);
			}
		}
	}

	public void addRandomPoint() {
		for (int i = 0; i < 50; i++) {
			int x = random.nextInt(getWidth() - BUFFER_BOUNDS_PX * 2 + 1)
					+ BUFFER_BOUNDS_PX;
			int y = random.nextInt(getHeight() - BUFFER_BOUNDS_PX * 2 + 1)
					+ BUFFER_BOUNDS_PX;

			Point p = new Point(x, y);
			pointList.add(p);
		}

		repaint();
	}

	public void clearPoints() {
		pointList.clear();
		repaint();
	}

	public LinkedList<Point> getPointList() {
		return pointList;
	}

	public void updateStep(Point hullPoint, Point p) {
		step = new Line(hullPoint, p);
	}

	public void clearStep() {
		step = null;
	}

	public void addCHList(LinkedList<Point> newConvexHullList) {
		convexHullList = newConvexHullList;
	}

	public void clearConvexHull() {
		convexHullList.clear();
	}

	public void clear() {
		clearConvexHull();
		clearPoints();
		clearStep();
	}

	public void start() {
		if (chAlgorithm == CHAppSettings.CH_JARVIS_MARCH_NAME) {
			final JarvisMarch jm = new JarvisMarch(pointList);
			cgActionListener.setConvexHullAlgo(jm);
		} else if (chAlgorithm == CHAppSettings.CH_MONOTONE_CHAIN_NAME) {
			final MonotoneChain mc = new MonotoneChain(pointList);
			cgActionListener.setConvexHullAlgo(mc);
		} else if (chAlgorithm == CHAppSettings.CH_RANDOMIZED_INCREMENTAL_NAME) {
			final RandomIncremental ri = new RandomIncremental(pointList);
			cgActionListener.setConvexHullAlgo(ri);
		} else {
			throw new IllegalStateException("No valid algorithm selected");
		}

		ConvexHullApp.numCounterPane.setText("0");
		taskTimer.start();
	}

	public void stop() {
		taskTimer.stop();
	}

	public void resume() {
		taskTimer.start();
	}

	public void setConvexHullList(LinkedList<Point> newConvexHullList) {
		if (newConvexHullList == null) {
			throw new IllegalArgumentException(
					"Null convex hull list passed in");
		}
		this.convexHullList = newConvexHullList;
	}

	public void addPoint(int x, int y) {
		pointList.add(new Point(x, y));
		repaint();
	}

	public void setCHAlgorithm(String algoString) {
		chAlgorithm = algoString;
	}

	public void setSpeed(int delay) {
		taskTimer.setDelay(delay);
	}
}
