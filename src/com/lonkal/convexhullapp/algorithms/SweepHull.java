package com.lonkal.convexhullapp.algorithms;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.lonkal.convexhullapp.util.Line;
import com.lonkal.convexhullapp.util.Primitives;

public class SweepHull extends ConvexHullAlgo {

	private int i = 3;
	private int tangentIndex = 0;

	private boolean isDone = false;
	private boolean topTangentDone = false;
	private boolean lowerTangentDone = false;

	private Point tRm, lRm, p, q, r;

	public SweepHull(LinkedList<Point> list) {
		super(list);
		init();
	}

	@Override
	protected void init() {
		Collections.sort(pointList, new XSortComparator());
		// add in clockwise position starting at leftmost pt
		if (Primitives.orientation(pointList.get(0), pointList.get(1),
				pointList.get(2)) == 1) {
			// Left turn means we add the pts, where the 3rd point in X-coord is 2nd
			convexHullList.add(pointList.get(0));
			convexHullList.add(pointList.get(2));
			convexHullList.add(pointList.get(1));
			convexHullList.add(pointList.get(0));
		} else {
			convexHullList.add(pointList.get(0));
			convexHullList.add(pointList.get(1));
			convexHullList.add(pointList.get(2));
			convexHullList.add(pointList.get(0));
		}
	}

	@Override
	public void step() {
		if (i >= pointList.size() || isDone) {
			isDone = true;
			return;
		}
		stepNum++;

		r = pointList.get(i); // need to find tangent lines to this point

		if (convexHullList.size() <= 2) {
			convexHullList.add(pointList.get(i));
			i++;
			return;
		}

		// Start top tangent
		if (!topTangentDone) {
			p = convexHullList.get(tangentIndex);
			q = convexHullList.get(tangentIndex + 1);
			if (Primitives.orientation(p, q, r) <= 1) {
				tRm = convexHullList.get(tangentIndex + 1);
				topTangentDone = true;
				tangentIndex = convexHullList.size() - 1;
			} else {
				tangentIndex++;
			}
		} else if (topTangentDone && !lowerTangentDone) {

			p = convexHullList.get(tangentIndex);
			q = convexHullList.get(tangentIndex - 1);
			if (Primitives.orientation(p, q, r) == 2
					|| Primitives.orientation(p, q, r) == 0) {
				lRm = convexHullList.get(tangentIndex - 1);
				lowerTangentDone = true;

				if (tRm == lRm) {
					// If same tangent point, we remove it and add the new point
					// in place.
					convexHullList.remove(tRm);
					convexHullList.add(tangentIndex - 1, r);
				} else {
					int tIndex = convexHullList.indexOf(tRm);
					int lIndex = convexHullList.indexOf(lRm);

					if (convexHullList.size() > 4) {
						// Remove all between tRm and lRm
						for (int i = tIndex; i <= lIndex; i++) {
							convexHullList.remove(tIndex);
							tangentIndex--;
						}
						convexHullList.add(tIndex, r);
					} else {
						// Special case when its a triangle, we add to the lower
						// index instead
						convexHullList.add(lIndex + 1, r);
					}
				}

				// Reset the tangentIndex for next iteration
				tangentIndex = 0;

			} else {
				tangentIndex--;
			}
		} else if (topTangentDone && lowerTangentDone) {
			i++;
			topTangentDone = false;
			lowerTangentDone = false;
		}
	}

	@Override
	public boolean isDone() {
		return isDone;
	}

	@Override
	public Line getCurrentStepLine() {
		if (i >= pointList.size()) {
			return null;
		}

		Point t = new Point(pointList.get(i).x, 0);
		Point b = new Point(pointList.get(i).x, 800);
		return new Line(t, b);

	}

	@Override
	public LinkedList<Point> getCurrentStepPoints() {
		if (p == null || q == null || r == null) {
			return null;
		}

		LinkedList<Point> stepPoints = new LinkedList<Point>();
		stepPoints.add(p);
		stepPoints.add(q);
		stepPoints.add(r);

		return stepPoints;
	}

	private class XSortComparator implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			// p1 is bigger on a tie if it is more down the p2
			return (p1.x == p2.x) ? p1.y - p2.y : p1.x - p2.x;
		}

	}
}
