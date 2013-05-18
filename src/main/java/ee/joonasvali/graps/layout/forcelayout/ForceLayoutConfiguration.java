package ee.joonasvali.graps.layout.forcelayout;

import ee.joonasvali.graps.layout.LayoutConfiguration;

public class ForceLayoutConfiguration extends LayoutConfiguration{
	
	public static String STRING_STRENGTH = "string.strength";
	public static String MASS_CONSTANT = "mass.const";
	public static String DAMPING = "damping";
	public static String REPULSE = "repulse";
	public static String REPULSE_MAX_DISTANT = "repulse.max.distant";
	public static String PAUSE = "pause";
	public static String PAUSE_REACTION = "pause.reaction";
	public static String CENTER_STRENGTH= "center.strength";
	public static String SLEEP_TIME = "sleep.time";
	public static String EDGE_MARGINS = "edge.margins";
	public static String RANDOMIZE_GRAPH = "randomize.graph";
	
	private final double stringStrength = 0.01;
	private final double massConstant = 0.003;
	private final double damping = 0.65; 
	private final double repulse = 100;
	private final int repulseMaxDistance = 0;
	private final boolean pause = false;
	private final int pauseReaction = 20;
	private final double centerStrength = 0.005;
	private final int sleepTime = 20;
	private final int edgeMargins = 20;
	private final boolean randomizeGraph = false;
	
	public ForceLayoutConfiguration() {
		setValue(STRING_STRENGTH, stringStrength);
		setValue(MASS_CONSTANT, massConstant);
		setValue(DAMPING, damping);
		setValue(REPULSE, repulse);
		setValue(REPULSE_MAX_DISTANT, repulseMaxDistance);
		setValue(PAUSE, pause);
		setValue(PAUSE_REACTION, pauseReaction);
		setValue(CENTER_STRENGTH, centerStrength);
		setValue(SLEEP_TIME, sleepTime);
		setValue(EDGE_MARGINS, edgeMargins);
		setValue(RANDOMIZE_GRAPH, randomizeGraph);
	}
	/**
	 * String strengths between nodes.
	 * @return
	 */
	public double getStringStrength() {				
		return getValue(STRING_STRENGTH);
	}	

	public void setStringStrength(double str){
		setValue(STRING_STRENGTH, str);
	}
	
	/**
	 * Mass constant. 
	 * @return
	 */	
	public double getMassConstant() {
		return getValue(MASS_CONSTANT);
	}
	
	public void setMassConstant(double mass){
		setValue(MASS_CONSTANT, mass);		
	}
	
	/**
	 * The energy loss
	 * @return
	 */
	public double getDamping() {				
		return getValue(DAMPING);
	}
	
	public void setDamping(double damping){
		setValue(DAMPING, damping);
	}
	
	/**
	 * Repulsive force between node and every other node
	 * @return
	 */
	public double getCoulombRepulseStrength() {				
		return getValue(REPULSE);
	}
	
	public void setCoulombRepulseStrength(double str){
		setValue(REPULSE, str);
	}

	/**
	 * How far is the other node, when the force is no longer  
	 * calculated and returned 0. (measures x and y coordinates separately, 
	 * no diagonal calculations.) return 0 or negative if it limits are not used.
	 * @return the distance in standard measurement unit used by node.location()
	 */
	public int getCoulombForceMaxDistance() {	     
    return getValue(REPULSE_MAX_DISTANT);
  }
	
	public void setCoulombForceMaxDistance(int max){
		setValue(REPULSE_MAX_DISTANT, max);
	}

	/**
	 * The thread pauses before starting new iteration.
	 * Control this method if pause is needed.
	 * @return true if thread needs to be paused.
	 */	
	public boolean isPause() {	      
    return getValue(PAUSE);
  }	
	
	public void setPause(boolean pause){
		setValue(PAUSE, pause);
	}
	
	/**
	 * @return how long is the sleeping period in pause cycle in milliseconds.
	 */
	public int getPauseReactionTime() {	      
    return getValue(PAUSE_REACTION);
  }	
	
	public void setPauseReactionTime(int pauseReaction){
		setValue(PAUSE_REACTION, pauseReaction);
	}
	/**
	 * The force in the center every node gets pulled towards to.
	 * Use 0 or less to disable.
	 * @return
	 */
	public double getCenterForcePullStrength(){
		return getValue(CENTER_STRENGTH);
	}
	
	public void setCenterForcePullStrength(double str){
		setValue(CENTER_STRENGTH, str);
	}
	
	/**
	 * The sleep time between every iteration of force calculations
	 * @return time in milliseconds
	 */
	public int getSleepTimeBetweenIterations(){
		return getValue(SLEEP_TIME);
	}
	
	public void setSleepTimeBetweenIterations(int sleepTime){
		setValue(SLEEP_TIME, sleepTime);
	}
	
	/**
	 * The graph is moved on the screen, so that nodes don't go to negative coordinates,
	 * This returns the edge margins in standard measurement units used by nodes.
	 * @return the edge margins in standard measurement units used by nodes.
	 */
	public int getEdgeMargins(){
		return getValue(EDGE_MARGINS);
	}
	
	public void setEdgeMargins(int margins){
		setValue(EDGE_MARGINS, margins);
	}

	public boolean isRandomizeGraph() {
  	return getValue(RANDOMIZE_GRAPH);
  }

	public void setRandomizeGraph(boolean randomizeGraph) {
  	setValue(RANDOMIZE_GRAPH, randomizeGraph);
  }
	
	
}

