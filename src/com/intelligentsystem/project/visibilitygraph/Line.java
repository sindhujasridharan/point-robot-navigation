package com.intelligentsystem.project.visibilitygraph;

import java.awt.Point;

/**
 * Name: Sindhuja Sridharan
 * ID: 800935745

 * This class is used to represent start and end
 * points of a Line
 *
 */
public class Line {
	private Point start;
	private Point end;

	//constructor
	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	//getters and setters
	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

}
