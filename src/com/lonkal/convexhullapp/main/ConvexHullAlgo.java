package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.LinkedList;

// Interface for all convex hull algorithms
public abstract class ConvexHullAlgo {
	protected LinkedList<Point> pointList;
	protected LinkedList<Point> convexHullList;
	protected int stepNum;

	public ConvexHullAlgo(LinkedList<Point> list) {
		convexHullList = new LinkedList<Point>();

		if (list == null) {
			throw new IllegalArgumentException("Passed in list was null");
		}
		pointList = list;

		if (pointList.size() < 3) {
			throw new IllegalArgumentException(
					"Fail... the number of points has to be 3 or more");
		}
	}
	
	protected abstract void init();
	
	/**
	 * Step once through an algorithm
	 */
	public abstract void step();

	/**
	 * Check if the algorithm is done yet
	 * @return boolean of whether it is done
	 */
	public abstract boolean isDone();

	/**
	 * Get the step number
	 * @return step number
	 */
	public int getCurrentStep() {
		return stepNum;
	}

	/**
	 * Return the pending list of points in convex hull
	 * @return linkedlist of ch pts
	 */
	public LinkedList<Point> getConvexHullList() {
		return convexHullList;
	}

	/**
	 * Any intermediary line represented by a step
	 * @return Line of step
	 */
	public abstract Line getCurrentStepLine();

}
