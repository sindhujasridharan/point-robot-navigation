package com.intelligentsystem.project.environment;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: Sindhuja Sridharan
 * ID: 800935745
 *
 * This class stores the details of an obstacle in the environment
 *
 */
public class Obstacle {

	private List<Point> vertices;
	private GeneralPath path;
	private Color color;

	//constructor
	public Obstacle(){}
	public Obstacle(List<Point> vertices) {
		this.vertices = vertices;
	}

	public Obstacle(List<Point> vertices, GeneralPath path, Color color) {
		this.vertices = vertices;
		this.path = path;
		this.color = color;
	}

	//getter and setters
	public List<Point> getVertices() {
		return vertices;
	}

	public void setVertices(List<Point> vertices) {
		this.vertices = vertices;
	}

	public GeneralPath getPath() {
		return path;
	}
	public void setPath(GeneralPath path) {
		this.path = path;
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	//convert to string
	@Override
	public String toString() {
		return "Obstacle [vertices=" + vertices + "]";
	}

	//method to make a copy
	public Obstacle clone() {
		Obstacle oClone = new Obstacle();
		List<Point> pClone = new ArrayList<Point>(this.vertices.size());
		for (Point p: this.vertices) {
			  pClone.add((Point)p.clone());
			}
		oClone.vertices = pClone;
		oClone.path = path;

		return oClone;
	}

	//checks if a obstacle contains a point
	public boolean contains(Point point) {
		return path.contains(point.getX(), point.getY());
	}

	//checks if a obstacle contains a point
	public boolean contains(double x, double y) {
		return path.contains(x, y);
	}


}
