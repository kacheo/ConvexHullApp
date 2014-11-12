package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.LinkedList;

// Interface for all convex hull algorithms
public interface ConvexHullAlgo {

	public void step();

	public boolean isDone();

	public int getCurrentStep();

	public LinkedList<Point> getConvexHullList();

	public Line getCurrentStepLine();

}
