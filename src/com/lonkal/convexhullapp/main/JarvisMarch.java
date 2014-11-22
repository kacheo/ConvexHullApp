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

	public JarvisMarch(LinkedList<Point> list) {
		super(list);
		init();
	}

	private int currentIndexOfElement = 0; // Which was the last checked ith
											// point of pointList.
	private Point p, q, r;

	private boolean isDone = false;

	// private boolean isRunning = false;

	public void runEntirely() {
		while (!isDone) {
			step();
		}
	}

	protected void init() {
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
	
	// TODO : return list of lines/points instead of just 1 line, 
	// a step should be a triple of points in a left test
	public Line getCurrentStepLine() {
		return new Line(q, r);
	}

	@Override
	public LinkedList<Point> getCurrentStepPoints() {
		if (p == null || q == null || r == null) {
			return null;
		}

		LinkedList<Point> points = new LinkedList<Point>();
		points.add(p);
		points.add(q);
		points.add(r);

		return points;
	}
}
