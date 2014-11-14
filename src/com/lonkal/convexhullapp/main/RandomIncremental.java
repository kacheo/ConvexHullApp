package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;

public class RandomIncremental extends ConvexHullAlgo {

	public RandomIncremental(LinkedList<Point> list) {
		super(list);
	}

	protected void init() {
		// Pick 3 points for a "triangle"
		Point p, q, r;

		do {
			Collections.shuffle(pointList);

			p = pointList.get(0);
			q = pointList.get(1);
			r = pointList.get(2);
		} while (Primitives.orientation(p, q, r) == 0);
		
		convexHullList.add(p);
		convexHullList.add(q);
		convexHullList.add(r);
	}

	@Override
	public void step() {
		// Iterate pointList
		
		// If p is in current CH, remove it
		
		// Else add to current CH and create new CH list
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public int getCurrentStep() {
		return 0;
	}

	@Override
	public LinkedList<Point> getConvexHullList() {
		return convexHullList;
	}

	@Override
	public Line getCurrentStepLine() {
		return null;
	}

}
