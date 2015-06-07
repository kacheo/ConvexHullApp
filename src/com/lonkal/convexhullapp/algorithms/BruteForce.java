package com.lonkal.convexhullapp.algorithms;

import java.awt.Point;
import java.util.LinkedList;

import com.lonkal.convexhullapp.util.Line;

public class BruteForce extends ConvexHullAlgo {

	public BruteForce(LinkedList<Point> list) {
		super(list);
		init();
	}

	@Override
	protected void init() {
	}

	@Override
	public void step() {		
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public Line getCurrentStepLine() {
		return null;
	}

	@Override
	public LinkedList<Point> getCurrentStepPoints() {
		return null;
	}

}
