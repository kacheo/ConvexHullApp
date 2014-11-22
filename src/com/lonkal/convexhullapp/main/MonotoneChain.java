package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class MonotoneChain extends ConvexHullAlgo {

	public MonotoneChain(LinkedList<Point> list) {
		super(list);
		init();
	}

	private LinkedList<Point> upperHull = new LinkedList<Point>();
	private LinkedList<Point> lowerHull = new LinkedList<Point>();
	private boolean upperHullIsDone;
	private boolean lowerHullIsDone;

	private Point p, q, r;
	private int i = 0;

	@Override
	public void step() {
		if (upperHullIsDone && lowerHullIsDone) {
			return;
		}

		stepNum++;

		if (!upperHullIsDone) {
			convexHullList = upperHull;

			if (i < 0) {
				upperHullIsDone = true;
				convexHullList = upperHull;
				i = 0; // Reset i back to 0 for lower hull calculation
				return;
			}

			// step thru computation of upperhull
			if (upperHull.size() >= 2
					&& Primitives.orientation(
							upperHull.get(upperHull.size() - 1),
							upperHull.get(upperHull.size() - 2),
							pointList.get(i)) <= 1) {
				p = upperHull.get(upperHull.size() - 1);
				q = upperHull.get(upperHull.size() - 2);
				r = pointList.get(i);
				upperHull.removeLast();
				return;
			} else {
				upperHull.addLast(pointList.get(i));
				r = pointList.get(i);
				i--;
				return;
			}
		}

		// if upper is done, do lower hull
		if (upperHullIsDone && !lowerHullIsDone) {
			// step thru lowerhull

			if (i >= pointList.size()) {
				lowerHullIsDone = true;

				// collinear cases, so check before removing
				if (lowerHull.getFirst() == upperHull.getFirst()) {
					lowerHull.removeFirst();
				}
				if (lowerHull.getLast() == upperHull.getLast()) {
					lowerHull.removeLast();
				}

				// combine upper and lower hull
				upperHull.addAll(0, lowerHull);

				// reassign
				convexHullList = upperHull;
				return;
			}

			// step thru computation of upperhull
			if (lowerHull.size() >= 2
					&& Primitives.orientation(
							lowerHull.get(lowerHull.size() - 1),
							lowerHull.get(lowerHull.size() - 2),
							pointList.get(i)) <= 1) {
				p = upperHull.get(upperHull.size() - 1);
				q = lowerHull.get(lowerHull.size() - 2);
				r = pointList.get(i);

				lowerHull.removeLast();
			} else {
				lowerHull.addLast(pointList.get(i));
				i++;
			}

			convexHullList = lowerHull;
		}

	}

	@Override
	public boolean isDone() {
		return upperHullIsDone && lowerHullIsDone;
	}

	@Override
	public Line getCurrentStepLine() {
		if (q == null || r == null)
			return null;

		return new Line(q, r);
	}

	public LinkedList<Point> getCurrentStepPoints() {
		if (p == null || q == null || r == null) {
			return null;
		}

		LinkedList<Point> points = new LinkedList<Point>();
		points.add(p);
		points.add(q);
		points.add(r);

		return points;
	}

	@Override
	public void init() {
		upperHull = new LinkedList<Point>();
		lowerHull = new LinkedList<Point>();

		Collections.sort(pointList, new XSortComparator());
		i = pointList.size() - 1; // start at last index for upperhull
									// calculation
	}

	private class XSortComparator implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			return (p1.x == p2.x) ? p1.y - p2.y : p1.x - p2.x; // if positive,
																// then p1 is
																// more right
																// then p2
		}

	}

}
