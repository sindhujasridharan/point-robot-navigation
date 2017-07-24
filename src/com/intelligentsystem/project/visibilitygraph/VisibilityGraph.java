package com.intelligentsystem.project.visibilitygraph;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.intelligentsystem.project.environment.Obstacle;
import com.intelligentsystem.project.environment.RobotEnvironment;
import com.intelligentsystem.project.factory.Action;
import com.intelligentsystem.project.factory.ActionType;
import com.intelligentsystem.project.factory.State;


/**
 * Name: Sindhuja Sridharan
 * Student ID: 800935745
 * This class is used to compute the visibility graph
 */

public class VisibilityGraph {

	private RobotEnvironment environment;

	//constructor
	public VisibilityGraph(RobotEnvironment environment) {
		this.environment = environment;
	}


	//generate visibility graph
	public List<Action> generateVisibilityGraph(State currentState, State goalState) {
		List<Action> actions = new ArrayList<Action>();

		Point startPoint = new Point();
		startPoint.x = currentState.getX();
		startPoint.y = currentState.getY();

		Point goalPoint = new Point();
		goalPoint.x = goalState.getX();
		goalPoint.y = goalState.getY();

		List<Point> availblePositions = new ArrayList<Point>();
		availblePositions.add(startPoint);
		availblePositions.add(goalPoint);

		//clone all obstacles
		for (Obstacle o: environment.getObstacles()) {
			for(Point p: o.getVertices()){
				availblePositions.add(p);
			}		
		}

		for(Point vertex: availblePositions) {
			if(vertex.equals(startPoint)) {
				// do nothing
			} else {
				if(isVisible(startPoint, vertex)) {
					State vState = new State(vertex.x, vertex.y);
					Action nAction = new Action(ActionType.MOVE, vState);
					actions.add(nAction);
				} else {
					//do nothing
				}
			}
		}

		return actions;
	}

	//check if a end point is visible from the start point
	private boolean isVisible(Point start, Point end) {
		Line line = new Line(start, end);
		Line2D.Double line2D = new Line2D.Double(start,end);
		for(Obstacle vObs: environment.getObstacles()) {
			if(isLineInObstacle(line, vObs)) {
				return false;
			}
			List<Line> obsLines = new ArrayList<Line>();
			int size = vObs.getVertices().size();
			Point x = vObs.getVertices().get(size-1);
			for(Point p : vObs.getVertices()) {
				obsLines.add(new Line(x, p));
				x = p;
			}
			for(Line obsLine: obsLines) {

				Line2D.Double obsLine2D = new Line2D.Double(obsLine.getStart(), obsLine.getEnd());
				if(!lineIntersectLine(line2D, obsLine2D)){
					return false;
				}
			}
		}
		return true;
	}

	//check if given line intersects with any other line
	private boolean lineIntersectLine(Line2D line1, Line2D line2) {
		if(line1.intersectsLine(line2) && !line1.equals(line2) &&
				!line2.getP1().equals(line1.getP1()) && !line2.getP2().equals(line1.getP1()) &&
				!line2.getP1().equals(line1.getP2()) && !line2.getP2().equals(line1.getP2())){

			return false;
		}

		return true;

	}

	//check if given line is inside any obstacle
	private boolean isLineInObstacle(Line line, Obstacle obstacle) {
		double midx = (line.getStart().getX() + line.getEnd().getX()) / 2.0;
		double midy = (line.getStart().getY() + line.getEnd().getY()) / 2.0;
		double delta = 1;
		return (obstacle.contains(midx-delta,midy-delta) &&
				obstacle.contains(midx-delta,midy+delta) &&
				obstacle.contains(midx+delta,midy-delta) &&
				obstacle.contains(midx+delta,midy+delta));
	}

	//get all points on a line
	@SuppressWarnings("unused")
	private List<Point2D> getAllPointsOnLine(Line2D line) {
		List<Point2D> points = new ArrayList<Point2D>();
		double precision = 1.0;
		double deltaX = line.getX1() < line.getX2() ? precision : -1 * precision;
		double deltaY = line.getY1() < line.getY2() ? precision : -1 * precision;

		double diffX =  Math.abs(line.getX2() - line.getX1());
		double diffY = Math.abs(line.getY2() - line.getY1());
		double error = diffX - diffY;

		double y = line.getY1();
		double x = line.getX1();
		while(Math.abs(x - line.getX2()) > 0.9 || (Math.abs(y - line.getY2()) > 0.9)) {
			Point2D p = new Point2D.Double(x, y);
			double newError = 2*error;
			if (newError > -diffY) {
				error -= diffY;
				x += deltaX;
			}
			if (newError < diffX) {
				error += diffX;
				y += deltaY;
			}
			points.add(p);
		}
		return points;
	}

	//generate visibility graph
	public List<State> generateVisibilityGraphHC(State currentState, State goalState) {
		List<State> states = new ArrayList<State>();

		Point startPoint = new Point();
		startPoint.x = currentState.getX();
		startPoint.y = currentState.getY();

		Point goalPoint = new Point();
		goalPoint.x = goalState.getX();
		goalPoint.y = goalState.getY();

		List<Point> availblePositions = new ArrayList<Point>();
		availblePositions.add(startPoint);
		availblePositions.add(goalPoint);

		//clone all obstacles
		for (Obstacle o: environment.getObstacles()) {
			for(Point p: o.getVertices()){
				availblePositions.add(p);
			}		
		}

		for(Point vertex: availblePositions) {
			if(vertex.equals(startPoint)) {
				// do nothing
			} else {
				if(isVisible(startPoint, vertex)) {
					State vState = new State(vertex.x, vertex.y);
					states.add(vState);
				} else {
					//do nothing
				}
			}
		}

		return states;
	}
}
