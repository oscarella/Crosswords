import java.awt.*;
import hsa.Console;
import java.lang.*; // to access Thread class

/*
   Oscar Jaewon Han
   Ms. Krasteva
   Jan 14th, 2020
	Displays a splash screen as a introduction to the program
*/
public class SplashScreen extends Thread
{
    private Console c;

    /*
	    Local Variable List- introDisplay()

	    Name            |   Data type    |   Description
	   -----------------|------------ ---|-------------------------------------------------------------------------------------------------------------------------
	    down            | String         | each character of word CROSS is drawn vertically using a for loop- displays first part of program title
	    across          | String         | each character of word WORD is drawn horizontally using a for loop- displays second part of program title
    */
    // introDisplay() creates and displays an animated splashScreen
    public void introDisplay ()
    {
	String down;
	String across;
	synchronized (c)
	{
	    down = "CROSS"; // represents first portion of title- CROSS
	    across = "WORD"; // represents second protion of title- WORD
	    c.setColor (new Color (255, 251, 130)); // Color is set to light yellow
	    c.fillRect (0, 0, 720, 20);
	    c.fillRect (0, 20, 20, 560);
	    c.fillRect (0, 580, 720, 20);
	    c.fillRect (700, 20, 20, 560);
	    // displays the outer borders of the splash screen
	    c.setColor (Color.white);
	    c.fillRect (20, 20, 680, 560);
	    // draws a white background within the outer borders
	    c.setColor (new Color (176, 176, 176, 100)); // Color is set to light gray
	    for (int y = 0 ; y <= 520 ; y += 80)
	    {
		for (int x = 0 ; x <= 680 ; x += 80)
		{
		    c.fillRect (20 + x, 20 + y, 40, 40);
		}
	    }
	    // displays a checkered background starting from the 1st row going 2 rows down
	    for (int y = 0 ; y <= 520 ; y += 80)
	    {
		for (int x = 0 ; x <= 600 ; x += 80)
		{
		    c.fillRect (60 + x, 60 + y, 40, 40);
		}
	    }
	    // displays a checkered background starting from the 2nd row going 2 rows down
	    c.setColor (Color.black);
	    c.fillRect (50, 200, 100, 100);
	    c.fillRect (120, 350, 100, 100);
	    c.setColor (new Color (125, 125, 125));
	    c.fillRect (120, 250, 100, 100);
	    c.setColor (new Color (167, 167, 167));
	    c.fillRect (30, 300, 100, 100);
	    c.fillRect (200, 270, 100, 100);
	    c.fillRect (150, 150, 100, 100);
	    c.setColor (Color.black);
	    c.fillRect (220, 220, 100, 100);
	    c.setColor (new Color (125, 125, 125));
	    c.fillRect (250, 120, 100, 100);
	    c.fillRect (300, 320, 140, 130);
	    c.setColor (new Color (167, 167, 167));
	    c.fillRect (320, 220, 100, 100);
	    c.fillRect (220, 370, 100, 100);
	    // displays random rectangles ranging from gray to black as foreground for the crossword splashcreen
	    c.setColor (Color.red);
	    c.fillRect (160, 30, 420, 60);
	    c.setColor (Color.white);
	    c.drawRect (170, 40, 400, 40);
	    // background for author title
	    c.setFont (new Font ("SansSerif.BOLD", 1, 35));
	    c.drawString ("Oscar Han Productions", 180, 70);
	    // displays production company name- OSCAR HAN PRODUCTIONS
	    c.setColor (Color.black);
	    c.fillRect (480, 125, 210, 350);
	    c.setColor (new Color (242, 145, 143));
	    c.fillRect (490, 135, 190, 330);
	    // background for definitions box- part of splash screen
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	    c.drawString ("Down", 495, 185);
	    c.setFont (new Font ("Serif", 1, 15));
	    c.drawString ("1. a mark, object, or figure", 495, 205);
	    c.drawString ("formed by two short inter", 495, 225);
	    c.drawString ("secting lines or pieces", 495, 245);
	    // definition for cross; down
	    c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	    c.drawString ("Across", 495, 315);
	    c.setFont (new Font ("Serif", 1, 15));
	    c.drawString ("2. a single distinct meaning", 495, 335);
	    c.drawString ("ful element of speech or", 495, 355);
	    c.drawString ("writing, used with others to", 495, 375);
	    c.drawString ("form a sentence", 495, 395);
	    // definition for word; across
	    for (int i = 0 ; i <= 240 ; i += 60) // displays a vertical crossword line
	    {
		c.setColor (Color.black);
		c.fillRect (280, 150 + i, 70, 70);
		c.setColor (Color.white);
		c.fillRect (280, 150 + i, 60, 60);
		c.setColor (Color.black);
		c.drawRect (280, 150 + i, 60, 60);
		// displays a crossword box
		if (i == 0) // displays word referencing the definition on the first box of the crossword
		{
		    c.setFont (new Font ("SansSerif", 1, 20));
		    c.drawString ("1", 285, 165);
		}
		try
		{
		    Thread.sleep (400);
		}
		catch (Exception e)
		{
		}
	    }
	    for (int i = 0 ; i <= 180 ; i += 60) // displays a horizontal crossword line
	    {
		if (i != 60) // doesn't draw a box that interferes with the vertical crossword line
		{
		    c.setColor (Color.black);
		    c.fillRect (220 + i, 270, 60, 70);
		    c.setColor (Color.white);
		    c.fillRect (220 + i, 270, 60, 60);
		    c.setColor (Color.black);
		    c.drawRect (220 + i, 270, 60, 60);
		    // displays a crossword box
		    if (i == 0) // displays word referencing the definition on the first box of the crossword
		    {
			c.setFont (new Font ("SansSerif", 1, 20));
			c.drawString ("2", 225, 285);
		    }
		    try
		    {
			Thread.sleep (400);
		    }
		    catch (Exception e)
		    {
		    }
		}
	    }
	    c.setColor (Color.black);
	    c.setFont (new Font ("Helvetica.BOLD", 1, 50));
	    for (int i = 0 ; i <= 4 ; i++) // letters of down (CROSS) are individually drawn vertically using a for loop
	    {
		c.drawString (String.valueOf (down.charAt (i)), 285, 200 + i * 60); // letter of cross is displayed
		try
		{
		    Thread.sleep (400);
		}
		catch (Exception e)
		{
		}
	    }
	    for (int i = 0 ; i <= 3 ; i++) // letters of across (WORD) are individually drawn horizontally using a for loop
	    {
		c.drawString (String.valueOf (across.charAt (i)), 225 + i * 60, 320); // letter of cross is displayed
		try
		{
		    Thread.sleep (400);
		}
		catch (Exception e)
		{
		}
	    }
	}
    }


    public SplashScreen (Console con)
    {
	c = con;
    }


    public void run ()
    {
	introDisplay ();
    }
}


