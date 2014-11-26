package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class GrahamScan extends ConvexHullAlgo {

	private LinkedList<Point> stepPointList;

	public GrahamScan(LinkedList<Point> list) {
		super(list);
		init();
	}

	private class AngleComparator implements Comparator<Point> {

		private Point comparisonPoint; // assume it is lowest

		public AngleComparator(Point cp) {
			comparisonPoint = cp;
		}

		@Override
		public int compare(Point p1, Point p2) {

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
		Point lowestRightestPt = pointList.getFirst();
		while (it.hasNext()) {
			Point p = it.next();
			if (p.y > lowestRightestPt.y) {
				lowestRightestPt = p;
			} else if (p.y == lowestRightestPt.y) {
				lowestRightestPt = (lowestRightestPt.x > p.x) ? lowestRightestPt
						: p;
			}
		}
		pointList.remove(lowestRightestPt);

		// Sort all points based on angle
		Collections.sort(pointList, new AngleComparator(lowestRightestPt));
		stepPointList = pointList;
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
		return stepPointList;
	}

}
