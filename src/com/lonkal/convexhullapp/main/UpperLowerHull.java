package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class UpperLowerHull extends ConvexHullAlgo {

	public UpperLowerHull(LinkedList<Point> list) {
		super(list);
		init();
	}

	private LinkedList<Point> upperHull = new LinkedList<Point>();
	private LinkedList<Point> lowerHull = new LinkedList<Point>();
	private boolean upperHullIsDone;
	private boolean lowerHullIsDone;
	
	private Point p,q,r;
	private int i = 3;

	@Override
	public void step() {
		System.out.println(upperHull);
		if (upperHullIsDone && lowerHullIsDone) {
			return;
		}
		
		if (!upperHullIsDone) {
			
			if (i >= pointList.size()) {
				upperHullIsDone = true;
				convexHullList = upperHull;
				return;
			}
			
			//step thru computation of upperhull
			
			// if left
			if (Primitives.orientation(p, q, r) == 1) {
				upperHull.remove(q);
				upperHull.add(r);
				q = r;
				r = pointList.get(i);
 //remove intermediary point
			} else if (Primitives.orientation(p, q, r) == 2) {
				upperHull.add(r);
				p = q;
				q = r;
				r = pointList.get(i);
			}
			i++;
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
		upperHull = new LinkedList<Point>();
		lowerHull = new LinkedList<Point>();
		
		Collections.sort(pointList, new XSortComparator());
		
		p = pointList.get(0);
		q = pointList.get(1);
		r = pointList.get(2);
		
		upperHull.add(p);
		upperHull.add(q);
		upperHull.add(r);

		lowerHull.add(pointList.get(0));
		lowerHull.add(pointList.get(1));
		lowerHull.add(pointList.get(2));
	}
	
	private class XSortComparator implements Comparator<Point>{

		@Override
		public int compare(Point p1, Point p2) {
			return p1.x - p2.x; //if positive, then p1 is more right then p2
		}
		
	}

}
