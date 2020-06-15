import java.awt.*;
import hsa.Console;
import java.lang.*; // to access Thread class

/*
   Oscar Jaewon Han
   Ms. Krasteva
   Jan 14th, 2020
   https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java/14323134- Used to help create the timer
	A timer is created that displays a seconds counter when level is timed; timer is used for player score

	Global Variable List

	Name            |   Data type    |   Description
       -----------------|------------ ---|-------------------------------------------------------------------------------------------------------------------------
	c               | Console        | instance variable of the console class
	elapsedSeconds  | static int     | used to display the timer that visibly increases every second in the main class and to calculate player score
	startTime       | int            | saves the time when the Timer class started
*/
public class Timer extends Thread
{
    private Console c;
    static int elapsedSeconds = 0;
    int startTime = (int) System.currentTimeMillis (); // saves the time when the Timer class started

    /*
	    Local Variable List- timeDisplay()

	    Name            |   Data type    |   Description
	   -----------------|------------ ---|-------------------------------------------------------------------------------------------------------------------------
	    elapsedTime     | int            | represents the difference between the current time and startTime
    */
    // timeDisplay() is used to calculate the timer (seconds) and display it in the main class
    public void timeDisplay ()
    {
	int elapsedTime;
	while (CrosswordPuzzle.timerDisplay) // timer is displayed as long as timerDisplay is true in the main class
	{
	    synchronized (c)
	    {
		c.setColor (Color.white);
		c.fillRect (530, 25, 160, 60);
		c.setColor (new Color (227, 86, 86)); // Color is set to red-pink
		c.fillRect (540, 35, 140, 40);
		// displays the background for the timer box
		c.setColor (Color.black);
		elapsedTime = (int) System.currentTimeMillis () - startTime; // difference between the curent time and startTime- creates timer
		elapsedSeconds = elapsedTime / 1000; // time difference is converted into seconds by dividing it by 1000
		c.setColor (Color.white);
		c.setFont (new Font ("SansSerif.BOLD", 1, 30));
		c.drawString (elapsedSeconds + "", 555, 65); // timer (seconds) is displayed in main class
	    }
	    try
	    {
		Thread.sleep (100);
	    }
	    catch (Exception e)
	    {

	    }
	}
    }


    public Timer (Console con)
    {
	c = con;
    }


    public void run ()
    {
	timeDisplay ();
    }
}


