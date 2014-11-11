package com.lonkal.convexhullapp.main;

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

	private LinkedList<Point> pointList = new LinkedList<Point>();
	private LinkedList<Point> convexHullList = new LinkedList<Point>();
	private Random random = new Random();
	private Line step;
	private Timer taskTimer = new Timer(1000*5, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			repaint();
		}
	});

	private final static int BUFFER_BOUNDS = 5;

	public Timer getTaskTimer() {
		return taskTimer;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, 800, 800);

		// Draw the points
		for (Point p : pointList) {
			g.drawLine((int) p.getX(), (int) p.getY(), (int) p.getX(),
					(int) p.getY());
		}

		// Draw a step
		if (step != null) {
			g.drawLine(step.getPoint1().x, step.getPoint1().y,
					step.getPoint2().x, step.getPoint2().y);
		}

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
		repaint();
	}

	public void clearStep() {
		step = null;
		repaint();
	}

	public void addCHList(LinkedList<Point> convexHullList) {
		this.convexHullList = convexHullList;
	}

	public void clearConvexHull() {
		this.convexHullList.clear();
	}

	public void clear() {
		clearConvexHull();
		clearPoints();
		clearStep();
	}
	
	public void start() {
		JarvisMarch jm = new JarvisMarch(pointList);

		jm.runEntirely();

		setConvexHullList(jm.getConvexHullList());
		repaint();
	}
	
	public void stop() {		
	}
	
	public void resume() {
	}

	public void setConvexHullList(LinkedList<Point> newConvexHullList) {
		if (newConvexHullList == null) {
			throw new IllegalArgumentException("Null convex hull list passed in");
		}
		this.convexHullList = newConvexHullList;
	}
}
