package com.intelligentsystem.project.environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.intelligentsystem.project.factory.Path;
import com.intelligentsystem.project.hillclimbing.HillClimbingImpl;
import com.intelligentsystem.project.lrtastar.LRTAStarImpl;

/**
 * Name: Sindhuja Sridharan
 * ID: 800935745
 *
 * Used to get user input from the GUI and trigger the corresponding action
 *
 */

public class NavigationMap extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JLabel displayInstruction = new JLabel();
	private JLabel displayInstruction1 = new JLabel();
	private JLabel displayTimeTaken = new JLabel();
	private JLabel displayTimeTakenHC = new JLabel();
	private int inputIndicator = 0;
	private Point obsPreviousVertex = new Point();
	private boolean obsStatus = false;
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private List<Point> obsVerticesList = new ArrayList<Point>();
	private Point startPosition = new Point();
	private Point endPosition = new Point();
	private RobotEnvironment environment = new RobotEnvironment();
	private boolean mouseActionEnabled = true;
	private List<Path> pathHC =  null;
	private List<Path> path = null;
	int counter = 0;
	private Long totalTime;
	private Long totalTimeHC;
	Random random = new Random();

	//constructor
	public NavigationMap() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	//request focus to the key listener
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	//display GUI
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g.create();

		if(inputIndicator == 0){
			displayInstruction.setText("Click on the screen to set the Robot Start position");
		}
		displayInstruction.setFont(new Font("Verdana",1,20));
		displayInstruction.setBounds(0, 0, 1750, 100);
		add(displayInstruction);

		displayInstruction1.setFont(new Font("Verdana",1,20));
		displayInstruction1.setBounds(0, 30, 1750, 100);
		add(displayInstruction1);

		displayTimeTaken.setFont(new Font("Verdana",1,20));
		displayTimeTaken.setBounds(1450, 10, 1750, 100);
		add(displayTimeTaken);
		displayTimeTakenHC.setFont(new Font("Verdana",1,20));
		displayTimeTakenHC.setBounds(1450, 30, 1750, 100);
		add(displayTimeTakenHC);

		//display state position
		if(startPosition != null) {
			draw(g2d, null, startPosition);
		}
		//display end position
		if(endPosition != null) {
			draw(g2d, null, endPosition);
		}

		//display the obstacles in the scene
		for(Obstacle obs: obstacles) {
			List<Point> verti = obs.getVertices();
			int x[] = new int [verti.size()];
			int y[] = new int [verti.size()];
			for(int i=0; i<verti.size(); i++) {
				x[i] = verti.get(i).x;
				y[i] = verti.get(i).y;
			}
			g.setColor(obs.getColor());
			g.fillPolygon(x, y, verti.size());
		}
		
		
		if(!obsVerticesList.isEmpty()) {
			Point prevPoint = new Point();

			prevPoint = obsVerticesList.get(0);
			Iterator<Point> it = obsVerticesList.iterator();
			while (it.hasNext())
			{
				Point curPoint = it.next();
				draw(g2d, prevPoint, curPoint);
				prevPoint = curPoint;
			}

			if (obsStatus) {
				draw(g2d, prevPoint, obsVerticesList.get(0));
			} else {
				draw(g2d, prevPoint, obsPreviousVertex);
			}
			
			//draw hill climbing path
			if(pathHC != null) {
				displayTimeTakenHC.setText("Total time taken[Hill Climbing]: " +totalTimeHC + " ms");
				for(Path p: pathHC) {
					Point startPoint = new Point();
					startPoint.x = p.getFromState().getX();
					startPoint.y = p.getFromState().getY();

					Point endPoint = new Point();
					endPoint.x = p.getToState().getX();
					endPoint.y = p.getToState().getY();

					Line2D  line= new Line2D.Double(startPoint, endPoint);
					g2d.setColor(Color.red);
					g2d.setStroke(new BasicStroke(12));
					g2d.draw(line);

				}
			}
			
			//draw LRTA* path
			if(path != null) {
				displayTimeTaken.setText("Total time taken[LRTA*]: " +totalTime + " ms");			
				for(Path p: path) {
					Point startPoint = new Point();
					startPoint.x = p.getFromState().getX();
					startPoint.y = p.getFromState().getY();

					Point endPoint = new Point();
					endPoint.x = p.getToState().getX();
					endPoint.y = p.getToState().getY();

					Line2D  line= new Line2D.Double(startPoint, endPoint);
					g2d.setColor(Color.orange);
					g2d.setStroke(new BasicStroke(5));
					g2d.draw(line);

				}
			}
			
		}
		repaint();

	}

	//method used to draw the start position, end position and the obstacles
	private void draw(Graphics2D g, Point p1, Point p2)
	{
		if(p1 == null) {
			g.setColor(Color.black);
			g.fillOval(p2.x, p2.y, 8, 8);
		} else {
			int x1 = p1.x;
			int y1 = p1.y;

			int x2 = p2.x;
			int y2 = p2.y;

			g.setColor(Color.green.darker());
			g.drawLine(x1 + 3, y1 + 3, x2 + 3, y2 + 3);
			g.drawLine(x1 + 4, y1 + 4, x2 + 4, y2 + 4);
			g.drawLine(x1 + 5, y1 + 5, x2 + 5, y2 + 5);

			g.setColor(Color.red);
			g.fillOval(x1, y1, 8, 8);

			g.setColor(Color.blue);
			g.fillOval(x2, y2, 8, 8);
		}
	}

	//mouse events
	@Override
	public void mouseMoved(MouseEvent e) {
		if(mouseActionEnabled){
			obsPreviousVertex.x = e.getX();
			obsPreviousVertex.y = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(mouseActionEnabled) {
			int x = e.getX();
			int y = e.getY();
			Point newVertexPosition = new Point(x, y);
			if(inputIndicator == 0) {
				startPosition = newVertexPosition;
				inputIndicator++;

				displayInstruction.setText("Click on the screen to set the Robot Goal position");
				repaint();
			} else if(inputIndicator == 1) {
				endPosition = newVertexPosition;
				inputIndicator++;
				displayInstruction.setText("Click and drag on the screen to create obstacles. Press n to complete the current polygon and start a new obstacle.");

				displayInstruction1.setText("Press o when the obstacles are complete !!");

				add(displayInstruction1);
				repaint();
			} else {
				if(obsStatus)
				{
					obsVerticesList.clear();
					obsStatus = false;
				}
				obsVerticesList.add(newVertexPosition);
				repaint();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//method to add an obstacle to the list of obstacles
	private void addObstacles(List<Point> ps) {

		int x[] = new int [ps.size()];
		int y[] = new int [ps.size()];
		for(int i=0; i<ps.size(); i++) {
			x[i] = ps.get(i).x;
			y[i] = ps.get(i).y;
		}
		GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,x.length);
		polygon.moveTo(x[0], y[0]);

		for (int index = 1; index < x.length; index++) {
			polygon.lineTo(x[index], y[index]);
		};
		polygon.closePath();

		Obstacle o = new Obstacle(ps, polygon, new Color(0,0,182,155));
		obstacles.add(o);

		counter++;
		repaint();
	}


	//key board event listener
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == 'n') {
			obsStatus = true;
			List<Point> tempVe = new ArrayList<Point>(obsVerticesList.size());
			for (Point p: obsVerticesList) {
				tempVe.add((Point)p.clone());
			}
			addObstacles(tempVe);
		} else if(e.getKeyChar() == 'o') {
			mouseActionEnabled = false;
			environment = new RobotEnvironment(startPosition, endPosition, obstacles);
			displayInstruction1.setText("Press l to start Robot navigation using LRTA*/Press h to start Robot navigation using Hill Climbing !!");
		} else if(e.getKeyChar() == 'l') {
			//environment = new RobotEnvironment(startPosition, endPosition, obstacles);
			LRTAStarImpl lrtaState = new LRTAStarImpl(environment);
			Long startTime = System.currentTimeMillis();
			lrtaState.LRTAStarSearch();
			Long endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			path = lrtaState.getPath();
			repaint();

		} else if(e.getKeyChar() == 'h') {
			HillClimbingImpl hillClimbing = new HillClimbingImpl(environment);
			Long startTime = System.currentTimeMillis();
			hillClimbing.hillClimbingSearch();
			Long endTime = System.currentTimeMillis();
			totalTimeHC = endTime - startTime;
			pathHC = hillClimbing.getPath();

			repaint();
		} 

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


}
