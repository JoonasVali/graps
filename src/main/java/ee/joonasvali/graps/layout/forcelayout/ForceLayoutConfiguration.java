package ee.joonasvali.graps.layout.forcelayout;

public class ForceLayoutConfiguration {
	
	private volatile double stringStrength = 0.01;
	private volatile double massConstant = 0.003;
	private volatile double damping = 0.65; 
	private volatile double repulse = 100;
	private volatile int repulseMaxDistance = 0;
	private volatile boolean pause = false;
	private volatile int pauseReaction = 20;
	private volatile double centerStrength = 0.005;
	private volatile int sleepTime = 20;
	private volatile int edgeMargins = 20;
	private boolean randomizeGraph = false;
	

	/**
	 * String strengths between nodes.
	 * @return
	 */
	public double getStringStrength() {				
		return stringStrength;
	}	

	public void setStringStrength(double str){
		this.stringStrength = str;
	}
	
	/**
	 * Mass constant. 
	 * @return
	 */	
	public double getMassConstant() {
		return massConstant;
	}
	
	public void setMassConstant(double mass){
		this.massConstant = mass;
	}
	
	/**
	 * The energy loss
	 * @return
	 */
	public double getDamping() {				
		return damping;
	}
	
	public void setDamping(double damping){
		this.damping = damping;
	}
	
	/**
	 * Repulsive force between node and every other node
	 * @return
	 */
	public double getCoulombRepulseStrength() {				
		return repulse;
	}
	
	public void setCoulombRepulseStrength(double str){
		this.repulse = str;
	}

	/**
	 * How far is the other node, when the force is no longer  
	 * calculated and returned 0. (measures x and y coordinates separately, 
	 * no diagonal calculations.) return 0 or negative if it limits are not used.
	 * @return the distance in standard measurement unit used by node.location()
	 */
	public int getCoulombForceMaxDistance() {	     
    return repulseMaxDistance;
  }
	
	public void setCoulombForceMaxDistance(int max){
		this.repulseMaxDistance = max;
	}

	/**
	 * The thread pauses before starting new iteration.
	 * Control this method if pause is needed.
	 * @return true if thread needs to be paused.
	 */	
	public boolean isPause() {	      
    return pause;
  }	
	
	public void setPause(boolean pause){
		this.pause = pause;
	}
	
	/**
	 * @return how long is the sleeping period in pause cycle in milliseconds.
	 */
	public int getPauseReactionTime() {	      
    return pauseReaction;
  }	
	
	public void setPauseReactionTime(int pauseReaction){
		this.pauseReaction = pauseReaction;
	}
	/**
	 * The force in the center every node gets pulled towards to.
	 * Use 0 or less to disable.
	 * @return
	 */
	public double getCenterForcePullStrength(){
		return centerStrength;
	}
	
	public void setCenterForcePullStrength(double str){
		this.centerStrength = str;
	}
	
	/**
	 * The sleep time between every iteration of force calculations
	 * @return time in milliseconds
	 */
	public int getSleepTimeBetweenIterations(){
		return sleepTime;
	}
	
	public void setSleepTimeBetweenIterations(int sleepTime){
		this.sleepTime = sleepTime;
	}
	
	/**
	 * The graph is moved on the screen, so that nodes don't go to negative coordinates,
	 * This returns the edge margins in standard measurement units used by nodes.
	 * @return the edge margins in standard measurement units used by nodes.
	 */
	public int getEdgeMargins(){
		return edgeMargins;
	}
	
	public void setEdgeMargins(int margins){
		this.edgeMargins = margins;
	}

	public boolean isRandomizeGraph() {
  	return randomizeGraph;
  }

	public void setRandomizeGraph(boolean randomizeGraph) {
  	this.randomizeGraph = randomizeGraph;
  }
	
	
}
