import org.w3c.dom.ls.LSOutput;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{


	static String[] philStates;
	static boolean talking = false;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		philStates = new String[piNumberOfPhilosophers];
		for(int i = 0; i< piNumberOfPhilosophers; i++){
			philStates[i] = "thinking";
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		// keeps looping if neighbour is eating
		while( philStates[Math.floorMod(piTID-1, philStates.length)] == "eating" || philStates[Math.floorMod(piTID+1, philStates.length)] == "eating"){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		//allow other threads to eat
		notifyAll();
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		if(talking){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		talking = false;
		notifyAll();
	}
}

// EOF
