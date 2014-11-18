package com.lonkal.convexhullapp.main;

import java.awt.Point;

public class Primitives {

	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Counterclockwise, Left
	// 2 --> Clockwise, Right
	public static int orientation(Point p, Point q, Point r) {
		int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		if (val == 0)
			return 0;

		return (val > 0) ? 1 : 2;
	}
}
