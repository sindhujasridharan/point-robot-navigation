package com.intelligentsystem.project.factory;

/**
 * Name: Sindhuja Sridharan
 * Student ID: 800935745

 * This class represents the action of the LRTA* agent
 * It contains the action type and the location details.
 */

public class Action {
	private ActionType type;
	private State location;
	
	//constructor
	public Action(ActionType type, State location) {
		super();
		this.type = type;
		this.location = location;
	}

	//getters & setters
	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public State getLocation() {
		return location;
	}

	public void setLocation(State location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Action other = (Action) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
}
