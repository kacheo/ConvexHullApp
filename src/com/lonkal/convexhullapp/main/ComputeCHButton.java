package com.lonkal.convexhullapp.main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ComputeCHButton implements ActionListener {

	private GraphPanel graphPanel;

	public ComputeCHButton(GraphPanel gp) {
		graphPanel = gp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// default is jarvis
		jarvisMarch();
	}

	public void changeCHMethod() {
		// to change the method
	}

	public void jarvisMarch() {
		LinkedList<Point> convexHullList = new LinkedList<Point>();		
		LinkedList<Point> pointList = graphPanel.getPointList();
		
		graphPanel.addCHList(convexHullList);
		//convexHullList.add(getLeftmostPoint(pointList));
		
		Point hullPoint = getLeftmostPoint(pointList);
		Point endPoint;
		do{
			endPoint = pointList.getFirst();
			convexHullList.add(hullPoint);

			//Walk through the pointList
			for (Point p : pointList) {
				System.out.println(p);
				//graphPanel.updateStep(hullPoint, p);
				//System.out.println(Primitives.orientation(hullPoint, endPoint, p));
				if (endPoint == hullPoint || Primitives.orientation(hullPoint, endPoint, p) == 2) {
					endPoint = p;
				}
			}
			hullPoint = endPoint;

			graphPanel.repaint();
			System.out.println(convexHullList);

		} while (endPoint != convexHullList.getFirst());
		convexHullList.add(convexHullList.getFirst());
		System.out.println(convexHullList);
	}

	public Point getLeftmostPoint(LinkedList<Point> list) {
		Point leftMostPoint = list.getFirst();
		for (Point p : list) {
			if (p.x < leftMostPoint.x) {
				leftMostPoint = p;
			}
		}

		return leftMostPoint;
	}
}
