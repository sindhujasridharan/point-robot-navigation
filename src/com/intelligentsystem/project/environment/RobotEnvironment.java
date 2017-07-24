package com.intelligentsystem.project.environment;

import java.awt.Point;
import java.util.List;

/**
 * Name: Sindhuja Sridharan
 * ID: 800935745
 *
 * This class stores the details of the environment created by the user
 *
 */
public class RobotEnvironment {

	private Point startPosition;
	private Point endPosition;
	private List<Obstacle> obstacles;

	//constructor
	public RobotEnvironment() {

	}

	public RobotEnvironment(Point startPosition, Point endPosition, List<Obstacle> obstacles) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.obstacles = obstacles;
	}

	//getter and setters
	public Point getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Point startPosition) {
		this.startPosition = startPosition;
	}

	public Point getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Point endPosition) {
		this.endPosition = endPosition;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(List<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

}
