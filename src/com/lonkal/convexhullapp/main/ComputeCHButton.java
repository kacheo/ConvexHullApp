package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;

public class ComputeCHButton implements ActionListener {

	private GraphPanel graphPanel;
	private ConvexHullAlgo currentAlgo;

	public ComputeCHButton(GraphPanel gp) {
		graphPanel = gp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JarvisMarch jm = new JarvisMarch(graphPanel.getPointList());

		jm.runEntirely();

		graphPanel.setConvexHullList(jm.getConvexHullList());
		graphPanel.repaint();
		// default is jarvis
		// jarvisMarch();
	}

	public void changeCHMethod() {
		// to change the method
	}

	public void jarvisMarch() {
		Timer t = graphPanel.getTaskTimer();

		LinkedList<Point> convexHullList = new LinkedList<Point>();
		LinkedList<Point> pointList = graphPanel.getPointList();

		graphPanel.addCHList(convexHullList);

		Point hullPoint = getLeftmostPoint(pointList);
		Point endPoint;

		t.start();
		do {
			endPoint = pointList.getFirst();
			convexHullList.add(hullPoint);

			// Walk through the pointList
			for (Point p : pointList) {
				System.out.println(p);
				// Send a draw event
				// Wait for draw event + pause
				// Comeback and keep going
				System.out.println(Thread.currentThread().getName());
				graphPanel.updateStep(hullPoint, p);

				// System.out.println(Primitives.orientation(hullPoint,
				// endPoint, p));
				if (endPoint == hullPoint
						|| Primitives.orientation(hullPoint, endPoint, p) == 2) {
					endPoint = p;
				}
			}
			hullPoint = endPoint;

			// graphPanel.repaint();
			System.out.println(convexHullList);

		} while (endPoint != convexHullList.getFirst());
		convexHullList.add(convexHullList.getFirst());
		t.stop();
		System.out.println(convexHullList);
	}

	public Point getLeftmostPoint(LinkedList<Point> list) {
		Point leftMostPoint = list.getFirst();
		for (Point p : list) {
			if (p.x < leftMostPoint.x) {
				leftMostPoint = p;
			}
		}

		return leftMostPoint;
	}
}
