package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.LinkedList;

// Interface for all convex hull algorithms
public abstract class ConvexHullAlgo {
	protected LinkedList<Point> pointList;
	protected LinkedList<Point> convexHullList;
	protected int stepNum;

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
