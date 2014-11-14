package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.LinkedList;

/**
 * A class that represents an algorithm called Jarvis March
 * 
 * @author Kal
 * 
 */
public class JarvisMarch extends ConvexHullAlgo {

	private int currentIndexOfElement = 0; // Which was the last checked ith
											// point of pointList.
	private Point p,q,r;


	private boolean isDone = false;
	//private boolean isRunning = false;

	public JarvisMarch(LinkedList<Point> list) {
		if (list == null) {
			throw new IllegalArgumentException("Passed in list was null");
		}
		pointList = list;

		if (pointList.size() < 3) {
			throw new IllegalArgumentException(
					"Fail... the number of points has to be 3 or more");
		}

		init();
	}

	public void runEntirely() {
		while (!isDone) {
			step();
		}
	}

	private void init() {
		convexHullList = new LinkedList<Point>();

		Point leftMostPoint = getLeftmostPoint(pointList);
		// Add leftmost point to the convex hull list
		convexHullList.add(leftMostPoint);

		p = leftMostPoint;
		q = pointList.getFirst();
		r = pointList.getFirst();
	}

	@Override
	public void step() {
		stepNum++;
		if (currentIndexOfElement >= pointList.size()) {

			// q is the new hull point
			convexHullList.add(q);
			if (q == convexHullList.getFirst()) {

				// We're done, since we reached the originating point of CH
				isDone = true;
				r = null;
			}

			p = q;
			q = pointList.getFirst();

			// reset the index to 0 to restart search based on new hull point
			currentIndexOfElement = 0;
			return;
		}

		r = pointList.get(currentIndexOfElement);
		if (q == p || Primitives.orientation(p, q, r) == 1) {
			q = r;
		}

		currentIndexOfElement++;
	}

	// Is the method done or not
	@Override
	public boolean isDone() {
		return isDone;
	}

	// return the current convexhull list
	public LinkedList<Point> getConvexHullList() {
		return convexHullList;
	}

	// Method specific to this method, which gets the left most point of points
	private Point getLeftmostPoint(LinkedList<Point> list) {
		Point leftMostPoint = list.getFirst();
		for (Point p : list) {
			if (p.x < leftMostPoint.x) {
				leftMostPoint = p;
			}
		}

		return leftMostPoint;
	}

	public Line getCurrentStepLine() {
		return new Line(p, q);
	}
}
