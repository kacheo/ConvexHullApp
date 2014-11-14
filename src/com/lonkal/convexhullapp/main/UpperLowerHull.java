package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class UpperLowerHull extends ConvexHullAlgo {

	public UpperLowerHull(LinkedList<Point> list) {
		super(list);
	}

	private LinkedList<Point> upperHull = new LinkedList<Point>();
	private LinkedList<Point> lowerHull = new LinkedList<Point>();
	private boolean upperHullIsDone;
	private boolean lowerHullIsDone;

	@Override
	public void step() {
		System.out.println(pointList);
		if (upperHullIsDone && lowerHullIsDone) {
			return;
		}
		
		if (!upperHullIsDone) {
			//step thru computation of upperhull
		}
		
		if (upperHullIsDone && !lowerHullIsDone) {
			//step thru lowerhull
		}
		
	}

	@Override
	public boolean isDone() {
		return upperHullIsDone && lowerHullIsDone;
	}

	@Override
	public Line getCurrentStepLine() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		convexHullList = new LinkedList<Point>();
		Collections.sort(pointList, new XSortComparator());
	}
	
	private class XSortComparator implements Comparator<Point>{

		@Override
		public int compare(Point p1, Point p2) {
			return p1.x - p2.x; //if positive, then p1 is more right then p2
		}
		
	}

}
