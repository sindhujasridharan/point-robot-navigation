package com.intelligentsystem.project.navigation;

import java.awt.Dimension;
import javax.swing.JFrame;
import com.intelligentsystem.project.environment.NavigationMap;


/**
 * Name: Sindhuja Sridharan
 * ID: 800935745
 * 
 * This is the Main class
 *
 */
public class RobotNavigationInitiator extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension PREFERRED_SIZE = new Dimension(1750, 1024);
	
	public static void main (String[] args)
	{
		JFrame frame = new JFrame("Online Robot Navigation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(PREFERRED_SIZE);
		frame.setContentPane(new NavigationMap());
		frame.pack();
	    frame.setVisible(true);
	  }
}
