package com.intelligentsystem.project.factory;

/**
 * Name: Sindhuja Sridharan
 * Student ID: 800935745
 *
 * This class has a from and to state to store the path 
 * 
 */

public class Path {

	private State fromState;
	private State toState;

	//constructor
	public Path(State fromState, State toState) {
		this.fromState = fromState;
		this.toState = toState;
	}

	//getter and setters
	public State getFromState() {
		return fromState;
	}

	public void setFromState(State fromState) {
		this.fromState = fromState;
	}

	public State getToState() {
		return toState;
	}

	public void setToState(State toState) {
		this.toState = toState;
	}


}
