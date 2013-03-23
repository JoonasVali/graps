package ee.joonasvali.graps.layout.forcelayout;

public class ForceLayoutConfiguration {

	/**
	 * String strengths between nodes.
	 * @return
	 */
	public double getStringStrength() {				
		return 0.01;
	}	

	/**
	 * Mass constant. 
	 * @return
	 */	
	public double getMassConstant() {
		return 0.003;
	}
	
	/**
	 * The energy loss
	 * @return
	 */
	public double getDamping() {				
		return 0.65;
	}
	
	/**
	 * Repulsive force between node and every other node
	 * @return
	 */
	public double getCoulombRepulseStrength() {				
		return 100;
	}

	/**
	 * How far is the other node, when the force is no longer  
	 * calculated and returned 0. (measures x and y coordinates separately, 
	 * no diagonal calculations.) return 0 or negative if it limits are not used.
	 * @return the distance in standard measurement unit used by node.location()
	 */
	public double getCoulombForceMaxDistance() {	     
    return 2000;
  }

	/**
	 * The thread pauses before starting new iteration.
	 * Control this method if pause is needed.
	 * @return true if thread needs to be paused.
	 */	
	public boolean pause() {	      
    return false;
  }	
	
	/**
	 * @return how long is the sleeping period in pause cycle in milliseconds.
	 */
	public int pauseReactionTime() {	      
    return 20;
  }	
	
	/**
	 * The force in the center every node gets pulled towards to.
	 * Use 0 or less to disable.
	 * @return
	 */
	public double centerForcePullStrength(){
		return 1.4;
	}
	
	/**
	 * The sleep time between every iteration of force calculations
	 * @return time in milliseconds
	 */
	public int sleepTimeBetweenIterations(){
		return 20;
	}
	
	/**
	 * The graph is moved on the screen, so that nodes don't go to negative coordinates,
	 * This returns the edge margins in standard measurement units used by nodes.
	 * @return the edge margins in standard measurement units used by nodes.
	 */
	public int edgeMargins(){
		return 20;
	}
}
