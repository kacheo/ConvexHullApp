package com.lonkal.convexhullapp.algorithms;

import java.awt.Point;
import java.util.LinkedList;

import com.lonkal.convexhullapp.main.ConvexHullApp;
import com.lonkal.convexhullapp.util.Line;
import com.lonkal.convexhullapp.util.Primitives;

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

		ConvexHullApp.log("Finding leftmost point...");
		Point leftMostPoint = getLeftmostPoint(pointList);
		// Add leftmost point to the convex hull list
		convexHullList.add(leftMostPoint);

		p = leftMostPoint;
		q = pointList.getFirst();
		r = pointList.getFirst();
	}

	@Override
	public void step() {
		if (isDone) {
			ConvexHullApp.log("Finished");
			return;
		}
		
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
			
			ConvexHullApp.log("Resetting index search based on new point " + p);
			return;
		}

		r = pointList.get(currentIndexOfElement);
		if (q == p || Primitives.orientation(p, q, r) == 1) {
			ConvexHullApp.log("Left turn! so we move up from q");
			q = r;
		} else {
			ConvexHullApp.log("Right turn!");
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
		return null;
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
		points.add(p);

		return points;
	}
}
