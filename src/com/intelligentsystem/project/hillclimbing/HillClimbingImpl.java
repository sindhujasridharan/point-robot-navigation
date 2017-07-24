package com.intelligentsystem.project.hillclimbing;

import java.util.ArrayList;
import java.util.List;

import com.intelligentsystem.project.environment.RobotEnvironment;
import com.intelligentsystem.project.factory.Path;
import com.intelligentsystem.project.factory.State;
import com.intelligentsystem.project.visibilitygraph.VisibilityGraph;


/**
 * Name: Sindhuja Sridharan
 * ID: 800935745
 * 
 * This class contains the implementation of the hill climbing 
 * algorithm with random restart
 *
 */


public class HillClimbingImpl {

	private RobotEnvironment environment;
	private State startState;
	private State goalState;
	private List<Path> path = new ArrayList<Path>(); 

	//constructor
	public HillClimbingImpl(RobotEnvironment environment) {
		this.environment = environment;
		this.startState = new State(environment.getStartPosition().x, environment.getStartPosition().y);
		this.goalState = new State(environment.getEndPosition().x, environment.getEndPosition().y);
	}

	//getters and setters
	public List<Path> getPath() {
		return path;
	}

	public void setPath(List<Path> path) {
		this.path = path;
	}

	/*
	 * Basic hill climbing algorithm implementation
	 */
	public void hillClimbingSearch() {
		//
		VisibilityGraph visibilitGraph = new VisibilityGraph(environment);
		
		State currentState = startState;
		
		while(currentState != null) {
			if(currentState.equals(goalState)) {
				return;
			}
			State nextState = null;
			//generate successor nodes
			List<State> successorStates = visibilitGraph.generateVisibilityGraphHC(currentState, goalState);
			//choose node with minimum heuristic value
			double minCost = currentState.computeHeuristicValue(goalState); 
			for(State state:successorStates) {
				double cost = state.computeHeuristicValue(goalState);
				if(cost < minCost) {
					nextState = state;
					minCost = cost;
				}
							
			}
			
			if(nextState != null) {
			path.add(new Path(currentState, nextState));
			}
			currentState = nextState;
		}
		
		return;
		
	}
	
		
}
