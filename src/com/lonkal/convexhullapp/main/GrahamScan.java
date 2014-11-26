package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class GrahamScan extends ConvexHullAlgo {

	private LinkedList<Point> stepPointList;

	private int i = 2; // starts at the "3rd" array element

	private boolean isDone;
	private Point lowestRightestPt;

	private Point p, q, r;

	public GrahamScan(LinkedList<Point> list) {
		super(list);
		stepPointList = new LinkedList<Point>();

		init();
	}

	private class AngleComparator implements Comparator<Point> {

		private Point comparisonPoint; // assume it is lowest

		public AngleComparator(Point cp) {
			comparisonPoint = cp;
		}

		@Override
		public int compare(Point p1, Point p2) {

			// comparisonPoint is default bigger then anything, to be at the end
			if (p1 == comparisonPoint) {
				return 1;
			} else if (p2 == comparisonPoint){
				return -1;
			}

			// Basically, the angle of the points are calculated from the the
			// comparisonPoint
			// But if the point lies further left of the point, we look at the
			// right side angle instead
			// by doing pi - the left side angle

			Double angleOfP1 = Math.atan(Math.abs(p1.getY()
					- comparisonPoint.getY())
					/ Math.abs(p1.getX() - comparisonPoint.getX()));
			if (p1.getX() < comparisonPoint.getX()) {
				angleOfP1 = Math.PI - angleOfP1;
			}

			Double angleOfP2 = Math.atan(Math.abs(p2.getY()
					- comparisonPoint.getY())
					/ Math.abs(p2.getX() - comparisonPoint.getX()));
			if (p2.getX() < comparisonPoint.getX()) {
				angleOfP2 = Math.PI - angleOfP2;
			}

			// We are forced to do this since the values are doubles
			// and the difference could be below 1 which means it gets
			// casted to 0 :<

			if (angleOfP1 > angleOfP2) {
				return 1;
			} else if (angleOfP2 > angleOfP1) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	@Override
	protected void init() {

		// Find lowest and bottommost point
		Iterator<Point> it = pointList.iterator();
		lowestRightestPt = pointList.getFirst();
		while (it.hasNext()) {
			Point p = it.next();
			if (p.y > lowestRightestPt.y) {
				lowestRightestPt = p;
			} else if (p.y == lowestRightestPt.y) {
				lowestRightestPt = (lowestRightestPt.x > p.x) ? lowestRightestPt
						: p;
			}
		}

		// Sort all points based on angle
		Collections.sort(pointList, new AngleComparator(lowestRightestPt));

		convexHullList.add(lowestRightestPt);
		convexHullList.add(pointList.getFirst());
		convexHullList.add(pointList.get(1));
	}

	@Override
	public void step() {
		if (isDone) {
			return;
		}

		stepNum++;

		if (i >= pointList.size()) {
			isDone = true;
			convexHullList.add(lowestRightestPt);
			pointList.add(lowestRightestPt); // Add it back so it is rendered
			return;
		}

		p = convexHullList.get(convexHullList.size() - 2);
		q = convexHullList.get(convexHullList.size() - 1);
		r = pointList.get(i);

		if (Primitives.orientation(p, q, r) <= 1 || i == pointList.size() - 1) {
			convexHullList.add(r); // We're good
			i++;
		} else if (Primitives.orientation(p, q, r) == 2) {
			convexHullList.remove(q);
		}
	}

	@Override
	public boolean isDone() {
		return isDone;
	}

	@Override
	public Line getCurrentStepLine() {
		return null;
	}

	@Override
	public LinkedList<Point> getCurrentStepPoints() {
		if (p == null || q == null || r == null) {
			return null;
		}

		stepPointList.clear();
		stepPointList.add(p);
		stepPointList.add(q);
		stepPointList.add(r);
		return stepPointList;
	}

}
