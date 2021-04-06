/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		int iPhilosophers = 0;
		try
		{

			if (!(argv.length == 0)) {

				try {
					iPhilosophers = Integer.parseInt(argv[0]);
				}
				catch(NumberFormatException e){
					reportException(e);
				}

				if(iPhilosophers < 1){
					System.out.println("Invalid number of philosophers");
					System.exit(1);
				}
				if(iPhilosophers == 1){
					System.out.println("The Philosopher starved to death since there is only 1 chopstick available.");
					System.exit(1);
				}
			}
			else {
				iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;
			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher[] aoPhilosophers = new Philosopher[iPhilosophers];

			System.out.println( iPhilosophers + " philosopher(s) came in for a dinner.");

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}


			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
