package com.intelligentsystem.project.factory;

/**
 * Name: Sindhuja Sridharan
 * Student ID: 800935745
 *
 * This class represents the State [x,y] postion 
 * 
 */
public class State {
	private int x;
	private int y;

	//constructor
	public State(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	//getters & setters
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	//computes the heuristic value based on straight line distance between the state and the goal state 
	public double computeHeuristicValue(State state) {
		double hVal = 0;
		int x1 = state.getX();
		int y1 = state.getY();

		//shortest distance from current state to goal state
		hVal = Math.sqrt(((x1 - x) * (x1 - x)) + ((y1 - y) * (y1 - y)));

		return hVal;
	}

	//checks if the current state is the goal state
	public boolean goalTest(State goalState) {
		if(goalState.getX() == x && goalState.getY() == y) {
			return true;
		} else {
			return false;
		}
	}
	
	//state to string
	@Override
	public String toString() {
		return "State [x=" + x + ", y=" + y + "]";
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


}
