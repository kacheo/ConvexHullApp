package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class IncrementalCH extends ConvexHullAlgo {

	private int i = 3;
	private int tangentIndex = 0;

	private boolean isDone = false;
	private boolean topTangentDone = false;
	private boolean lowerTangentDone = false;

	private Point tRm, lRm;

	public IncrementalCH(LinkedList<Point> list) {
		super(list);
		init();
	}

	@Override
	protected void init() {
		Collections.sort(pointList, new XSortComparator());
		// add in clockwise position starting at leftmost pt
		convexHullList.add(pointList.get(0));
		convexHullList.add(pointList.get(1));
		convexHullList.add(pointList.get(2));
		convexHullList.add(pointList.get(0));
	}

	@Override
	public void step() {
		System.out.println(convexHullList);

		if (i >= pointList.size() || isDone) {
			isDone = true;
			return;
		}

		Point r = pointList.get(i); // need to find tangent lines to this point

		// Start top tangent
		if (!topTangentDone) {
			if (Primitives.orientation(convexHullList.get(tangentIndex),
					convexHullList.get(tangentIndex + 1), r) <= 1) {
				tRm = convexHullList.get(tangentIndex + 1);
				topTangentDone = true;
				tangentIndex = convexHullList.size() - 1;
			} else {
				tangentIndex++;
			}
		} else if (topTangentDone && !lowerTangentDone) {
			if (Primitives.orientation(convexHullList.get(tangentIndex),
					convexHullList.get(tangentIndex - 1), r) == 2
					|| Primitives.orientation(convexHullList.get(tangentIndex),
							convexHullList.get(tangentIndex - 1), r) == 0) {
				lRm = convexHullList.get(tangentIndex - 1);
				lowerTangentDone = true;

				if (tRm == lRm) {
					convexHullList.remove(tRm);
					convexHullList.add(tangentIndex - 1, r);
				} else {
					// remove all between tRm and lRm
					int tIndex = convexHullList.indexOf(tRm);
					int lIndex = convexHullList.indexOf(lRm);
					for (int i = tIndex; i < lIndex+1; i++) {
						convexHullList.remove(tIndex);
						tangentIndex--;
					}

					convexHullList.add(tangentIndex, r);
				}

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
		return null;
	}

	@Override
	public LinkedList<Point> getCurrentStepPoints() {
		return null;
	}

	private class XSortComparator implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			return (p1.x == p2.x) ?  p2.y - p1.y : p1.x - p2.x; // if positive,
																// then p1 is
																// more right
																// then p2
		}

	}
}
