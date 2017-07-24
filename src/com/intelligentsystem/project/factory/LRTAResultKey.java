package com.intelligentsystem.project.factory;

/**
 * Name: Sindhuja Sridharan
 * Student ID: 800935745

 * This class contains the details of a state and its corresponding action.
 * 
 */

public class LRTAResultKey {
	private State state;
	private Action action;

	//constructor
	public LRTAResultKey(State state, Action action) {
		super();
		this.state = state;
		this.action = action;
	}

	//getters and setters
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		LRTAResultKey other = (LRTAResultKey) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	};

}
