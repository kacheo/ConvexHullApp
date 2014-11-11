package com.lonkal.convexhullapp.main;

// Interface for all convex hull algorithms
public interface ConvexHullAlgo {

	public void step();

	public boolean isDone();

	public int getCurrentStep();

}
