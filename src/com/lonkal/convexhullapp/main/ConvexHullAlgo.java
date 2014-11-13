package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.LinkedList;

// Interface for all convex hull algorithms
public interface ConvexHullAlgo {

	/**
	 * Step once through an algorithm
	 */
	public void step();

	/**
	 * Check if the algorithm is done yet
	 * @return boolean of whether it is done
	 */
	public boolean isDone();

	/**
	 * Get the step number
	 * @return step number
	 */
	public int getCurrentStep();

	/**
	 * Return the pending list of points in convex hull
	 * @return linkedlist of ch pts
	 */
	public LinkedList<Point> getConvexHullList();

	/**
	 * Any intermediary line represented by a step
	 * @return Line of step
	 */
	public Line getCurrentStepLine();

}
