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

public class GraphPanel extends JPanel {

	public final static int DEFAULT_DELAY_MS = 5;
	private final static int BUFFER_BOUNDS = 5;

	private LinkedList<Point> pointList;
	private LinkedList<Point> convexHullList;
	private Random random = new Random();
	private Line step;

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
				convexHullAlgo.step();
				setConvexHullList(convexHullAlgo.getConvexHullList());
				repaint();
			} else {
				stop();
			}
		}

		public void setConvexHullAlgo(ConvexHullAlgo cha) {
			convexHullAlgo = cha;

		}

		public ConvexHullAlgo getConvexHullAlgo() {
			return convexHullAlgo;
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
			int x = random.nextInt(getWidth() - BUFFER_BOUNDS * 2 + 1)
					+ BUFFER_BOUNDS;
			int y = random.nextInt(getHeight() - BUFFER_BOUNDS * 2 + 1)
					+ BUFFER_BOUNDS;

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
//		final JarvisMarch jm = new JarvisMarch(pointList);
		UpperLowerHull ulh = new UpperLowerHull(pointList);
		ConvexHullApp.numCounterPane.setText("0");
		cgActionListener.setConvexHullAlgo(ulh);

//		cgActionListener.setConvexHullAlgo(jm);

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
		pointList.add(new Point(x,y));
		repaint();
	}
}
