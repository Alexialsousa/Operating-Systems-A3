
/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{


	static States[] states;
	static boolean talking = false;
	int lastToEat = -1;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		states = new States[piNumberOfPhilosophers];

		// setting all philosophers to thinking
		for(int i = 0; i< piNumberOfPhilosophers; i++){
			states[i] = States.thinking;
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
		// set philosopher to a hungry state
		states[piTID] = States.hungry;

		// check if last philosopher to eat wants to eat again and wait thread to prevent starvation
		if(lastToEat == piTID) {
			System.out.println("Philosopher " + (piTID + 1) + " just ate!! No eating twice in a row");
			try {
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// check if neighbours are eating before picking up both chopsticks
		while( states[Math.floorMod(piTID-1, states.length)] == States.eating || states[Math.floorMod(piTID+1, states.length)] == States.eating){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lastToEat = piTID;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		//signal other threads to eat
		notifyAll();
		states[piTID] = States.thinking;
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// wait if someone else is talking
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
