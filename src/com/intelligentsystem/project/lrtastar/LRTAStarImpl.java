package com.intelligentsystem.project.lrtastar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.intelligentsystem.project.environment.RobotEnvironment;
import com.intelligentsystem.project.factory.Action;
import com.intelligentsystem.project.factory.ActionType;
import com.intelligentsystem.project.factory.LRTAResultKey;
import com.intelligentsystem.project.factory.Path;
import com.intelligentsystem.project.factory.State;
import com.intelligentsystem.project.visibilitygraph.VisibilityGraph;

/**
 * Name: Sindhuja Sridharan
 * Student ID: 800935745

 * This class has the LRTAStar Agent algorithm implementation as per the textbook
 */

public class LRTAStarImpl {

	private HashMap<LRTAResultKey, State> resultTable = new HashMap<LRTAResultKey, State>();
	private HashMap<State, Double> heuristicTable = new HashMap<State, Double>();
	private RobotEnvironment environment;
	private State state = null;
	private Action action = null;
	private State startState;
	private State goalState;
	private List<Path> path = new ArrayList<>();

	//constructor
	public LRTAStarImpl() {}
	public LRTAStarImpl(RobotEnvironment environment) {
		this.environment = environment;
		this.startState = new State(environment.getStartPosition().x, environment.getStartPosition().y);
		this.goalState = new State(environment.getEndPosition().x, environment.getEndPosition().y);
	}

	//getters and setters for the result path
	public List<Path> getPath() {
		return path;
	}

	public void setPath(List<Path> path) {
		this.path = path;
	}
	
	//implementation of LRTA* search agent
	public void LRTAStarSearch() {
		VisibilityGraph visibilityGraph = new VisibilityGraph(environment);
		State currentState = startState;
		while(currentState != null) {
			if(action != null) {
				if(action.getType().equals(ActionType.STOP)){return;}				
			}
			if(currentState.goalTest(goalState)) {
				action.setType(ActionType.STOP);				
			} else {
				if(!heuristicTable.containsKey(currentState)) {
					heuristicTable.put(currentState, currentState.computeHeuristicValue(goalState));
				}

				//if state is not the start state
				//update state's heuristic value to the minimum heuristic value 
				if(state != null) {
					LRTAResultKey resultKey = new LRTAResultKey(state, action);
					resultTable.put(resultKey, currentState);
					double minFValue = Double.MAX_VALUE;
					List<Action> actions = visibilityGraph.generateVisibilityGraph(state, goalState); 
					for(Action a: actions) {
						double fValue = getLrtaCost(state, a, resultTable.get(new LRTAResultKey(state, a)));
						if(fValue < minFValue) {
							minFValue = fValue;							
						}
					}
					heuristicTable.put(state, minFValue);
				}
				double minFValue = Double.MAX_VALUE;
				action = new Action(ActionType.STOP, null);
				List<Action> actions = visibilityGraph.generateVisibilityGraph(currentState, goalState); 
				List<State> visiStates = new ArrayList<>();
				for(Action a: actions) {
					double fValue = getLrtaCost(currentState, a, resultTable.get(new LRTAResultKey(currentState, a)));
					visiStates.add(a.getLocation());
					if(fValue < minFValue) {
						minFValue = fValue;
						action.setLocation(a.getLocation());
						action.setType(a.getType());
					}
				}				
			}

			state = currentState;
			currentState = action.getLocation();
			Path p = new Path(state, currentState);
			path.add(p);
		}
	}

	//compute the LRTA* cost
	private double getLrtaCost(State state, Action action, State newState){
		double fValue =0;
		if(newState == null) {
			fValue = action.getLocation().computeHeuristicValue(goalState);
		} else {
			double hValue = heuristicTable.get(newState);
			double kValue = state.computeHeuristicValue(newState);
			fValue = hValue + kValue;
		}
		return fValue;
	}

}
