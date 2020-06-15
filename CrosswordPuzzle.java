import java.awt.*; // import all of the classes in java.awt
import hsa.Console; // import only the Console class
import java.lang.*; // to access Thread class
import java.io.*;
import javax.swing.JOptionPane;

/*
    Oscar Jaewown Han
    January 14th,2019
    Ms. Krasteva
    Crossword Puzzle Final Improved
	This program consists of three themed crossword puzzles, each with a minimum of 16 words built across and down with clues. The puzzles can either be timed and untimed, and contains either
	a win screen or a lose screen depending on if the user successfully completes the crossword puzzle or if the user chooses to give up/ the timer runs up (for timed levels). If the level has
	been successfully solved, the user can choose to view a leaderboard from mainMenu(), which ranks the highest scores for each puzzle depending on word solved and time taken.

	Global Variable List

	Name            |   Data type    |   Description
       -----------------|------------ ---|--------------------------------------------------------------------------------------------------------------------------------------------------------
	c               | Console        | instance variable of the console class
	mainNum         | int            | represents user option from main menu (play, instructions, leaderboard, exit)
	TITLE_ONE       | final String   | used in menuBackground() and mainMenu()- displays first part of program title: CROSS
	TITLE_TWO       | final String   | used in menuBackground() and mainMenu()- displays second part of program title: WORD
	DARK_GRAY       | final Color    | used in menuBackground() and mainMenu()- used for graphics to draw the background for program title
	puzzleLevel     | int            | used to determine whether the level is timed or untimed
	pauseMenu       | boolean        | used to determine whether the user wants to go back to main menu- user presses '!'
	playerName      | String         | playerName records the user's name during play and displays it on the leaderboard
	timerDisplay    | static boolean | displays timer- timerDisplay is true if user is playing a timed level
	levelNum        | int            | determines what level the user plays (1. Environment, 2. Computers, 3. Canadian)
	answerGrid      | String[][]     | used to create a crossword puzzle grid as the user plays; values of guessGrid are placed above crossword puzzle grid
	guessGrid       | String[][]     | values are displayed on top of the crossword puzzle grid during play and compared with the values of answerGrid to determine whether the user has won
	linesFileDef    | String[][]     | stores the different clues within the answer key files for display
	defNum          | int            | used to determine what clue to display within linesFileDef
	guessWord       | boolean        | guessWord determines whether the user is entering a number or guess during the game and is used to display cheat options if level is timed
	numberString    | String         | used to take in input of and display user input for number within guessbox
	guess           | String         | used to take in input of and display user input for guess within guessbox

*/
public class CrosswordPuzzle
{
    Console c;
    int mainNum;
    final String TITLE_ONE = "CROSS"; // upper portion of program title is CROSS
    final String TITLE_TWO = "WORD"; // lower portion of program title is WORD
    final Color DARK_GRAY = new Color (122, 122, 121);
    int puzzleLevel;
    boolean pauseMenu;
    String playerName = ""; // default playerName is empty
    static boolean timerDisplay;
    int levelNum;
    String[] [] answerGrid;
    String[] [] guessGrid;
    String[] linesFileDef;
    int defNum;
    boolean guessWord;
    String numberString;
    String guess;
    public CrosswordPuzzle ()  // class constructor
    {
	c = new Console (30, 90, "Crossword Puzzle"); // creates new instance of Console for use by the class; title: "Crossword Puzzle"
    }

    // splashScreen method displays the splashScreen using threads
    public void splashScreen ()
    {
	SplashScreen ss = new SplashScreen (c);
	ss.start ();
	try
	{
	    ss.join (); // other methods run after splashScreen() is done executing
	}
	catch (InterruptedException e)
	{
	}
    }


    // menuBackground displays a basic background for mainMenu(), instructions(), highScore(), and goodbye()
    public void menuBackground ()
    {
	synchronized (c)
	{
	    c.clear (); // screen is cleared
	    c.setColor (new Color (255, 251, 130)); // color is set to light yellow
	    c.fillRect (0, 0, 720, 20);
	    c.fillRect (0, 20, 20, 560);
	    c.fillRect (0, 580, 720, 20);
	    c.fillRect (700, 20, 20, 560);
	    // displays outer borders of the game
	    c.setColor (Color.white);
	    c.fillRect (20, 20, 680, 560);
	    // displays white background within the inner borders
	    c.setColor (new Color (176, 176, 176, 100)); // Color is set to light gray
	    for (int y = 0 ; y <= 520 ; y += 80) // displays gray squares starting from the first row going down two rows each time
	    {
		for (int x = 0 ; x <= 680 ; x += 80)
		{
		    c.fillRect (20 + x, 20 + y, 40, 40);
		}
	    }
	    for (int y = 0 ; y <= 520 ; y += 80) // displays gray squares starting from the secon row going down two rows each time
	    {
		for (int x = 0 ; x <= 600 ; x += 80)
		{
		    c.fillRect (60 + x, 60 + y, 40, 40);
		}
	    }
	    // displays checkered background with a pattern of light gray/white boxes
	    for (int i = 0 ; i <= 200 ; i += 50) // for loop draws crossword boxes as the background for the title of the game- CROSSWORD
	    {
		c.setColor (Color.black);
		c.fillRect (235 + i, 80, 55, 55);
		c.setColor (Color.white);
		c.fillRect (235 + i, 30, 50, 50);
		c.fillRect (235 + i, 80, 50, 50);
		c.setColor (Color.black);
		c.drawRect (235 + i, 30, 50, 50);
		c.drawRect (235 + i, 80, 50, 50);
	    }
	    c.setColor (DARK_GRAY);
	    c.fillRect (236, 81, 49, 49);
	    c.setColor (Color.black);
	    c.fillRect (485, 30, 55, 55);
	    c.setColor (DARK_GRAY);
	    c.fillRect (486, 31, 49, 49);
	    // Dark gray boxes- part of the title background
	    c.setFont (new Font ("SansSerif.BOLD", 1, 50));
	    for (int i = 0 ; i <= 4 ; i++) // displays first part of the title- CROSS; each indivdual letter is drawn on one crossword box
	    {
		c.setColor (Color.yellow);
		c.drawString (String.valueOf (TITLE_ONE.charAt (i)), 236 + i * 50, 72);
		c.setColor (Color.black);
		c.drawString (String.valueOf (TITLE_ONE.charAt (i)), 238 + i * 50, 70);
	    }
	    for (int i = 0 ; i <= 3 ; i++) // displays second part of the title- WORD- each individual letter is drawn on one crossword box
	    {
		c.setColor (Color.yellow);
		c.drawString (String.valueOf (TITLE_TWO.charAt (i)), 286 + i * 50, 122);
		c.setColor (Color.black);
		c.drawString (String.valueOf (TITLE_TWO.charAt (i)), 288 + i * 50, 120);
	    }
	}
    }


    /*
	Local Variable List- mainMenu()

	Name            |   Data type    |   Description
       -----------------|------------ ---|---------------------------------------------------------------------------------------------------------------
	gameSetup       | boolean        | Used to check whether the user has inputted a valid option during the player information phase before playing
	COUNTDOWN       | final String[] | Values within COUNTDOWN are used to display a countdown message before the game is loaded
	mainOption      | char           | Takes in input from the user in the main menu home screen
	mainPlay        | char           | Takes additional user information (name, gamemode) if the user decides to play
    */
    // mainMenu is used so that user can either choose to play, read instructions, view leaderboard, or exit from the main menu
    // if user chooses to play, additional player information is asked in mainMenu before the user plays
    public void mainMenu ()
    {
	boolean gameSetup = false;
	final String[] COUNTDOWN = {"3", "2", "1", "GO"}; // values 3,2,1,GO are used for the countdown message
	char mainOption = ' ';
	char mainPlay;
	pauseMenu = false; // pauseMenu is used to display an indicator depending on what num (option) the user presses
	menuBackground (); // menuaBackground() displays a checkered background and title
	c.setColor (Color.black);
	c.setFont (new Font ("SansSerif", 1, 17));
	c.drawString ("OSCAR", 635, 40);
	c.drawString ("HAN", 660, 60);
	c.drawString ("PRODUCTIONS", 575, 80);
	// Productions Credit
	c.setFont (new Font ("Serif", 10, 15));
	c.drawString ("*Press the number corresponding to your option and then enter to confirm.", 130, 575);
	// Basic instructions on the bottom of the screen
	while (true) // while loop repeats until the user chooses an option from main menu
	{
	    for (int i = 0 ; i <= 300 ; i += 100) // displays boxes with the different options from main menu
	    {
		c.setColor (Color.black);
		c.fillRect (210, 150 + i, 300, 80);
		if (pauseMenu == true && (mainNum - 1) * 100 == i) // color indicator that shows user's choice if user has pressed 1-4
		{
		    c.setColor (new Color (245, 149, 149));
		}
		else // option box is filled with a basic white color
		{
		    c.setColor (Color.white);
		}
		c.fillRect (215, 155 + i, 290, 70); // draws option boxes
	    }
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
	    c.drawString ("1", 220, 180);
	    c.drawString ("2", 220, 280);
	    c.drawString ("3", 220, 380);
	    c.drawString ("4", 220, 480);
	    // different number option (1-4)
	    c.setFont (new Font ("SansSerif.BOLD", 1, 50));
	    c.drawString ("P", 250, 210);
	    c.drawString ("I", 250, 310);
	    c.drawString ("L", 250, 410);
	    c.drawString ("E", 250, 510);
	    // First character of the titles of the options
	    c.setFont (new Font ("SansSerif", 1, 30));
	    c.drawString ("lay", 285, 210);
	    c.drawString ("nstructions", 270, 310);
	    c.drawString ("eaderboard", 285, 410);
	    c.drawString ("xit", 285, 510);
	    // Choices corresponding to the different number options
	    mainOption = c.getChar (); // mainOption keeps track of the user's input from main menu
	    if (mainNum == 1 && mainOption == '\n' && pauseMenu == true) // if user chooses to play and the option is registered- takes in user info
	    {
		c.setColor (new Color (0, 0, 0, 210));
		c.fillRect (0, 0, 720, 600);
		// translucent black background covers the screen
		for (int i = 0 ; i <= 200 ; i += 50)
		{
		    c.setColor (Color.black);
		    c.fillRect (235 + i, 80, 55, 55);
		    c.setColor (Color.white);
		    c.fillRect (235 + i, 30, 50, 50);
		    c.fillRect (235 + i, 80, 50, 50);
		    c.setColor (Color.black);
		    c.drawRect (235 + i, 30, 50, 50);
		    c.drawRect (235 + i, 80, 50, 50);
		}
		// displays crossword background of the title- CROSSWORD
		c.setColor (DARK_GRAY);
		c.fillRect (236, 81, 49, 49);
		c.setColor (Color.black);
		c.fillRect (485, 30, 55, 55);
		c.setColor (DARK_GRAY);
		c.fillRect (486, 31, 49, 49);
		// part of the crossword background for the title
		c.setFont (new Font ("SansSerif.BOLD", 1, 50));
		for (int i = 0 ; i <= 4 ; i++) // displays first part of the title- CROSS
		{
		    c.setColor (Color.yellow);
		    c.drawString (String.valueOf (TITLE_ONE.charAt (i)), 236 + i * 50, 72);
		    c.setColor (Color.black);
		    c.drawString (String.valueOf (TITLE_ONE.charAt (i)), 238 + i * 50, 70);
		}
		for (int i = 0 ; i <= 3 ; i++) // displays second part of the title- WORD
		{
		    c.setColor (Color.yellow);
		    c.drawString (String.valueOf (TITLE_TWO.charAt (i)), 286 + i * 50, 122);
		    c.setColor (Color.black);
		    c.drawString (String.valueOf (TITLE_TWO.charAt (i)), 288 + i * 50, 120);
		}
		c.setColor (Color.black);
		c.fillRect (210, 150, 300, 80);
		c.setColor (new Color (245, 149, 149));
		c.fillRect (215, 155, 290, 70);
		c.setColor (Color.black);
		c.setFont (new Font ("SansSerif.BOLD", 1, 30));
		c.drawString ("1", 220, 180);
		c.setFont (new Font ("SansSerif.BOLD", 1, 50));
		c.drawString ("P", 250, 210);
		c.setFont (new Font ("SansSerif", 1, 30));
		c.drawString ("lay", 285, 210);
		// As user has chosen to play, option (1 PLAY) is highlighted
		c.setColor (Color.white);
		c.setFont (new Font ("SansSerif.BOLD", 1, 30));
		c.drawString ("Enter player name:", 225, 270);
		c.setFont (new Font ("SansSerif.BOLD", 2, 20));
		c.drawString ("*max 15 characters", 265, 295);
		// Input prompt for player name
		c.setFont (new Font ("SansSerif", 2, 15));
		c.drawString ("*Press enter after input for registration", 235, 580);
		// Instructions for input
		while (true) // while loop repeats until user has entered a valid player name
		{
		    c.setColor (new Color (245, 149, 149)); // color is set to light pink
		    c.fillRect (210, 310, 300, 50);
		    c.setColor (Color.white);
		    c.fillRect (215, 315, 290, 40);
		    // displays text box to display player name
		    c.setColor (Color.black);
		    c.setFont (new Font ("Helvetica.BOLD", 1, 25));
		    c.drawString (playerName, 225, 345);
		    // displays player name for display
		    mainPlay = c.getChar (); // user input after main menu and before game starts
		    if (mainPlay == '\n' && playerName.length () == 0) // Error- if user tries to enter an empty player name
		    {
			JOptionPane.showMessageDialog (null, "Your name should at least have one character.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		    else if (mainPlay == '\n' && playerName.length () >= 1) // Loop is exited if user enters a proper player name
		    {
			c.setColor (Color.black);
			c.fillRect (210, 310, 300, 50);
			c.setColor (new Color (245, 149, 149)); // color is set to light pink
			c.fillRect (215, 315, 290, 40);
			c.setColor (Color.black);
			c.setFont (new Font ("Helvetica.BOLD", 1, 25));
			c.drawString (playerName, 225, 345);
			// text box background for player name changes: white --> light pink
			break;
		    }
		    else if ((int) mainPlay == 8) // if user presses backspace
		    {
			if (playerName.length () == 1) // playerName is empty if length is 1
			{
			    playerName = "";
			}
			else if (playerName.length () > 1) // if length of playerName is 2 or more, the last character is deleted using substring
			{
			    playerName = playerName.substring (0, playerName.length () - 1);
			}
		    }
		    else // if user enters a character meant to be part of the name
		    {
			try
			{
			    if (mainPlay != ' ') // As long as the user doesn't input ' ' as part of playerName
			    {
				playerName += mainPlay; // mainPlay is added to the player name
			    }
			    if (playerName.length () > 15) // Exception- if player name exceeds 15 characters
			    {
				throw new Exception ();
			    }
			}
			catch (Exception e)  // Exception message
			{
			    JOptionPane.showMessageDialog (null, "Your name shouldn't exceed 15 characters.", "Error", JOptionPane.ERROR_MESSAGE);
			    playerName = playerName.substring (0, playerName.length () - 1); // last character of playerName is deleted
			}
		    }
		}
		while (true) // After player name is repeated, while loop repeats until user either chooses a time/ untimed level
		{
		    c.setColor (Color.white);
		    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
		    c.drawString ("Choose your level:", 225, 400);
		    // Prompt for level choice
		    c.setColor (new Color (245, 149, 149)); // Color is set to light pink
		    if (puzzleLevel == 0 && gameSetup == true) // If user has either pressed 0 (untimed level), outer text background for 0 turns black
		    {
			c.setColor (Color.black);
		    }
		    c.fillRoundRect (135, 440, 200, 50, 25, 25); // outer text background (0)
		    c.setColor (new Color (245, 149, 149)); // Color is set to light pink
		    if (puzzleLevel == 1 && gameSetup == true) // If user has either pressed 1 (timed level), outer text background for 1 turns black
		    {
			c.setColor (Color.black);
		    }
		    c.fillRoundRect (385, 440, 200, 50, 25, 25); // outer text background (1)
		    c.setColor (Color.white);
		    if (puzzleLevel == 0 && gameSetup == true) // If user has either pressed 0 (untimed level), inner text background for 0 turns light pink
		    {
			c.setColor (new Color (245, 149, 149));
		    }
		    c.fillRoundRect (140, 445, 190, 40, 25, 25); // inner text background (0)
		    c.setColor (Color.white);
		    if (puzzleLevel == 1 && gameSetup == true) // If user has either pressed 0 (untimed level), inner text background for 1 turns light pink
		    {
			c.setColor (new Color (245, 149, 149));
		    }
		    c.fillRoundRect (390, 445, 190, 40, 25, 25); // inner text background (1)
		    // Indication of user choice between timed/ untimed level; color changes depending on choice
		    c.setColor (Color.black);
		    c.setFont (new Font ("SansSerif.BOLD", 1, 25));
		    c.drawString ("0", 150, 465);
		    c.drawString ("1", 400, 465);
		    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
		    c.drawString ("U", 170, 475);
		    c.drawString ("T", 420, 475);
		    c.setFont (new Font ("SansSerif", 10, 25));
		    c.drawString ("ntimed", 190, 475);
		    c.drawString ("imed", 440, 475);
		    // Choices- Timed/ Untimed
		    mainPlay = c.getChar (); // mainPlay is used to determine level choice
		    if (mainPlay == '\n' && gameSetup == true) // If user has entered their option (either 0 or 1)
		    {
			c.setColor (Color.white);
			c.fillRect (322, 500, 75, 50);
			// outer background for countdown
			for (int i = 0 ; i <= 3 ; i++) // countdown
			{
			    c.setColor (Color.red);
			    c.fillRect (327, 505, 65, 40);
			    // red inner background for countdown
			    c.setColor (Color.white);
			    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
			    c.drawString (COUNTDOWN [i], 335, 535); // Countdown sequence- 3, 2, 1, GO!
			    try
			    {
				Thread.sleep (1000);
			    }
			    catch (Exception e)
			    {
			    }
			    // The next value of COUNTDOWN is displayed after -1 second
			}
			if (puzzleLevel == 1) // Timer is started only if the user chooses a timed level
			{
			    Timer t = new Timer (c);
			    t.start ();
			}
			// Thread is used to display the timer
			break; // Program exits the main menu and moves to the crossword game
		    }
		    else // If user has not entered an option yet (0 or 1)
		    {
			try
			{
			    puzzleLevel = Integer.parseInt (String.valueOf (mainPlay)); // mainPlay is casted into an integer
			    if (puzzleLevel != 0 && puzzleLevel != 1) // Exception- if puzzleLevel is not 0 or 1
			    {
				throw new Exception ();
			    }
			    gameSetup = true; // gameSetup is true is true if puzzleLevel is 0 or 1
			}
			catch (NumberFormatException e)
			{
			    if (gameSetup == true) // Enter message if user has chosen an option and then a non-number character (not enter)
			    {
				JOptionPane.showMessageDialog (null, "Press enter to register your input.", "Error", JOptionPane.ERROR_MESSAGE);
			    }
			    else // Error message if user has not chosen an option and chosen a non-number character
			    {
				JOptionPane.showMessageDialog (null, "Enter a number. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
			catch (Exception e)  // Exception- if input is not 0 or 1
			{
			    JOptionPane.showMessageDialog (null, "Your input should either be 0 or 1. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
			    gameSetup = false; // gameSetup is false and user has to press/repress 0 or 1
			}
		    }
		}
		if (gameSetup == true) // Program exits the main menu and the user proceeds to the crossword game
		{
		    break;
		}
	    }
	    else if (mainOption == '\n' && pauseMenu == true) // Exits main menu if user has already chosen an option and presses enter
	    {
		break;
	    }
	    else // From main menu- If user has not registered their option and enters a key
	    {
		try
		{
		    mainNum = Integer.parseInt (String.valueOf (mainOption)); // mainOption is casted into an int
		    if (mainNum < 1 || mainNum > 4) // mainNum has to be one of the available choices (1-4)
		    {
			throw new Exception ();
		    }
		    pauseMenu = true; // If mainNum is between 1-4, user can proceed to register their option
		}
		catch (NumberFormatException e)  // If mainOption is not a number
		{
		    if (pauseMenu == true) // If user has pressed an option but didn't register it
		    {
			JOptionPane.showMessageDialog (null, "Press enter to register your option. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		    else // If user presses an improper key and hasn't pressed an option yet
		    {
			JOptionPane.showMessageDialog (null, "Your choice must be a number. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
		catch (Exception e)  // If maniNum is not between 1 and 4
		{
		    JOptionPane.showMessageDialog (null, "Your choice must be between 1 and 4. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
		    pauseMenu = false; // User has to press/ repress 1-4
		}
	    }
	}
    }


    // puzzleGrid() displays the crossword grid as well as user input during the crossword game; puzzleGrid() is declared as a separate method for efficiency (redraw purposes)
    public void puzzleGrid ()
    {
	synchronized (c)
	{
	    for (int row = 0 ; row < answerGrid.length ; row++) // row for crossword grid
	    {
		for (int column = 0 ; column < answerGrid [0].length ; column++) // column for crossword grid
		{
		    if (!answerGrid [row] [column].equals ("0"))
		    {
			c.setColor (Color.black);
			c.fillRect (35 + column * 20, 95 + row * 20, 25, 25);
			c.setColor (Color.white);
			c.fillRect (35 + column * 20, 95 + row * 20, 20, 20);
			c.setColor (Color.black);
			c.drawRect (35 + column * 20, 95 + row * 20, 20, 20);
		    }
		    // draws a white crossword box for non-empty grid values- if the value isn't 0
		}
	    }
	    for (int row = 0 ; row < guessGrid.length ; row++) // row for guess grid
	    {
		for (int column = 0 ; column < guessGrid [0].length ; column++) // column for guess grid
		{
		    if (!guessGrid [row] [column].equals ("0")) // if the guess value of guess grid contains an actual value (not 0)
		    {
			if (guessGrid [row] [column].equals ("1")) // if the guess value is 1- value has been uncovered using cheats
			{
			    c.setColor (Color.blue); // Color is set to blue
			    c.setFont (new Font ("Monospace", 10, 15));
			    c.drawString (answerGrid [row] [column], 40 + column * 20, 110 + row * 20); // answer value of the same grid value is displayed on top of the crossword grid
			}
			else // If the guess value is not 0 or 1- hasn't been answered with cheats
			{
			    if (guessGrid [row] [column].equals (answerGrid [row] [column])) // If guess value is true compared to the answer value
			    {
				c.setColor (Color.black); // Letter is displayed black
			    }
			    else // If guess value is false compared to the answer value
			    {
				c.setColor (Color.red); // Letter is displayed red
			    }
			    c.setFont (new Font ("Monospace", 10, 15));
			    c.drawString (guessGrid [row] [column], 40 + column * 20, 110 + row * 20); // displays the non-zero values of the guess grid on the crossword grid
			}
		    }
		}
	    }
	}
    }


    /*
	Local Variable List- clueBox()

	Name            |   Data type    |   Description
       -----------------|------------ ---|---------------------------------------------------------------------------------------------------------------
	LEMON_YELLOW    | final Color    | Used for clue box outer background: 1. Environment
	LIME_GREEN      | final Color    | Used for clue box inner background: 1. Environment
	PARAKEET_GREEN  | final Color    | Used for clue box foreground: 1. Environment
	SLATE_GRAY      | final Color    | Used for graphics- 2. Computers: futuristic city
	DARK_MAGENTA    | final Color    | Used for graphics- 2. Computers: futuristic city
	VIOLET_RED      | final Color    | Used for graphics- 2. Computers: futuristic city
	ALICE_BLUE      | final Color    | Used for graphics- 2. Computers: futuristic city
	DIM_GRAY        | final Color    | Used for graphics- 2. Computers: futuristic city
	DARK_SLATE_GRAY | final Color    | Used for graphics- 2. Computers: futuristic city
	OCEAN_BLUE      | final Color    | Used for graphics- 2. Computers: futuristic city
	MEDIUM_ORCHID   | final Color    | Used for graphics- 2. Computers: futuristic city
	defLine         | int            | To ensure the definition fits inside the clue box, defLine splits the definitions into different lines inside the clue box
	defSpace        | int            | Represents the vertical space between the definitions split into different lines
	tectX           | int            | x coordinate used for coordinate positioning for the futuristic city- 2. Computers
	techY           | int            | y coordinate used for coordiante positioning for the futuristic city- 2. Computers
    */
    // clueBox() displays the clues related to the words in the crossword puzzle; clueBox() is declared as a separate method for efficiency (redraw purposes)
    public void clueBox ()
    {
	final Color LEMON_YELLOW = new Color (252, 255, 150);
	final Color LIME_GREEN = new Color (75, 204, 99);
	final Color PARAKEET_GREEN = new Color (103, 219, 124);
	final Color SLATE_GRAY = new Color (112, 128, 144);
	final Color DARK_MAGENTA = new Color (139, 0, 139);
	final Color VIOLET_RED = new Color (219, 112, 147);
	final Color ALICE_BLUE = new Color (219, 238, 255);
	final Color DIM_GRAY = new Color (105, 105, 105);
	final Color DARK_SLATE_GRAY = new Color (47, 79, 79);
	final Color OCEAN_BLUE = new Color (95, 158, 160);
	final Color MEDIUM_ORCHID = new Color (186, 85, 211);
	int defLine; // makes sure the clue's length does not exceed clue box
	int defSpace; // represents space between definition split into different lines
	int techX = 180;
	int techY = 475;
	synchronized (c)
	{
	    if (levelNum == 0) // if the level chosen is 1. Environment
	    {
		c.setColor (Color.black);
		c.setFont (new Font ("Helvetica.BOLD", 1, 10));
		c.drawString ("1", 42, 190);
		c.drawString ("2", 26, 290);
		c.drawString ("3", 100, 153);
		c.drawString ("4", 82, 210);
		c.drawString ("5", 280, 90);
		c.drawString ("6", 220, 150);
		c.drawString ("7", 340, 110);
		c.drawString ("8", 305, 172);
		c.drawString ("9", 245, 170);
		c.drawString ("10", 221, 230);
		c.drawString ("11", 160, 250);
		c.drawString ("12", 260, 270);
		c.drawString ("13", 380, 230);
		c.drawString ("14", 280, 330);
		c.drawString ("15", 320, 310);
		c.drawString ("16", 220, 370);
		// draws the according numbers relating to the location of the word for the crossword grid
		c.setColor (Color.white);
		c.fillRoundRect (25, 470, 670, 100, 25, 25);
		// Outer background for cluebox
		c.setColor (LEMON_YELLOW);
		c.fillRoundRect (30, 475, 660, 90, 25, 25);
		// Inner background for clue box
		c.setColor (LIME_GREEN);
		c.fillRect (57, 475, 5, 10);
		c.fillRect (50, 485, 20, 5);
		c.fillRect (45, 490, 30, 5);
		c.fillRect (40, 495, 40, 70);
		// Building 1- from the left
		c.fillRect (90, 505, 40, 60);
		// Building 2- from the left
		c.fillRect (250, 495, 40, 70);
		// Building 4- from the left
		c.fillRect (300, 505, 30, 60);
		// Building 5- from the left
		c.fillRect (340, 525, 40, 40);
		// Building 6- from the left
		for (int i = 0 ; i <= 10 ; i++)
		{
		    c.drawLine (340, 525 - i, 380, 525);
		}
		// Top of Building 6- from the left
		c.fillRect (410, 535, 40, 30);
		// Building 7- from the left
		for (int i = 0 ; i <= 20 ; i++)
		{
		    c.drawLine (400, 535, 430, 535 - i);
		    c.drawLine (430, 535 - i, 460, 535);
		}
		// Roof of Building 6- from the left
		c.fillRect (470, 515, 40, 50);
		c.fillRect (475, 510, 30, 5);
		c.fillRect (487, 490, 5, 20);
		// Building 8- from the left
		c.fillRect (520, 515, 30, 50);
		// Building 9- from the left
		c.fillRect (560, 525, 30, 40);
		// Building 10- from the left
		c.fillRect (605, 545, 30, 20);
		// Building 11- from the left
		for (int i = 0 ; i <= 20 ; i++)
		{
		    c.drawLine (600 + i, 545 - i, 640 - i, 545 - i);
		}
		c.fillRect (650, 515, 30, 50);
		// Building 12- from the left
		c.setColor (Color.white);
		for (int i = 0 ; i <= 40 ; i += 10)
		{
		    c.fillRect (45, 500 + i, 30, 5);
		}
		// Window of Building 1- from the left
		for (int y = 0 ; y <= 32 ; y += 16)
		{
		    for (int x = 0 ; x <= 20 ; x += 10)
		    {
			c.fillRect (96 + x, 510 + y, 8, 10);
		    }
		}
		// Window of Building 2- from the left
		for (int i = 0 ; i <= 20 ; i += 5)
		{
		    c.fillRect (255, 500 + i, 30, 3);
		    c.fillRect (255, 530 + i, 30, 3);
		}
		// Window of Building 4- from the left
		for (int y = 0 ; y <= 9 ; y += 9)
		{
		    for (int x = 0 ; x <= 14 ; x += 7)
		    {
			c.fillRect (305 + x, 510 + y, 5, 7);
			c.fillRect (305 + x, 535 + y, 5, 7);
		    }
		}
		// Window of Building 5- from the left
		for (int y = 0 ; y <= 7 ; y += 7)
		{
		    for (int x = 0 ; x <= 25 ; x += 5)
		    {
			c.fillRect (345 + x, 525 + y, 3, 5);
			c.fillRect (345 + x, 545 + y, 3, 5);
		    }
		}
		// Window of Building 6- from the left
		for (int i = 0 ; i <= 9 ; i += 9)
		{
		    c.fillRect (422 + i, 537, 7, 7);
		    c.fillRect (422 + i, 546, 7, 7);
		}
		// Window of Building 7- from the left
		for (int i = 0 ; i <= 28 ; i += 6)
		{
		    c.fillRect (475 + i, 520, 5, 35);
		}
		// Window of Building 8- from the left
		for (int y = 0 ; y <= 32 ; y += 8)
		{
		    for (int x = 0 ; x <= 20 ; x += 5)
		    {
			c.fillRect (523 + x, 520 + y, 4, 6);
		    }
		}
		// Window of Building 9- from the left
		for (int i = 0 ; i <= 25 ; i += 5)
		{
		    c.fillRect (565, 530 + i, 20, 3);
		}
		// Window of Building 10- from the left
		for (int i = 0 ; i <= 7 ; i += 7)
		{
		    c.fillRect (614 + i, 545, 5, 5);
		    c.fillRect (614 + i, 552, 5, 5);
		}
		// Window of Building 11- from the left
		for (int i = 0 ; i <= 20 ; i += 5)
		{
		    c.fillRect (653 + i, 520, 3, 40);
		}
		// Window of Building 12- from the left
		c.setColor (LIME_GREEN);
		c.fillRect (150, 525, 30, 40);
		c.fillRect (180, 545, 60, 20);
		for (int i = 0 ; i <= 10 ; i++)
		{
		    c.drawLine (150, 525 - i, 180, 525);
		    c.drawLine (180, 545 - i, 210, 545);
		}
		// Factory- Building 3 from the left
		int xLeft[] = {153, 158, 168, 173};
		int yLeft[] = {530, 505, 505, 530};
		c.fillPolygon (xLeft, yLeft, 4);
		c.fillRect (158, 499, 10, 3);
		// top of left factory
		int xMiddle[] = {185, 190, 200, 205};
		int yMiddle[] = {545, 520, 520, 545};
		c.fillPolygon (xMiddle, yMiddle, 4);
		c.fillRect (190, 514, 10, 3);
		// top of middle factory
		int xRight[] = {215, 220, 230, 235};
		int yRight[] = {555, 530, 530, 555};
		c.fillPolygon (xRight, yRight, 4);
		c.fillRect (220, 524, 10, 3);
		// top of right factory
		c.setColor (Color.white);
		c.fillRect (158, 502, 10, 3);
		c.fillRect (190, 517, 10, 3);
		c.fillRect (220, 527, 10, 3);
		// white part on top of factories
		c.fillRoundRect (158, 482, 30, 10, 25, 25);
		c.fillRoundRect (185, 497, 30, 10, 10, 10);
		c.fillRoundRect (200, 502, 25, 12, 10, 10);
		// clouds
		c.setColor (PARAKEET_GREEN);
		for (int i = 0 ; i <= 15 ; i++)
		{
		    c.drawLine (40, 565 - i, 680, 565);
		}
		// land- clue box foreground
	    }
	    if (levelNum == 1) // if the level chosen is 2. Computers
	    {
		c.setColor (Color.black);
		c.setFont (new Font ("Helvetica.BOLD", 1, 10));
		c.drawString ("1", 160, 90);
		c.drawString ("2", 105, 190);
		c.drawString ("3", 140, 250);
		c.drawString ("4", 85, 225);
		c.drawString ("5", 60, 310);
		c.drawString ("6", 25, 285);
		c.drawString ("7", 180, 270);
		c.drawString ("8", 45, 430);
		c.drawString ("9", 225, 190);
		c.drawString ("10", 240, 190);
		c.drawString ("11", 260, 230);
		c.drawString ("12", 240, 270);
		c.drawString ("13", 297, 150);
		c.drawString ("14", 280, 310);
		c.drawString ("15", 300, 330);
		c.drawString ("16", 140, 347);
		// draws the according numbers relating to the location of the word for the crossword grid
		techX = 180; // used as the x coordinate of the futuristic city for coordiante positioning
		techY = 475; // used as the y coordiante of the futuristic city for coordinate positioning
		c.setColor (Color.black);
		c.fillRoundRect (25, 470, 670, 100, 25, 25);
		// outer background of clue box
		c.setColor (MEDIUM_ORCHID);
		c.fillRoundRect (30, 475, 660, 90, 25, 25);
		// inner background of clue box
		c.setColor (SLATE_GRAY);
		c.fillRect (techX, techY + 70, 360, 5);
		c.fillRect (techX + 40, techY + 75, 45, 10);
		c.fillRect (techX + 125, techY + 80, 65, 5);
		c.fillRect (techX + 285, techY + 80, 75, 5);
		c.setColor (DARK_MAGENTA);
		c.fillRect (techX + 50, techY + 75, 310, 5);
		c.fillRect (techX + 20, techY + 72, 10, 10);
		c.fillRect (techX + 60, techY + 72, 30, 2);
		// support of the city (floating land)
		c.setColor (ALICE_BLUE);
		c.fillRect (techX + 20, techY + 60, 10, 10);
		// Building 1- from the left
		c.fillRect (techX + 35, techY + 30, 25, 40);
		c.setColor (DIM_GRAY);
		for (int i = 0 ; i <= 18 ; i += 6)
		{
		    c.fillRect (techX + 37 + i, techY + 33, 3, 34);
		}
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 37, techY + 43, 3, 5);
		c.fillRect (techX + 37, techY + 36, 3, 5);
		c.fillRect (techX + 43, techY + 33, 3, 5);
		c.fillRect (techX + 49, techY + 38, 3, 5);
		c.fillRect (techX + 49, techY + 55, 3, 5);
		c.fillRect (techX + 55, techY + 42, 3, 5);
		c.fillRect (techX + 55, techY + 50, 3, 5);
		c.setColor (DIM_GRAY);
		c.fillRect (techX + 50, techY + 25, 11, 5);
		for (int i = 0 ; i <= 5 ; i++)
		{
		    c.drawLine (techX + 50, techY + 25, techX + 60, techY + 25 - i);
		}
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 53, techY + 15, 5, 10);
		// Building 3- from the left
		c.setColor (DARK_MAGENTA);
		c.fillRect (techX + 30, techY + 50, 10, 20);
		c.setColor (VIOLET_RED);
		c.fillRect (techX + 35, techY + 55, 6, 10);
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 37, techY + 55, 2, 10);
		// Building 2- from the left
		c.setColor (DARK_SLATE_GRAY);
		c.fillRect (techX + 60, techY + 10, 10, 60);
		c.setColor (DIM_GRAY);
		c.fillRect (techX + 70, techY + 10, 10, 60);
		c.setColor (VIOLET_RED);
		c.fillRect (techX + 71, techY + 5, 8, 4);
		c.fillRect (techX + 88, techY + 10, 5, 8);
		c.fillRect (techX + 88, techY + 20, 5, 35);
		c.setColor (OCEAN_BLUE);
		for (int i = 0 ; i <= 35 ; i += 7)
		{
		    c.fillRect (techX + 73, techY + 13 + i, 4, 4);
		}
		// Building 4- from the left
		c.setColor (DARK_SLATE_GRAY);
		c.fillRect (techX + 80, techY + 55, 10, 15);
		for (int i = 0 ; i <= 5 ; i++)
		{
		    c.drawLine (techX + 80, techY + 55 - i, techX + 89, techY + 55);
		}
		c.fillRect (techX + 90, techY + 45, 10, 25);
		c.fillRect (techX + 91, techY + 30, 8, 10);
		c.fillRect (techX + 96, techY + 20, 3, 6);
		// Building 5- from the left
		c.setColor (ALICE_BLUE);
		c.fillRect (techX + 105, techY + 35, 5, 20);
		c.setColor (SLATE_GRAY);
		c.fillRect (techX + 100, techY + 50, 20, 20);
		for (int i = 0 ; i <= 10 ; i++)
		{
		    c.drawLine (techX + 100, techY + 50 - i, techX + 119, techY + 50);
		}
		// Building 6- from the left
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 75, techY + 60, 30, 3);
		c.fillRect (techX + 95, techY + 65, 15, 2);
		c.setColor (ALICE_BLUE);
		c.fillRect (techX + 125, techY + 10, 15, 60);
		c.fillRect (techX + 140, techY + 20, 10, 50);
		for (int i = 0 ; i <= 10 ; i++)
		{
		    c.drawLine (techX + 130, techY + 10 - i, techX + 130, techY + 10);
		}
		c.setColor (MEDIUM_ORCHID);
		c.fillRect (techX + 125, techY + 15, 7, 3);
		c.fillRect (techX + 140, techY + 25, 15, 3);
		c.fillRect (techX + 140, techY + 30, 15, 3);
		c.fillRect (techX + 140, techY + 35, 15, 3);
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 125, techY + 20, 3, 3);
		c.fillRect (techX + 120, techY + 25, 8, 3);
		c.fillRect (techX + 120, techY + 30, 8, 3);
		// Building 8- from the left
		c.setColor (VIOLET_RED);
		c.fillRect (techX + 130, techY + 30, 5, 15);
		c.setColor (DARK_MAGENTA);
		c.fillRect (techX + 105, techY + 55, 15, 15);
		c.fillRect (techX + 120, techY + 50, 20, 20);
		for (int i = 0 ; i <= 10 ; i++)
		{
		    c.drawLine (techX + 120, techY + 50, techX + 139, techY + 50 - i);
		}
		c.setColor (VIOLET_RED);
		c.fillRect (techX + 115, techY + 57, 5, 10);
		c.fillRect (techX + 125, techY + 55, 20, 3);
		c.fillRect (techX + 125, techY + 65, 15, 3);
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 125, techY + 60, 20, 3);
		// Building 7- from the left
		c.setColor (SLATE_GRAY);
		c.fillRect (techX + 151, techY + 50, 8, 10);
		c.fillRect (techX + 151, techY + 60, 3, 10);
		// Building 9- from the left
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 151, techY + 57, 5, 5);
		c.setColor (DARK_SLATE_GRAY);
		c.fillRect (techX + 165, techY + 20, 5, 50);
		c.fillRect (techX + 166, techY + 13, 3, 5);
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 166, techY + 20, 3, 20);
		c.setColor (SLATE_GRAY);
		c.fillRect (techX + 166, techY + 50, 3, 15);
		c.fillRect (techX + 170, techY + 20, 15, 50);
		c.fillRect (techX + 185, techY + 45, 5, 25);
		for (int i = 0 ; i <= 5 ; i++)
		{
		    c.drawLine (techX + 185, techY + 45 - i, techX + 190, techY + 45);
		}
		c.fillRect (techX + 190, techY + 60, 10, 10);
		for (int i = 0 ; i <= 10 ; i++)
		{
		    c.drawLine (techX + 190, techY + 60 - i, techX + 200, techY + 60);
		}
		c.setColor (VIOLET_RED);
		c.fillRect (techX + 145, techY + 45, 55, 2);
		c.fillRect (techX + 175, techY + 13, 3, 15);
		c.fillRect (techX + 175, techY + 3, 3, 7);
		// Building 10- from the left
		c.setColor (MEDIUM_ORCHID);
		c.fillRect (techX + 180, techY + 30, 5, 7);
		c.fillRect (techX + 185, techY + 50, 15, 5);
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 173, techY + 50, 3, 15);
		c.fillRect (techX + 185, techY + 58, 2, 8);
		c.setColor (DARK_MAGENTA);
		c.fillRect (techX + 179, techY + 55, 3, 10);
		c.fillRect (techX + 170, techY + 68, 10, 5);
		// Building 11- from the left
		c.setColor (ALICE_BLUE);
		c.fillRect (techX + 210, techY + 30, 25, 40);
		for (int i = 0 ; i <= 5 ; i++)
		{
		    c.drawLine (techX + 210, techY + 30 - i, techX + 220, techY + 30);
		    c.drawLine (techX + 220, techY + 30, techX + 234, techY + 30 - i);
		}
		// Building 12- from the left
		c.setColor (DIM_GRAY);
		c.fillRect (techX + 200, techY + 25, 5, 45);
		c.fillRect (techX + 205, techY + 45, 10, 25);
		c.setColor (MEDIUM_ORCHID);
		c.fillRect (techX + 203, techY + 32, 12, 8);
		c.setColor (VIOLET_RED);
		c.fillRect (techX + 199, techY + 18, 7, 5);
		c.setColor (DARK_MAGENTA);
		c.fillRect (techX + 210, techY + 50, 9, 12);
		c.fillRect (techX + 203, techY + 67, 8, 5);
		// Building 13- from the left
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 215, techY + 52, 6, 9);
		c.setColor (DARK_SLATE_GRAY);
		c.fillRect (techX + 235, techY + 10, 5, 60);
		c.fillRect (techX + 240, techY + 15, 5, 55);
		c.setColor (ALICE_BLUE);
		c.fillRect (techX + 245, techY + 30, 5, 40);
		for (int i = 0 ; i <= 5 ; i++)
		{
		    c.drawLine (techX + 245, techY + 30 - i, techX + 249, techY + 30);
		}
		// Building 14- from the left
		c.setColor (SLATE_GRAY);
		c.fillRect (techX + 240, techY + 50, 10, 20);
		c.setColor (VIOLET_RED);
		for (int i = 0 ; i <= 14 ; i += 7)
		{
		    c.fillRect (techX + 243, techY + 17 + i, 3, 5);
		}
		c.fillRect (techX + 260, techY + 8, 4, 8);
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 220, techY + 40, 23, 5);
		c.fillRect (techX + 235, techY + 50, 3, 7);
		c.setColor (DARK_MAGENTA);
		c.fillRect (techX + 225, techY + 68, 20, 5);
		c.setColor (ALICE_BLUE);
		c.fillRect (techX + 255, techY + 35, 5, 35);
		c.setColor (DIM_GRAY);
		c.fillRect (techX + 270, techY + 10, 10, 60);
		c.fillRect (techX + 290, techY + 20, 5, 20);
		c.setColor (DARK_SLATE_GRAY);
		c.fillRect (techX + 280, techY + 5, 10, 65);
		for (int i = 0 ; i <= 5 ; i++)
		{
		    c.setColor (DIM_GRAY);
		    c.drawLine (techX + 270, techY + 10, techX + 279, techY + 10 - i);
		    c.setColor (DARK_SLATE_GRAY);
		    c.drawLine (techX + 280, techY + 5, techX + 289, techY + 5 - i);
		}
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 284, techY + 10, 3, 30);
		c.fillRect (techX + 284, techY + 45, 3, 6);
		c.fillRect (techX + 284, techY + 55, 3, 6);
		c.setColor (SLATE_GRAY);
		c.fillRect (techX + 260, techY + 20, 15, 50);
		for (int i = 0 ; i <= 5 ; i++)
		{
		    c.drawLine (techX + 265, techY + 20 - i, techX + 274, techY + 20 - i);
		    c.drawLine (techX + 265, techY + 15, techX + 274, techY + 15 - i);
		}
		// Building 15- from the left
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 261, techY + 30, 3, 6);
		c.fillRect (techX + 261, techY + 38, 3, 6);
		c.fillRect (techX + 268, techY + 30, 4, 35);
		// Building 16- from the left
		c.setColor (DARK_SLATE_GRAY);
		c.fillRect (techX + 252, techY + 44, 12, 6);
		c.fillRect (techX + 255, techY + 56, 10, 16);
		// Building 18- from the left
		c.setColor (ALICE_BLUE);
		c.fillRect (techX + 290, techY + 55, 20, 15);
		// Building 17- from the left
		c.setColor (DIM_GRAY);
		c.fillRect (techX + 310, techY + 40, 10, 30);
		c.setColor (OCEAN_BLUE);
		c.fillRect (techX + 305, techY + 48, 8, 20);
		c.setColor (SLATE_GRAY);
		c.fillRect (techX + 320, techY + 20, 30, 5);
		c.setColor (DARK_MAGENTA);
		c.fillRect (techX + 330, techY + 25, 20, 3);
		c.setColor (DIM_GRAY);
		c.fillRect (techX + 323, techY + 5, 8, 15);
		c.setColor (SLATE_GRAY);
		c.fillRect (techX + 331, techY + 10, 5, 10);
		c.setColor (DARK_SLATE_GRAY);
		c.fillRect (techX + 336, techY + 8, 10, 12);
		// floating island next to floating city
	    }
	    else if (levelNum == 2) // if the level chosen is 3. Canadian
	    {
		c.setColor (Color.black);
		c.setFont (new Font ("Helvetica.BOLD", 1, 10));
		c.drawString ("1", 82, 230);
		c.drawString ("2", 40, 290);
		c.drawString ("3", 360, 90);
		c.drawString ("4", 240, 130);
		c.drawString ("5", 122, 170);
		c.drawString ("6", 85, 210);
		c.drawString ("7", 180, 110);
		c.drawString ("8", 205, 228);
		c.drawString ("9", 220, 170);
		c.drawString ("10", 240, 330);
		c.drawString ("11", 320, 170);
		c.drawString ("12", 40, 370);
		c.drawString ("13", 380, 250);
		c.drawString ("14", 140, 370);
		c.drawString ("15", 120, 350);
		c.drawString ("16", 20, 430);
		// draws the according numbers relating to the location of the word for the crossword grid
		c.setColor (Color.white);
		c.fillRoundRect (25, 470, 670, 100, 25, 25);
		// outer background for clue box
		c.setColor (new Color (0, 166, 255)); // Color is set as light blue
		c.fillRoundRect (30, 475, 660, 90, 25, 25);
		// inner background for clue box

	    }
	}
	synchronized (c)
	{
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif", 10, 12));
	    c.drawString ("*Odd: Down, Even: Across", 40, 455); // explanation of numbers on crossword grid
	    if (levelNum == 0)
	    {
		c.setColor (Color.black);
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (new Color (0, 255, 61)); // Color is set to neon green
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (Color.white);
	    }
	    // colours of information inside clue box depend on levelNum
	    c.drawString ("*Use '>' and '<' to alternate between clues", 40, 562);
	    c.setFont (new Font ("Serif.BOLD", 10, 15));
	    c.drawString ("CLUES", 40, 495);
	    // title of clue box- CLUES
	    if (linesFileDef [defNum].length () >= 96) // if the definition (clue)'s length exceeds 95
	    {
		defLine = 0; // defLine is rest as 0
		defSpace = 0; // defSpace is reset as 0
		while (true) // as some defintions exceed the length of the clue box, while loop ensures does definitions are properly split and printed on different lines to fit inside the clue box
		{
		    if (levelNum == 0)
		    {
			c.setColor (Color.white);
		    }
		    else if (levelNum == 1)
		    {
			c.setColor (Color.black);
		    }
		    else if (levelNum == 2)
		    {
			c.setColor (Color.red);
		    }
		    // colour of the definition (clue) depends on levelNum (level chosen)
		    c.drawString (linesFileDef [defNum].substring (defLine, defLine + 96), 42, 517 + defSpace); // displays 95 characters on each line
		    if (levelNum == 0)
		    {
			c.setColor (Color.black);
		    }
		    else if (levelNum == 1)
		    {
			c.setColor (new Color (0, 255, 61));
		    }
		    else if (levelNum == 2)
		    {
			c.setColor (Color.white);
		    }
		    // colour of the definition (clue) depends on levelNum (level chosen)
		    c.drawString (linesFileDef [defNum].substring (defLine, defLine + 96), 40, 515 + defSpace); // displays 95 characters on each line
		    defLine += 96; // ensures the next 95 characters printed have not already been displayed
		    defSpace += 20; // vertical space between definitions is increased incremently (definitions split into different lines)
		    if (defLine + 96 >= linesFileDef [defNum].length ()) // if the next batch of 95 characters of defLine will exceed its length
		    {
			if (levelNum == 0)
			{
			    c.setColor (Color.white);
			}
			else if (levelNum == 1)
			{
			    c.setColor (Color.black);
			}
			else if (levelNum == 2)
			{
			    c.setColor (Color.red);
			}
			// colour of the definition (clue) depends on levelNum (level chosen)
			c.drawString (linesFileDef [defNum].substring (defLine), 42, 517 + defSpace); // rest of the definition is printed
			if (levelNum == 0)
			{
			    c.setColor (Color.black);
			}
			else if (levelNum == 1)
			{
			    c.setColor (new Color (0, 255, 61));
			}
			else if (levelNum == 2)
			{
			    c.setColor (Color.white);
			}
			// colour of the definition (clue) depends on levelNum (level chosen)
			c.drawString (linesFileDef [defNum].substring (defLine), 40, 515 + defSpace); // rest of the definition is printed
			break; // as definition has finished pritned, exists while loop
		    }
		}
	    }
	    else // if definition's length does not exceed 95 characters, definition is simply printed without being further modified
	    {
		if (levelNum == 0)
		{
		    c.setColor (Color.white);
		}
		else if (levelNum == 1)
		{
		    c.setColor (Color.black);
		}
		else if (levelNum == 2)
		{
		    c.setColor (Color.red);
		}
		// colour of the definition (clue) depends on levelNum (level chosen)
		c.drawString (linesFileDef [defNum], 42, 517); // corresponding definiton (defNum) is printed inside the clue box
		if (levelNum == 0)
		{
		    c.setColor (Color.black);
		}
		else if (levelNum == 1)
		{
		    c.setColor (new Color (0, 255, 61));
		}
		else if (levelNum == 2)
		{
		    c.setColor (Color.white);
		}
		// colour of the definition (clue) depends on levelNum (level chosen)
		c.drawString (linesFileDef [defNum], 40, 515); // corresponding definiton (defNum) is printed inside the clue box
	    }
	    c.drawLine (35, 485, 35, 555); // vertical line is drawn on the left side of the clue box for graphics
	}
    }


    /*
	Local Variable List- guessBox()

	Name            |   Data type    |   Description
       -----------------|------------ ---|-------------------------------------------------------------------------------------------------------------
	pandaX          | int            | Used as the main x coordinate to draw the panda if levelNum = 0 (1. Environment)
	pandaY          | int            | Used as the main y coordiante to draw the panda if levelNum = 0 (1. Environemnt)
	diskX           | int            | Used as the main x coordinate to draw the disk if levelNum = 1 (2. Computers)
	diskY           | int            | Used as the main y coordinate to draw the dis if levelNum = 1 (2. Computers)
    */
    // guessBox() displays the guess box for the user to guess/ reveal cheats within the crossword puzzle; guessBox() is declared as a separate method for efficiency (redraw purposes)
    public void guessBox ()
    {
	int pandaX;
	int pandaY;
	int diskX;
	int diskY;
	synchronized (c)
	{
	    c.setColor (Color.black);
	    c.fillRect (450, 90, 240, 370);
	    // outer background- guess box
	    if (levelNum == 0)
	    {
		c.setColor (new Color (163, 117, 70)); // Color is set to brown
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (new Color (169, 169, 169)); // Color is set to gray
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (Color.white);
	    }
	    c.fillRect (455, 95, 230, 360);
	    // inner background- guess box
	    c.setColor (Color.black);
	    for (int i = 0 ; i <= 10 ; i++)
	    {
		c.drawLine (455, 95 + i, 465, 95);
		c.drawLine (685, 95 + i, 675, 95);
		c.drawLine (455, 455 - i, 465, 455);
		c.drawLine (685, 455 - i, 675, 455);
	    }
	    // foreground- black triangular corners of the guess box
	    c.setColor (Color.black);
	    c.setFont (new Font ("Serif.BOLD", 10, 25));
	    c.drawString ("GUESS", 530, 120);
	    // title- guess box
	    if (levelNum == 0) // If level chosen is 1. Environment
	    {
		c.setColor (Color.black);
		pandaX = 527; // x point of panda used for coordinate positioning
		pandaY = 130; // y point of panda used for coordinate positioning
		int blackX[] = {pandaX, pandaX, pandaX - 5, pandaX - 5, pandaX - 10, pandaX - 10, pandaX - 10, pandaX - 10, pandaX - 10, pandaX - 10, pandaX - 10, pandaX - 5, pandaX - 5, pandaX - 5, pandaX - 5, pandaX, pandaX, pandaX + 5, pandaX + 5, pandaX + 10, pandaX + 15, pandaX + 15, pandaX + 15, pandaX + 20, pandaX + 20, pandaX + 20, pandaX + 20, pandaX + 25, pandaX + 25, pandaX + 30, pandaX + 30, pandaX + 35, pandaX + 40, pandaX + 45, pandaX + 45, pandaX + 45, pandaX + 40, pandaX + 40, pandaX + 40, pandaX + 45, pandaX + 50, pandaX + 55, pandaX + 55, pandaX + 50, pandaX + 50, pandaX + 50, pandaX + 55, pandaX + 60, pandaX + 65, pandaX + 65, pandaX + 70, pandaX + 70, pandaX + 70, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 70, pandaX + 70, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 70, pandaX + 70, pandaX + 65, pandaX + 60, pandaX + 60, pandaX + 55, pandaX + 50, pandaX + 45, pandaX + 40, pandaX + 40, pandaX + 35, pandaX + 35, pandaX + 30, pandaX + 25, pandaX + 25, pandaX + 20, pandaX + 20, pandaX + 15, pandaX + 10, pandaX + 5};
		int blackY[] = {pandaY, pandaY + 5, pandaY + 5, pandaY + 10, pandaY + 10, pandaY + 15, pandaY + 20, pandaY + 25, pandaY + 30, pandaY + 35, pandaY + 40, pandaY + 40, pandaY + 45, pandaY + 50, pandaY + 55, pandaY + 55, pandaY + 60, pandaY + 60, pandaY + 65, pandaY + 65, pandaY + 65, pandaY + 60, pandaY + 55, pandaY + 55, pandaY + 60, pandaY + 65, pandaY + 70, pandaY + 70, pandaY + 75, pandaY + 75, pandaY + 80, pandaY + 80, pandaY + 80, pandaY + 80, pandaY + 75, pandaY + 70, pandaY + 70, pandaY + 65, pandaY + 60, pandaY + 60, pandaY + 60, pandaY + 60, pandaY + 65, pandaY + 65, pandaY + 70, pandaY + 75, pandaY + 75, pandaY + 75, pandaY + 75, pandaY + 70, pandaY + 70, pandaY + 65, pandaY + 60, pandaY + 60, pandaY + 55, pandaY + 50, pandaY + 45, pandaY + 40, pandaY + 35, pandaY + 30, pandaY + 25, pandaY + 20, pandaY + 15, pandaY + 15, pandaY + 10, pandaY + 10, pandaY + 5, pandaY, pandaY, pandaY - 5, pandaY - 5, pandaY - 5, pandaY, pandaY, pandaY, pandaY, pandaY, pandaY - 5, pandaY - 5, pandaY - 10, pandaY - 10, pandaY - 10, pandaY - 5, pandaY - 5, pandaY, pandaY, pandaY, pandaY};
		c.fillPolygon (blackX, blackY, 88);
		// black outline of panda
		c.setColor (Color.white);
		int whiteXL[] = {pandaX + 5, pandaX + 5, pandaX, pandaX, pandaX - 5, pandaX - 5, pandaX - 5, pandaX - 5, pandaX - 5, pandaX, pandaX, pandaX, pandaX + 5, pandaX + 5, pandaX + 10, pandaX + 10, pandaX + 10, pandaX + 10, pandaX + 10, pandaX + 10, pandaX + 10, pandaX + 15, pandaX + 15, pandaX + 20, pandaX + 20, pandaX + 20, pandaX + 15, pandaX + 10};
		int whiteYL[] = {pandaY, pandaY + 5, pandaY + 5, pandaY + 10, pandaY + 10, pandaY + 15, pandaY + 20, pandaY + 25, pandaY + 30, pandaY + 30, pandaY + 35, pandaY + 40, pandaY + 40, pandaY + 45, pandaY + 45, pandaY + 40, pandaY + 35, pandaY + 30, pandaY + 25, pandaY + 20, pandaY + 15, pandaY + 15, pandaY + 10, pandaY + 10, pandaY + 5, pandaY, pandaY, pandaY};
		c.fillPolygon (whiteXL, whiteYL, 28);
		// white body of panda
		int whiteXR[] = {pandaX + 40, pandaX + 40, pandaX + 35, pandaX + 30, pandaX + 25, pandaX + 25, pandaX + 20, pandaX + 20, pandaX + 20, pandaX + 15, pandaX + 15, pandaX + 15, pandaX + 15, pandaX + 20, pandaX + 20, pandaX + 25, pandaX + 25, pandaX + 30, pandaX + 30, pandaX + 35, pandaX + 35, pandaX + 40, pandaX + 45, pandaX + 50, pandaX + 50, pandaX + 55, pandaX + 60, pandaX + 60, pandaX + 65, pandaX + 65, pandaX + 70, pandaX + 70, pandaX + 70, pandaX + 70, pandaX + 75, pandaX + 75, pandaX + 75, pandaX + 70, pandaX + 70, pandaX + 65, pandaX + 65, pandaX + 60, pandaX + 55, pandaX + 55, pandaX + 50, pandaX + 45};
		int whiteYR[] = {pandaY, pandaY + 5, pandaY + 5, pandaY + 5, pandaY + 5, pandaY + 10, pandaY + 10, pandaY + 15, pandaY + 20, pandaY + 20, pandaY + 25, pandaY + 30, pandaY + 35, pandaY + 35, pandaY + 40, pandaY + 40, pandaY + 45, pandaY + 45, pandaY + 50, pandaY + 50, pandaY + 55, pandaY + 55, pandaY + 55, pandaY + 55, pandaY + 50, pandaY + 50, pandaY + 50, pandaY + 45, pandaY + 45, pandaY + 40, pandaY + 40, pandaY + 35, pandaY + 30, pandaY + 25, pandaY + 25, pandaY + 20, pandaY + 15, pandaY + 15, pandaY + 10, pandaY + 10, pandaY + 5, pandaY + 5, pandaY + 5, pandaY, pandaY, pandaY};
		c.fillPolygon (whiteXR, whiteYR, 46);
		// white face of panda
		c.setColor (Color.black);
		c.fillRect (pandaX + 30, pandaY + 20, 10, 10);
		c.fillRect (pandaX + 25, pandaY + 25, 10, 10);
		c.fillRect (pandaX + 35, pandaY + 40, 15, 5);
		c.fillRect (pandaX + 40, pandaY + 45, 5, 5);
		c.fillRect (pandaX + 50, pandaY + 25, 10, 10);
		c.fillRect (pandaX + 55, pandaY + 35, 5, 5);
		// black facial features of panda
	    }
	    else if (levelNum == 1) // if level chosen is 2. Computers
	    {
		diskX = 570; // x point of disk used for coordinate positioning
		diskY = 170; // y point of disk used for coordinate positioning
		c.setColor (new Color (191, 198, 201)); // Color is set to light gray
		c.fillRect (525, 125, 90, 90); // draws background for the disk
		c.setColor (new Color (169, 169, 169)); // Color is set to dark grey
		for (int i = 0 ; i <= 15 ; i += 5) // Draws the basic outline of disk- sharp edges
		{
		    c.fillRect (diskX - 45, diskY - 45 + i, 20 - i, 5);
		    c.fillRect (diskX - 45, diskY + 40 - i, 20 - i, 5);
		    c.fillRect (diskX + 25 + i, diskY - 45 + i, 20 - i, 5);
		    c.fillRect (diskX + 25 + i, diskY + 40 - i, 20 - i, 5);
		}
		for (int i = 0 ; i <= 5 ; i += 5)
		{
		    c.setColor (new Color (119, 136, 153)); // Color is set to elephant-gray
		    c.fillRect (diskX - 40 + i, diskY - 20 + i, 5, 35 - i * 2);
		    c.fillRect (diskX - 30 + i, diskY - 15 + i, 5, 25 - i * 2);
		    c.fillRect (diskX - 20 + i, diskY + 35 - i, 40 - i * 2, 5);
		    c.fillRect (diskX - 15 + i, diskY + 25 - i, 30 - i * 2, 5);
		    c.fillRect (diskX - 20 + i, diskY - 40 + i, 40 - i * 2, 5);
		    c.fillRect (diskX - 15 + i, diskY - 30 + i, 30 - i * 2, 5);
		    c.fillRect (diskX + 35 - i, diskY - 15 + i, 5, 35 - i * 2);
		    c.fillRect (diskX + 25 - i, diskY - 10 + i, 5, 25 - i * 2);
		    // four portions connecting to the middle of the disk (top,down,left,right)
		    c.setColor (new Color (66, 78, 84)); // Color is set to dark blue
		    c.fillRect (diskX - 15 + i, diskY - 15 - i, 30 - i * 2, 5);
		    c.fillRect (diskX - 15 + i, diskY + 10 + i, 30 - i * 2, 5);
		    // displays background of the center of the disk
		}
		c.fillRect (diskX - 20, diskY - 10, 40, 20);
		// displays background of the center of the disk
		c.setColor (new Color (220, 220, 220)); // Color is set to white-gray
		c.fillRect (diskX - 15, diskY - 10, 30, 20);
		c.fillRect (diskX - 10, diskY - 15, 20, 5);
		c.fillRect (diskX - 10, diskY + 10, 20, 5);
		// displays white center of the disk
		int diskgRX[] = {diskX + 15, diskX + 15, diskX + 20, diskX + 20, diskX + 30, diskX + 30, diskX + 35, diskX + 35, diskX + 25, diskX + 25};
		int diskgRY[] = {diskY + 15, diskY + 25, diskY + 25, diskY + 35, diskY + 35, diskY + 30, diskY + 30, diskY + 20, diskY + 20, diskY + 15};
		c.fillPolygon (diskgRX, diskgRY, 10);
		// white upper left corner of the disk
		int diskgLX[] = {diskX - 15, diskX - 25, diskX - 25, diskX - 35, diskX - 35, diskX - 30, diskX - 30, diskX - 20, diskX - 20, diskX - 15};
		int diskgLY[] = {diskY - 15, diskY - 15, diskY - 20, diskY - 20, diskY - 30, diskY - 30, diskY - 35, diskY - 35, diskY - 25, diskY - 25};
		c.fillPolygon (diskgLX, diskgLY, 10);
		// white lower right corner of the disk
		c.setColor (Color.white);
		c.fillRect (diskX - 5, diskY - 5, 10, 10);
		// pure white center of the disk
		c.setColor (new Color (237, 45, 45)); // Color is set to red
		for (int i = 0 ; i <= 5 ; i += 5) // Part of the rainbow pattern in the lower left corner of the disk
		{
		    c.fillRect (diskX - 40, diskY + 15 + i, 5, 5);
		    c.fillRect (diskX - 35, diskY + 10 + i, 10, 5);
		    c.fillRect (diskX - 25, diskY + 5 + i, 5, 5);
		    c.setColor (new Color (245, 119, 61)); // Color is set to orange
		}
		c.setColor (new Color (240, 240, 62)); // Color is set to yellow
		c.fillRect (diskX - 35, diskY + 20, 5, 5);
		for (int i = 0 ; i <= 15 ; i += 5) // Part of the rainbow pattern in the lower left corner of the disk
		{
		    c.fillRect (diskX - 35 + i, diskY + 25 - i, 5, 5);
		}
		c.setColor (new Color (237, 133, 187)); // Color is set to pink
		c.fillRect (diskX - 30, diskY + 25, 5, 10);
		c.fillRect (diskX - 25, diskY + 20, 5, 5);
		// Part of the rainbow pattern in the lower left corner of the disk
		c.setColor (new Color (29, 171, 46)); // Color is set to green
		for (int i = 0 ; i <= 5 ; i += 5)
		{
		    c.fillRect (diskX - 30 + i, diskY + 35, 5, 5);
		    c.fillRect (diskX - 25 + i, diskY + 25, 5, 10);
		    c.fillRect (diskX - 20 + i, diskY + 15, 5, 10);
		    // Part of the rainbow pattern in the lower left corner of the disk
		    c.setColor (new Color (65, 224, 203)); // Color is set to light blue
		}
		c.setColor (new Color (191, 198, 201));
		c.fillRect (diskX - 30, diskY + 35, 5, 5);
		c.setColor (new Color (237, 45, 45)); // Color is set to red
		for (int i = 0 ; i <= 5 ; i += 5) // Part of the rainbow pattern in the upper right corner of the disk
		{
		    c.fillRect (diskX + 20, diskY - 10 - i, 5, 5);
		    c.fillRect (diskX + 25, diskY - 15 - i, 10, 5);
		    c.fillRect (diskX + 35, diskY - 20 - i, 5, 5);
		    c.setColor (new Color (245, 119, 61)); // Color is set to orange
		}
		c.setColor (new Color (240, 240, 62)); // Color is set to yellow
		c.fillRect (diskX + 30, diskY - 25, 5, 5);
		for (int i = 0 ; i <= 15 ; i += 5) // Part of the rainbow pattern in the upper right corner of the disk
		{
		    c.fillRect (diskX + 15 + i, diskY - 15 - i, 5, 5);
		}
		c.setColor (new Color (237, 133, 187)); // Color is set to pink
		c.fillRect (diskX + 20, diskY - 25, 5, 5);
		c.fillRect (diskX + 25, diskY - 35, 5, 10);
		// Part of the rainbow pattern in the upper right corner of the disk
		c.setColor (new Color (29, 171, 46)); // Color is set to green
		for (int i = 0 ; i <= 5 ; i += 5) // Part of the rainbow pattern in the upper right corner of the disk
		{
		    c.fillRect (diskX + 10 + i, diskY - 25, 5, 10);
		    c.fillRect (diskX + 15 + i, diskY - 35, 5, 10);
		    c.fillRect (diskX + 20 + i, diskY - 40, 5, 5);
		    c.setColor (new Color (65, 224, 203));
		}
		c.setColor (new Color (191, 198, 201)); // Color is set to light blue
		c.fillRect (diskX + 25, diskY - 40, 5, 5); // Part of the rainbow pattern in the upper right corner of the disk
	    }
	    else if (levelNum == 2) // if level chosen is 3. Canadian
	    {
		c.setColor (Color.white);
		c.fillRect (500, 135, 140, 70);
		c.setColor (Color.red);
		c.fillRect (500, 135, 40, 70);
		c.fillRect (600, 135, 40, 70);
		c.fillMapleLeaf (545, 145, 50, 50);
		// draws the Canadian flag
	    }
	    c.setColor (Color.black);
	    c.drawLine (475, 100, 475, 450);
	    c.drawLine (665, 100, 665, 450);
	    // vertical parallel lines on guess box
	    c.setFont (new Font ("Serif.ITALIC", 10, 20));
	    c.drawString ("Enter a number:", 500, 230);
	    // prompt for number input
	    if (levelNum == 0)
	    {
		c.setColor (Color.white);
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (Color.black);
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (Color.red);
	    }
	    c.fillRect (480, 240, 180, 20);
	    // box for number input
	    if (guessWord == false) // if the user is entering a number for guess
	    {
		for (int i = 0 ; i <= 10 ; i++) // arrows indicate a number input if guessWord is false
		{
		    if (levelNum == 0)
		    {
			c.setColor (Color.blue);
		    }
		    else if (levelNum == 1)
		    {
			c.setColor (Color.yellow);
		    }
		    else if (levelNum == 2)
		    {
			c.setColor (Color.red);
		    }
		    // colour of the arrows depend on levelNum
		    c.drawLine (460 + i, 238 + i, 460 + i, 258 - i);
		    c.drawLine (680 - i, 238 + i, 680 - i, 258 - i);
		}
	    }
	    else if (guessWord == true) // if user is entering a word after proper number input
	    {
		for (int i = 0 ; i <= 10 ; i++) // arrows indicate word input
		{
		    if (levelNum == 0)
		    {
			c.setColor (Color.blue);
		    }
		    else if (levelNum == 1)
		    {
			c.setColor (Color.yellow);
		    }
		    else if (levelNum == 2)
		    {
			c.setColor (Color.red);
		    }
		    // colour of the arrows depend on levelNum
		    c.drawLine (460 + i, 298 + i, 460 + i, 318 - i);
		    c.drawLine (680 - i, 298 + i, 680 - i, 318 - i);
		}
		c.setColor (Color.black);
		c.drawString ("Enter your guess:", 495, 290);
		c.setFont (new Font ("Serif", 10, 20));
		// prompt for word input
		if (levelNum == 0)
		{
		    c.setColor (Color.white);
		}
		else if (levelNum == 1)
		{
		    c.setColor (Color.black);
		}
		else if (levelNum == 2)
		{
		    c.setColor (Color.red);
		}
		// colour of the box depends on levelNum
		c.fillRect (480, 300, 180, 20); // box for word input
		if (puzzleLevel == 1) // cheats option is available is level is timed
		{
		    c.setColor (Color.black);
		    c.drawString ("CHEATS", 530, 350);
		    c.setFont (new Font ("Serif.ITALIC", 5, 12));
		    c.drawString ("*Enter as guess", 529, 361);
		    c.setColor (Color.white);
		    // guide for cheats
		    c.fillRoundRect (497, 367, 146, 36, 10, 10);
		    c.fillRoundRect (497, 412, 146, 36, 10, 10);
		    // background- box for letter/word cheat guide
		    c.setColor (Color.orange);
		    c.fillRoundRect (500, 370, 140, 30, 10, 10);
		    c.fillRoundRect (500, 415, 140, 30, 10, 10);
		    // foreground- box for letter/word cheat guide
		    c.setColor (Color.black);
		    c.setFont (new Font ("Serif", 10, 15));
		    c.drawString ("* - Reveal Character", 505, 390);
		    c.drawString ("# - Reveal Word", 517, 435);
		    // letter/word cheat guide
		}
	    }
	    if (levelNum == 0)
	    {
		c.setColor (Color.black);
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (new Color (89, 255, 48));
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (Color.white);
	    }
	    // colour of the user input (number/ guess) displayed depends on levelNum
	    c.setFont (new Font ("SansSerif", 1, 15));
	    c.drawString (numberString, 485, 255); // displays the number to enter to guess a word
	    c.drawString (guess, 485, 315); // displays the user's guess for a word that corresponds with the number
	}
    }


    // levelBackground() displays the background of the crossword game (level)- declared as a separate method for efficiency (redraw purposes)
    public void levelBackground ()
    {
	synchronized (c)
	{
	    if (levelNum == 0)
	    {
		c.setColor (new Color (204, 204, 0)); // Color is yellow-green
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (new Color (0, 88, 204)); // Color is blue
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (new Color (240, 240, 237)); // Color is light gray
	    }
	    for (int i = 0 ; i <= 720 ; i += 160)
	    {
		c.fillRect (i, 0, 80, 20);
		c.fillRect (i, 580, 80, 20);
	    }
	    for (int i = 100 ; i <= 480 ; i += 160)
	    {
		c.fillRect (0, i, 20, 80);
		c.fillRect (700, i, 20, 80);
	    }
	    // outer borders
	    if (levelNum == 0)
	    {
		c.setColor (new Color (154, 205, 50)); // Color is green-yellow
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (new Color (116, 95, 232)); // Color is purple
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (Color.red); // Color is red
	    }
	    for (int i = 80 ; i <= 640 ; i += 160)
	    {
		c.fillRect (i, 0, 80, 20);
		c.fillRect (i, 580, 80, 20);
	    }
	    for (int i = 20 ; i <= 560 ; i += 160)
	    {
		c.fillRect (0, i, 20, 80);
		c.fillRect (700, i, 20, 80);
	    }
	    // outer borders
	    if (levelNum == 0)
	    {
		c.setColor (new Color (128, 194, 142)); // Color is light green
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (new Color (128, 171, 194)); // Color is light purple
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (new Color (187, 147, 81)); // Color is maple-syrup
	    }
	    c.fillRect (20, 20, 680, 560); // foreground
	    if (levelNum == 0)
	    {
		c.setColor (new Color (255, 82, 82)); // Color is set to red-pink
	    }
	    else if (levelNum == 1)
	    {
		c.setColor (new Color (12, 135, 12)); // Color is set to neon-green
	    }
	    else if (levelNum == 2)
	    {
		c.setColor (Color.red);
	    }
	    c.fillRect (260, 25, 210, 50); // crossword theme banner
	    for (int i = 0 ; i <= 25 ; i++)
	    {
		c.drawLine (260, 25 + i, 210, 25);
		c.drawLine (260, 75 - i, 210, 75);
		c.drawLine (470, 25 + i, 520, 25);
		c.drawLine (470, 75 - i, 520, 75);
	    }
	    // part of the banner
	    c.setColor (Color.white);
	    c.setFont (new Font ("Serif.ITALIC", 1, 28));
	    if (levelNum == 0)
	    {
		c.drawString ("1. Environment", 263, 58);
	    }
	    else if (levelNum == 1)
	    {
		c.drawString ("2. Computers", 275, 58);
	    }
	    else if (levelNum == 2)
	    {
		c.drawString ("3. Canadian", 275, 58);
	    }
	    // crossword theme title is displayed on top of the banner
	    c.setColor (Color.black);
	    c.fillRect (52, 25, 100, 55);
	    // Outer background of the menu box
	    c.setColor (new Color (245, 242, 159));
	    c.fillRect (54, 27, 96, 51);
	    // Inner background of the menu box
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
	    c.drawString ("!", 55, 55);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 40));
	    c.drawString ("M", 65, 70);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 25));
	    c.drawString ("enu", 100, 70);
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 15));
	    c.drawString ("*Enter '!'", 70, 95);
	    // displays Menu box- instructs user how to exit the level
	}
    }


    /*
	Local Variable List- calculateLines(String name)

	Name            |   Data type    |   Description
       -----------------|------------ ---|-------------------------------------------------------------------------------------------------------------
	line            | String         | Used to read the text file given
	lineCounter     | int            | Used to calculate how many lines exist within the given text file using increments
    */
    // black box method- calculateLines() returns an int which represents how many lines exist within the given text file name
    public int calculateLines (String name)
    {
	int lineCounter = 0; // lineCounter starts at 0
	String line = "";
	try
	{
	    BufferedReader readerLines = new BufferedReader (new FileReader (name)); // name represents the fileName that will be read
	    while (line != null)
	    {
		line = readerLines.readLine (); // line reads each line of the text file until its value is null
		lineCounter++; // lineCounter incremently increases by 1
	    }
	    readerLines.close ();
	}
	catch (IOException e)
	{
	}
	return lineCounter; // lineCounter is returned
    }


    /*
	Local Variable List- levelScreen()

	Name            |   Data type    |   Description
       -----------------|------------ ---|-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	fileName        | String         | fileName represents the file name of the answer grid for the crossword
	fileNameDef     | String         | fileNameDef represents the file name of the answer key (definitions) for the crossword
	char            | char           | puzzleInput takes in all input from levelScreen- while user plays crossword game
	guessNum        | int            | number of the word within the crossword used to determine whether the guess is for an odd/even number
	startRow        | int            | row of the starting character of the word in relation to guessNum- used to calculate the word's length and input it in the according values within guessGrid
	sartCol         | int            | column of the starting character of the word in relation to guessNum- used to calculate the word's length and input it in the according values within guessGrid
	wordLength      | int            | determines the length of the word in relation to guessNum
	oneLetter       | String         | used to input upper case values as values of guessGrid
	cheat           | boolean        | determines if cheat option is available for use- ex. cheat option can't be used if all the characters of the word chosen are right
	win             | boolean        | determines if the user has won by comparing guessGrid and answerGrid
	wrongNum        | int            | used for character cheat option- determines how many numbers are wrong to cheat a random character out of the wrong/empty character
	wrongChar       | int[]          | used for character cheat option- determines the positions of the wrong characters from the starting position (startRow and startCol)
	wrongCounter    | int            | used to differentiate the different values for wrongChar when assigning values
	charCheat       | int            | generates a random value (for single character cheat option)
	placement       | int            | if the user wins game and level is timed- determines where the user placed (rank from 1-10)
	scoreUpdate     | String[][]     | if the user wins game and level is timed- used to update the high scores file
	score           | int            | if the user wins game and level is timed- calculates the user's score: cell completed without help x 10000 / time (s)
	cellComplete    | int            | if the user wins game and level is timed- determines how many cells were completed by the user without help to calculate score

    */
    // if user chooses to play from mainMenu(), user plays either 1. Environment, 2. Computers, or 3. Canadian (randomly selected) in levelScreen() until the user quits or wins
    public void levelScreen ()
    {
	String fileName = "";
	String fileNameDef = "";
	char puzzleInput = ' ';
	int guessNum = 0;
	int startRow = 0;
	int startCol = 0;
	int wordLength = 0;
	String oneLetter;
	boolean cheat = false;
	boolean win;
	int wrongNum;
	int wrongChar[];
	int wrongCounter;
	int charCheat;
	int placement = 0;
	int score = 0;
	int cellComplete = 0;
	String[] [] scoreUpdate = new String [10] [3]; // scoreUpdate was 10 rows representing the player info. of the top 10 ranking players and 3 rows representing the player information (level, player name, and score)
	levelNum = (int) (Math.random () * 3); // generates a random number between 0 and 2- determines level
	defNum = 0; // initially displays clue #1
	numberString = ""; // used to displays user input (number)
	guess = ""; // used to display user input (guess)
	pauseMenu = false; // determines whether the user is trying to exit the game- presses '!'
	guessWord = false; // determines whether the user is entering a number/guess
	if (puzzleLevel == 1) // if level is timed
	{
	    timerDisplay = true; // timer is displayed
	}
	if (levelNum == 0)
	{
	    fileNameDef = "LevelOne_AnsKey.txt";
	}
	else if (levelNum == 1)
	{
	    fileNameDef = "LevelTwo_AnsKey.txt";
	}
	else if (levelNum == 2)
	{
	    fileNameDef = "LevelThree_AnsKey.txt";
	}
	// definitions (clue box) file that will be read changes depending on the level chosen (0-2)
	if (levelNum == 0)
	{
	    fileName = "LevelOne_Grid.txt";
	}
	else if (levelNum == 1)
	{
	    fileName = "LevelTwo_Grid.txt";
	}
	else if (levelNum == 2)
	{
	    fileName = "LevelThree_Grid.txt";
	}
	// grid (answer key) file that will be read changes depending on the level chosen (0-2)
	answerGrid = new String [calculateLines (fileName) - 1] []; // answerGrid is the virtual 2D grid of answer key; number of rows is calculated using the black box method
	try
	{
	    BufferedReader readerOne = new BufferedReader (new FileReader (fileName));
	    for (int i = 0 ; i <= answerGrid.length - 1 ; i++) // reads the grid file again with a for loop after the number of rows has been identified
	    {
		answerGrid [i] = readerOne.readLine ().split (" "); // splits the row read by space- creates the columns of answer key
	    }
	    readerOne.close ();
	}
	catch (IOException e)
	{
	}
	linesFileDef = new String [calculateLines (fileNameDef) - 1]; // lines of the clue (definition) file; number of definitions is calculated using the black box method
	try
	{
	    BufferedReader readerOneDef = new BufferedReader (new FileReader (fileNameDef));
	    for (int i = 0 ; i <= linesFileDef.length - 1 ; i++) // reads the definition clue file again with a for loop after the number of clues has been identified
	    {
		linesFileDef [i] = readerOneDef.readLine (); // each line of the clue file is a value of linesFileDef
	    }
	    readerOneDef.close ();
	}
	catch (IOException e)
	{
	}
	guessGrid = new String [answerGrid.length] [answerGrid [0].length]; // guessGrid is the virtual 2D grid of the user's guesses; declared with the same dimensions as answerGrid
	for (int x = 0 ; x <= answerGrid.length - 1 ; x++) // rows of guessGrid
	{
	    for (int y = 0 ; y <= answerGrid [0].length - 1 ; y++) // columns of guessGrid
	    {
		guessGrid [x] [y] = "0"; // all values within guessGrid are initially set to 0- default value that is not displayed
	    }
	}
	c.clear (); // screen is cleared
	levelBackground (); // level background for the game is initially loaded
	puzzleGrid (); // crossword grid for the game is initially loaded
	clueBox (); // the definition box for the game is initially loaded
	guessBox (); // the guess box for the game is initially loaded
	while (true) // crossword game is played until the user either wins/exits via Menu option
	{
	    synchronized (c)
	    {
		if (guessNum != 0 && guessWord == true) // if user has entered a number
		{
		    // determines how many letters are in the corresponding word
		    for (int i = 0 ; i <= wordLength - 1 ; i++) // for loop highlights outline of the boxes within the word using the length of the word (wordLength) and the grid positioning
		    {
			if (levelNum == 0)
			{
			    c.setColor (Color.blue);
			}
			else if (levelNum == 1)
			{
			    c.setColor (new Color (206, 209, 6)); // Color is set to dark yellow
			}
			else if (levelNum == 2)
			{
			    c.setColor (new Color (255, 113, 64)); // Color is set to light orange
			}
			if (guessNum / 2 * 2 == guessNum) // even number
			{
			    c.drawRect (35 + (startCol + i) * 20, 95 + startRow * 20, 20, 20);
			    c.drawRect (36 + (startCol + i) * 20, 96 + startRow * 20, 18, 18);
			    // rectangular outline- highlighted box is drawn
			}
			else // odd number
			{
			    c.drawRect (35 + startCol * 20, 95 + (startRow + i) * 20, 20, 20);
			    c.drawRect (36 + startCol * 20, 96 + (startRow + i) * 20, 18, 18);
			    // rectangular outline- highlighted box is drawn
			}
		    }
		}
		if (pauseMenu == true) // If user has pressed '!' indicating that the user wants to quit the level
		{
		    c.setColor (new Color (0, 0, 0, 210)); // Color is set to transparent black
		    c.fillRect (0, 0, 720, 620);
		    // black transparent box covers the screen
		    c.setColor (Color.black);
		    c.fillRect (52, 25, 100, 55); // outer background for menu box title
		    c.setColor (new Color (245, 242, 159)); // Color is set to light yellow
		    c.fillRect (54, 27, 96, 51); // inner background for menu box title
		    c.setColor (Color.black);
		    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
		    c.drawString ("!", 55, 55);
		    c.setFont (new Font ("SansSerif.BOLD", 1, 40));
		    c.drawString ("M", 65, 70);
		    c.setFont (new Font ("SansSerif.BOLD", 1, 25));
		    c.drawString ("enu", 100, 70);
		    // ! Menu- menu box title
		    for (int i = 0 ; i <= 20 ; i++) // draws arrow indicators for the menu box
		    {
			c.setColor (Color.blue);
			c.drawLine (27 + i, 35 + i, 27 + i, 75 - i);
			c.drawLine (177 - i, 35 + i, 177 - i, 75 - i);
		    }
		    c.setColor (Color.white);
		    c.setFont (new Font ("SansSerif.BOLD", 1, 15));
		    c.drawString ("*WARNING- ERASES CURRENT GAMEPLAY", 50, 100);
		    c.drawString ("Enter ENTER to exit", 50, 120);
		    c.drawString ("Press ANY OTHER KEY to continue game", 50, 140);
		    // Warning + Additional instructions if '!' is pressed
		}
		win = true; // win condition is true at the start of each while loop cycle
		synchronized (c)
		{
		    puzzleInput = c.getChar (); // takes in input of the puzzle
		    if (puzzleInput == '<' || puzzleInput == ',') // if user wants to see the previous clue
		    {
			if (defNum == 0) // displays the last clue if the user is at the first clue
			{
			    defNum = 15;
			}
			else // Otherwise, previous clue is shown
			{
			    defNum--;
			}
			clueBox (); // clueBox method updates the clue box display in-game
		    }
		    else if (puzzleInput == '>' || puzzleInput == '.') // if user wants to see the next clue
		    {
			if (defNum == 15) // displays the first clue if the user is at the last clue
			{
			    defNum = 0;
			}
			else // Otherwise, next clue is shown
			{
			    defNum++;
			}
			clueBox (); // clueBox method updates the clue box display in-game
		    }
		    else if (puzzleInput == '!') // indicator of menu box pops up if user presses '!'
		    {
			pauseMenu = true;
		    }
		    else if (puzzleInput == '\n' && pauseMenu == true) // if player presses '!' and then enter- user confirms to exit game
		    {
			playerName = ""; // player name is reset
			timerDisplay = false; // timer is no longer displayed/ runs
			break; // program leaves the game and goes back to main menu
		    }
		    else if (puzzleInput != '\n' && pauseMenu == true) // if user has pressed '!' but hasn't entered
		    {
			pauseMenu = false; // indicators are no longer shown
			levelBackground (); // background of the level is refreshed
			puzzleGrid (); // 2D grid of the level is refreshed
			guessBox (); // guess box of the level is refreshed
			clueBox (); // definitions box of the level is refreshed
		    }
		    else if (guessWord == false) // if input is being inputted for "Enter a number:"
		    {
			if (puzzleInput == '\n') // If the user enters
			{
			    try
			    {
				guessNum = Integer.parseInt (numberString); // parses the user's number (String) into an int for errortrapping
				if (guessNum < 1 || guessNum > 16) // Exception thrown if number is not between 1 and 16
				{
				    throw new Exception ();
				}
				guessWord = true; // Otherwise- user input is now taken in as guess to corresponding number
				guessBox (); // guess box of the level is refreshed to display "Enter your guess"
				defNum = guessNum - 1; // the clue is updated to the user's number for efficiency
				clueBox (); // clue box is refreshed to auomatically display relevant clue
				if (levelNum == 0) // if level chosen is 1. Environment
				{
				    switch (guessNum) // switch cases for guessNum decide the startRow and startCol
				    {
					case 1:
					    startRow = 5;
					    startCol = 0;
					    break;
					case 2:
					    startRow = 9;
					    startCol = 0;
					    break;
					case 3:
					    startRow = 3;
					    startCol = 3;
					    break;
					case 4:
					    startRow = 5;
					    startCol = 3;
					    break;
					case 5:
					    startRow = 0;
					    startCol = 12;
					    break;
					case 6:
					    startRow = 2;
					    startCol = 10;
					    break;
					case 7:
					    startRow = 1;
					    startCol = 15;
					    break;
					case 8:
					    startRow = 3;
					    startCol = 14;
					    break;
					case 9:
					    startRow = 4;
					    startCol = 10;
					    break;
					case 10:
					    startRow = 6;
					    startCol = 10;
					    break;
					case 11:
					    startRow = 8;
					    startCol = 6;
					    break;
					case 12:
					    startRow = 8;
					    startCol = 12;
					    break;
					case 13:
					    startRow = 7;
					    startCol = 17;
					    break;
					case 14:
					    startRow = 11;
					    startCol = 13;
					    break;
					case 15:
					    startRow = 11;
					    startCol = 14;
					    break;
					case 16:
					    startRow = 13;
					    startCol = 10;
					    break;
				    }
				    // startRow and startCol represent the starting row and column of guessGrid of the word correspoding to guessNum; used to calculate the word's length and input it in the according values within guessGrid
				}
				if (levelNum == 1) // If level chosen is 2. Computers
				{
				    switch (guessNum) // switch cases for guessNum decide the startRow and startCol
				    {
					case 1:
					    startRow = 0;
					    startCol = 6;
					    break;
					case 2:
					    startRow = 4;
					    startCol = 4;
					    break;
					case 3:
					    startRow = 8;
					    startCol = 5;
					    break;
					case 4:
					    startRow = 6;
					    startCol = 3;
					    break;
					case 5:
					    startRow = 11;
					    startCol = 1;
					    break;
					case 6:
					    startRow = 9;
					    startCol = 0;
					    break;
					case 7:
					    startRow = 9;
					    startCol = 7;
					    break;
					case 8:
					    startRow = 16;
					    startCol = 1;
					    break;
					case 9:
					    startRow = 5;
					    startCol = 9;
					    break;
					case 10:
					    startRow = 4;
					    startCol = 11;
					    break;
					case 11:
					    startRow = 7;
					    startCol = 11;
					    break;
					case 12:
					    startRow = 8;
					    startCol = 11;
					    break;
					case 13:
					    startRow = 3;
					    startCol = 13;
					    break;
					case 14:
					    startRow = 10;
					    startCol = 13;
					    break;
					case 15:
					    startRow = 12;
					    startCol = 13;
					    break;
					case 16:
					    startRow = 12;
					    startCol = 6;
					    break;
				    }
				    // startRow and startCol represent the starting row and column of guessGrid of the word correspoding to guessNum; used to calculate the word's length and input it in the according values within guessGrid
				}
				if (levelNum == 2) // If level chosen is 3. Canadian
				{
				    switch (guessNum) // switch cases for guessNum decide the startRow and startCol
				    {
					case 1:
					    startRow = 7;
					    startCol = 2;
					    break;
					case 2:
					    startRow = 9;
					    startCol = 1;
					    break;
					case 3:
					    startRow = 0;
					    startCol = 16;
					    break;
					case 4:
					    startRow = 1;
					    startCol = 11;
					    break;
					case 5:
					    startRow = 4;
					    startCol = 4;
					    break;
					case 6:
					    startRow = 5;
					    startCol = 3;
					    break;
					case 7:
					    startRow = 1;
					    startCol = 7;
					    break;
					case 8:
					    startRow = 6;
					    startCol = 9;
					    break;
					case 9:
					    startRow = 4;
					    startCol = 9;
					    break;
					case 10:
					    startRow = 11;
					    startCol = 11;
					    break;
					case 11:
					    startRow = 4;
					    startCol = 14;
					    break;
					case 12:
					    startRow = 13;
					    startCol = 1;
					    break;
					case 13:
					    startRow = 8;
					    startCol = 17;
					    break;
					case 14:
					    startRow = 13;
					    startCol = 6;
					    break;
					case 15:
					    startRow = 13;
					    startCol = 4;
					    break;
					case 16:
					    startRow = 16;
					    startCol = 0;
					    break;
				    }
				    // startRow and startCol represent the starting row and column of guessGrid of the word correspoding to guessNum; used to calculate the word's length and input it in the according values within guessGrid
				}
				while (true) // while loop determines the length of the word corresponding to the number
				{
				    if (guessNum / 2 * 2 == guessNum) // even number: horizontal length is determined
				    {
					if (answerGrid [startRow] [startCol + wordLength].equals ("0")) // exits while loop if the next horizontal value to the right is 0- default empty value
					{
					    break;
					}
					else if (startCol + wordLength == answerGrid [0].length - 1) // wordLength increases once before breaking if the current value is the last horizonal value right (before border)
					{
					    wordLength++;
					    break;
					}
					wordLength++; // wordLength incremently increases if the next value to the right is not empty
				    }
				    else // odd number: vertical length is determined
				    {
					if (answerGrid [startRow + wordLength] [startCol].equals ("0")) // exits while loop if the next vertical value down is 0- default empty value
					{
					    break;
					}
					else if (startRow + wordLength == answerGrid.length - 1) // wordLength increases once before breaking if the current value is the last vertical value down (before border)
					{
					    wordLength++;
					    break;
					}
					wordLength++;
				    }
				}
			    }
			    catch (Exception e)  // Error message- if input for "Enter a number" is not from 1-16
			    {
				JOptionPane.showMessageDialog (null, "Your input should be from 1-16. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
			else if ((int) puzzleInput > 47 && (int) puzzleInput < 58) // If user is entering a number and input is a number character (0-9)
			{
			    try
			    {
				numberString += puzzleInput; // input character is added to numberString
				if (numberString.length () > 2) // Exception is thrown if length of numberString exceeds 2
				{
				    throw new Exception ();
				}
			    }
			    catch (Exception e)  // Error message- length of numberString
			    {
				if (numberString.length () > 2) // if numberString's length exceeds 2
				{
				    numberString = numberString.substring (0, numberString.length () - 1); // deletes last character
				}
				JOptionPane.showMessageDialog (null, "Your input should be two digits maximum. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
			else if ((int) puzzleInput == 8) // If user in inputting a number and input is a backspace
			{
			    if (numberString.length () == 1) // numberString is reset if its length is 1
			    {
				numberString = "";
			    }
			    else if (numberString.length () > 1) // if length of numberString is 2 or more, the last character is deleted using substring
			    {
				numberString = numberString.substring (0, numberString.length () - 1);
			    }
			}
			guessBox (); // guessBox is refreshed to display updated user input (number)
		    }
		    else if (guessWord == true) // If user is guessing for a word
		    {
			if (puzzleInput == '\n') // If user enters
			{
			    if (guess.equals ("*")) // If user is trying to reveal a singular character (cheat option)
			    {
				if (guessNum / 2 * 2 == guessNum) // even number
				{
				    for (int i = 0 ; i <= wordLength - 1 ; i++) // for loop determines if there is at least one character that is incorrect for reveal
				    {
					if (!guessGrid [startRow] [startCol + i].equals ("1")) // if the guessGrid value is not a cheat- 1 indicates cheat reveal and is permanent
					{
					    if (!guessGrid [startRow] [startCol + i].equals (answerGrid [startRow] [startCol + i])) // if the guessGrid value is not equal to answerGrid value (wrong value)
					    {
						cheat = true; // cheat option is available
					    }
					}
				    }
				}
				else // odd number
				{
				    for (int i = 0 ; i <= wordLength - 1 ; i++) // for loop determines if there is at least one character that is incorrect for reveal
				    {
					if (!guessGrid [startRow + i] [startCol].equals ("1")) // if the guessGrid value is not a cheat- 1 indicates cheat reveal and is permanent
					{
					    if (!guessGrid [startRow + i] [startCol].equals (answerGrid [startRow + i] [startCol])) // if the guessGrid value is not equal to answerGrid value (wrong value)
					    {
						cheat = true; // cheat option is available
					    }
					}
				    }
				}
				if (cheat == false) // Error message- if all numbers are correct
				{
				    JOptionPane.showMessageDialog (null, "All the characters for " + guessNum + " are all correct.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else // If cheat is true- at least one character within the word is false; Cheat option- character reveal
				{
				    wrongNum = 0; // number of wrong characters is initially set to 0
				    for (int i = 0 ; i <= wordLength - 1 ; i++) // for loop determines how many characters within the word for the number given is wrong
				    {
					if (guessNum / 2 * 2 == guessNum && !guessGrid [startRow] [startCol + i].equals (answerGrid [startRow] [startCol + i]) && !guessGrid [startRow] [startCol + i].equals ("1"))
					{
					    wrongNum++; // wrongNum incrementely increases
					}
					// even number- value that is wrong and not 1
					else if (guessNum / 2 * 2 != guessNum && !guessGrid [startRow + i] [startCol].equals (answerGrid [startRow + i] [startCol]) && !guessGrid [startRow + i] [startCol].equals ("1"))
					{
					    wrongNum++; // wrongNum incrementely increases
					}
					// odd number- value that is wrong and not 1
				    }
				    wrongCounter = 0; // number of wrong words is first set to 0
				    wrongChar = new int [wrongNum]; // used to contain the positions of the wrong characters from the starting position
				    for (int i = 0 ; i <= wordLength - 1 ; i++) // for loop determines the position of the wrong character in relation to startRow and startCol
				    {
					if (guessNum / 2 * 2 == guessNum && !guessGrid [startRow] [startCol + i].equals (answerGrid [startRow] [startCol + i]) && !guessGrid [startRow] [startCol + i].equals ("1"))
					{
					    wrongChar [wrongCounter] = i; // position of wrong character is stored
					    wrongCounter++; // moves to next value within array wrongChar
					}
					// even number- value that is wrong and not 1
					else if (guessNum / 2 * 2 != guessNum && !guessGrid [startRow + i] [startCol].equals (answerGrid [startRow + i] [startCol]) && !guessGrid [startRow + i] [startCol].equals ("1"))
					{
					    wrongChar [wrongCounter] = i; // position of wrong character is stored
					    wrongCounter++; // moves to next value within array wrongChar
					}
					// odd number- value that is wrong and not 1
				    }
				    charCheat = (int) (Math.random () * (wrongNum)); // a random value is generated depending on how many values are wrong
				    if (guessNum / 2 * 2 == guessNum) // horizontal value is modified for even number
				    {
					guessGrid [startRow] [startCol + wrongChar [charCheat]] = "1"; // one value within the word is switched to 1- cheat option
				    }
				    else if (guessNum / 2 * 2 != guessNum) // vertical value is modified for odd number
				    {
					guessGrid [startRow + wrongChar [charCheat]] [startCol] = "1"; // one value within the word is switched to 1- cheat option
				    }
				    cheat = false; // cheat option becomes false
				}
			    }
			    else if (guess.equals ("#")) // Cheat option- if user wants to reveal an entire word
			    {
				if (guessNum / 2 * 2 == guessNum) // even number
				{
				    for (int i = 0 ; i <= wordLength - 1 ; i++) // for loop determines if there is at least one character that is incorrect for reveal
				    {
					if (!guessGrid [startRow] [startCol + i].equals ("1")) // if the guessGrid value is not a cheat- 1 indicates cheat reveal and is permanent
					{
					    if (!guessGrid [startRow] [startCol + i].equals (answerGrid [startRow] [startCol + i])) // if the guessGrid value is not equal to answerGrid value (wrong value)
					    {
						cheat = true; // cheat option is available
					    }
					}
				    }
				}
				else // odd number
				{
				    for (int i = 0 ; i <= wordLength - 1 ; i++) // for loop determines if there is at least one character that is incorrect for reveal
				    {
					if (!guessGrid [startRow + i] [startCol].equals ("1")) // if the guessGrid value is not a cheat- 1 indicates cheat reveal and is permanent
					{
					    if (!guessGrid [startRow + i] [startCol].equals (answerGrid [startRow + i] [startCol])) // if the guessGrid value is not equal to answerGrid value (wrong value)
					    {
						cheat = true; // cheat option is available
					    }
					}
				    }
				}
				if (cheat == false) // Error message- if all values are correct
				{
				    JOptionPane.showMessageDialog (null, "All the characters for " + guessNum + " are all correct.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else // If cheat option is available- Cheat option: word reveal
				{
				    for (int i = 0 ; i <= wordLength - 1 ; i++) // runs through all the characters of that number using the length of the word
				    {
					if (guessNum / 2 * 2 == guessNum && !guessGrid [startRow] [startCol + i].equals (answerGrid [startRow] [startCol + i]))
					{
					    guessGrid [startRow] [startCol + i] = "1"; // using startRow and startCol, all values to the right up to wordLength are replaced with "1"
					}
					// even number
					else if (guessNum / 2 * 2 != guessNum && !guessGrid [startRow + i] [startCol].equals (answerGrid [startRow + i] [startCol]))
					{
					    guessGrid [startRow + i] [startCol] = "1"; // using startRow and startCol, all values down up to wordLength are replaced with "1"
					}
					// odd number
				    }
				    if (defNum < 16) // if definition ranges from 0-15
				    {
					guessNum %= 16; // the 15th clue will result in guessNum being 0
					defNum = guessNum; // shows the next clue for efficiency
					clueBox (); // updates clue box
				    }
				    cheat = false; // cheat option is not available for the next cycle
				}
			    }
			    else // If user presses enter and is not revealing a cheat
			    {
				try
				{
				    if (guess.length () != wordLength) // Exception thrown if length of guess is not equal to wordLength
				    {
					throw new Exception ();
				    }
				    if (defNum < 16) // If length of guess is right and definition ranges from 0 to 15
				    {
					guessNum %= 16; // the 15th clue will result in guessNum being 0
					defNum = guessNum; // shows the next clue for efficiency
					clueBox (); // updates clue box
				    }
				    oneLetter = ""; // used to input an upper case letter as a value in guessGrid
				    for (int i = 0 ; i <= wordLength - 1 ; i++)
				    {
					oneLetter = String.valueOf (guess.charAt (i)); // each character of guess is converted into a String
					if (guessNum / 2 * 2 == guessNum) // Even number
					{
					    if (!guessGrid [startRow] [startCol + i].equals ("1")) // As long as the horizontal value isn't 1- 1 indicates cheat option and is permanent
					    {
						guessGrid [startRow] [startCol + i] = oneLetter.toUpperCase (); // using startRow and startCol, capital of oneLetter is inputted into its respective place in guessGrid
					    }
					}
					else // Odd number
					{
					    if (!guessGrid [startRow + i] [startCol].equals ("1")) // As long as the vertical value isn't 1- 1 indicates cheat option and is permanent
					    {
						guessGrid [startRow + i] [startCol] = oneLetter.toUpperCase (); // using startRow and startCol, capital of oneLetter is inputted into its respective place in guessGrid
					    }
					}
				    }
				}
				// Exception- if length doesn't match
				catch (Exception e)
				{
				    JOptionPane.showMessageDialog (null, "Your guess should be " + wordLength + " characters, not " + guess.length () + " characters! Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
				    continue; // returns back to the start of the loop
				}
			    }
			    puzzleGrid (); // Puzzle grid is refreshed if guess is successfully inputted
			    numberString = ""; // numberString is reset (takes in number)
			    guess = ""; // guess is reset (guess of word)
			    guessWord = false; // if word is succesfully inputted, user input goes back to number
			    wordLength = 0; // length of the word is reset to 0
			}
			else if (((int) puzzleInput > 96 && (int) puzzleInput < 123) || ((int) puzzleInput > 64 && (int) puzzleInput < 91) || (int) puzzleInput == 35 || (int) puzzleInput == 42) // If puzzleInput is a lowercase/uppercase letter and the user is entering a guess
			{
			    try
			    {
				if (puzzleLevel == 1 || (puzzleLevel == 0 && ((int) puzzleInput != 35 && (int) puzzleInput != 42))) // if level is timed or level is untimed and the input is not '*' or '#'- cheat option only available for timed games
				{
				    guess += puzzleInput; // puzzleInput is added to guess
				}
				if (levelNum == 0) // if level chosen is 1. Environment
				{
				    if (guess.length () >= 14) // maximum length of guess is 14
				    {
					throw new Exception ();
				    }
				}
				else if (levelNum == 1) // if level chosen is 2. Computers
				{
				    if (guess.length () >= 11) // maximum length of guess is 11
				    {
					throw new Exception ();
				    }
				}
				else if (levelNum == 2) // if level chosen is 3. Canadian
				{
				    if (guess.length () >= 11) // maximum length of guess is 11
				    {
					throw new Exception ();
				    }
				}
			    }
			    catch (Exception e)  // Exception- if length of guess is above the maximum character limit
			    {
				JOptionPane.showMessageDialog (null, "You have exceeded the maximum character limit. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
				guess = guess.substring (0, guess.length () - 1); // last character is deleted
			    }
			    if (puzzleLevel == 1 && guess.length () == 2 && (guess.charAt (0) == '#' || guess.charAt (0) == '*' || guess.charAt (1) == '#' || guess.charAt (1) == '*')) // If your level is timed and the guess contains a cheat but is more than one character
			    {
				JOptionPane.showMessageDialog (null, "Except cheats, your guess must have letters only. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
				guess = guess.substring (0, guess.length () - 1); // last character is deleted
			    }
			}
			else if ((int) puzzleInput == 8 || (int) puzzleInput == 127) // If user is entering a guess and puzzleInput is a backspace
			{
			    if (guess.length () == 1) // guess is reset if length is 1
			    {
				guess = "";
			    }
			    else if (guess.length () > 1) // if guess's length is more than 1, the last character is deleted using substring
			    {
				guess = guess.substring (0, guess.length () - 1);
			    }
			}
			guessBox (); // guessBox is refreshed to update display of user input (guess)
		    }
		    for (int row = 0 ; row < answerGrid.length ; row++) // rows of answer grid
		    {
			for (int column = 0 ; column < answerGrid [0].length ; column++) // columns of answer grid
			{
			    if (!answerGrid [row] [column].equals (guessGrid [row] [column]) && !guessGrid [row] [column].equals ("1")) // Except cheat values, if a value in answerGrid does not equal the value in guessGrid in the same position
			    {
				win = false; // As the win condition is true each time, win is set to false if the values aren't equal
				break;
			    }
			}
		    }
		    if (puzzleLevel == 1 && win) // score is calculated if user has won and puzzle level is timed
		    {
			for (int row = 0 ; row < answerGrid.length ; row++) // rows of crossword
			{
			    for (int column = 0 ; column < answerGrid [0].length ; column++) // columns of crossword
			    {
				if (answerGrid [row] [column].equals (guessGrid [row] [column]) && !guessGrid [row] [column].equals ("1") && !guessGrid [row] [column].equals ("0"))
				    // if the guessGrid value is not 0, 1, and equals answerGrid value of the same position
				    {
					cellComplete++; // cellComplete increases by 1
				    }
			    }
			}
			score = cellComplete * 10000 / Timer.elapsedSeconds; // score is cells completed without help * 1000/ time (seconds)
			timerDisplay = false; // timer is no longer displayed
			try
			{
			    BufferedReader readerCurrentScore = new BufferedReader (new FileReader ("HighScores_Crossword.txt"));
			    for (int i = 0 ; i <= 9 ; i++) // reads 10 lines of the high scores text file- current top 10 player information
			    {
				scoreUpdate [i] = readerCurrentScore.readLine ().split (" "); // player information is split into columns by space from their respective rows
			    }
			    readerCurrentScore.close ();
			}
			catch (IOException e)
			{
			}
			for (int i = 0 ; i <= 9 ; i++) // for loop is used to determine user placement; current user score is compared to the leaderboard scores (from highest to lowest)
			{
			    if (score >= Integer.parseInt (scoreUpdate [i] [1])) // If current score is equal to or greater than leaderboard score
			    {
				placement = i; // placement becomes i; rank difference from 1st- placement of 0 would mean user is 1st
				break;
			    }
			}
			try
			{
			    PrintWriter output = new PrintWriter (new FileWriter ("HighScores_Crossword.txt"));
			    for (int i = 0 ; i < placement ; i++) // prints the player information of players who have a score higher than the current user score
			    {
				output.println (scoreUpdate [i] [0] + " " + scoreUpdate [i] [1] + " " + scoreUpdate [i] [2]); // columns represent: 1- level 2- score 3- name
			    }
			    output.println (levelNum + " " + String.valueOf (score) + " " + playerName); // player information is printed in the high scores text file
			    for (int i = 0 ; i < 9 - placement ; i++) // player information of players (total players: 10) who were on the leaderboard but had a score lower than current user score
			    {
				output.println (scoreUpdate [placement + i] [0] + " " + scoreUpdate [placement + i] [1] + " " + scoreUpdate [placement + i] [2]); // columns represent: 1- level 2- score 3- name
			    }
			    output.close ();
			}
			catch (IOException e)
			{
			}
		    }
		    if (win) // if user was won the game
		    {
			c.setColor (new Color (0, 0, 0, 200));
			c.fillRect (0, 0, 720, 600);
			c.setColor (Color.white);
			c.setFont (new Font ("SansSerif.BOLD", 3, 50));
			c.drawString ("Congratulations!", 150, 200);
			// congratulations message
			if (puzzleLevel == 1) // displays score is puzzle is timed
			{
			    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
			    c.drawString ("Your final score was:", 210, 250);
			    c.setFont (new Font ("SansSerif.BOLD", 1, 40));
			    c.drawString (score + "", (720 - String.valueOf (score).length ()) / 2 - String.valueOf (score).length () * 8, 300); // displays centered score according to length
			}
			c.setFont (new Font ("SansSerif.BOLD", 1, 20));
			c.drawString ("Press ENTER to go back to main menu!", 180, 340);
			// main menu instructions
			while (true) // returns back to main menu if user presses enters
			{
			    if (c.getChar () == '\n')
			    {
				break;
			    }
			}
			playerName = ""; // player name resets
			break; // program goes back to main menu
		    }
		}
	    }
	}
    }


    /*
	Local Variable List- instructions()

	Name            |   Data type    |   Description
       -----------------|------------ ---|-------------------------------------------------------------------------------------------------------------
	INSTRUCT_ONE    | final String   | Used for graphics- displays the first topic of instructions- GENERAL
	INSTRUCT_TWO    | final String   | Used for graphics- displays the second topic of instructions- PLAY
	INSTRUCT_THREE  | final String   | Used for graphics- displays the third topic of instructions- LEADERBOARD
	instructOption  | char           | Checks whether the user has inputted '!' before pressing enter to go back to main menu
    */
    // instructions displays instructions for general, play, and leaderboard for the crossword program
    public void instructions ()
    {
	final String INSTRUCT_ONE = "GENERAL";
	final String INSTRUCT_TWO = "PLAY";
	final String INSTRUCT_THREE = "LEADERBOARD";
	char instructOption;
	pauseMenu = false; // program goes back to main menu if user enters '!' (pauseMenu is true)
	menuBackground (); // displays a basic background- checkered crossword
	c.setColor (Color.black);
	c.fillRoundRect (280, 140, 160, 50, 25, 25);
	c.setColor (Color.white);
	c.fillRoundRect (283, 143, 154, 44, 25, 25);
	// displays background of the title
	c.setColor (Color.black);
	c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	c.drawString ("2", 295, 160);
	c.setFont (new Font ("SansSerif.BOLD", 1, 35));
	c.drawString ("I", 310, 175);
	c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	c.drawString ("nstructions", 320, 175);
	// displays title of the screen- Instructions
	for (int i = 0 ; i <= 448 ; i += 28)
	{
	    c.setColor (Color.black);
	    c.fillRect (20, 104 + i, 33, 28);
	    c.setColor (Color.white);
	    c.fillRect (20, 104 + i, 28, 28);
	    c.setColor (Color.black);
	    c.drawRect (20, 104 + i, 28, 28);
	}
	// displays vertical white crossword piece on the left side of the screen
	for (int i = 0 ; i <= 196 ; i += 28)
	{
	    c.setColor (Color.black);
	    c.fillRect (48 + i, 188, 33, 33);
	    c.setColor (Color.white);
	    c.fillRect (48 + i, 188, 28, 28);
	    c.setColor (Color.black);
	    c.drawRect (48 + i, 188, 28, 28);
	}
	// crossword background for first topic- GENERAL
	c.setFont (new Font ("SansSerif.BOLD", 1, 28));
	for (int i = 0 ; i <= 6 ; i++) // displays each character of the title of the first topic- GENERAL
	{
	    c.drawString (String.valueOf (INSTRUCT_ONE.charAt (i)), 50 + i * 28, 212);
	}
	c.setColor (DARK_GRAY);
	c.fillRect (245, 189, 27, 27);
	// part of crossword background for title of first topic
	c.setColor (Color.black);
	c.setFont (new Font ("Helvetica", 4, 15));
	c.drawString ("This game consists of three thematic puzzles which will be randomly selected and built across", 60, 240);
	c.drawString ("and down with clues. Each puzzle has two levels (timed and untimed), and the game detects when", 60, 260);
	c.drawString ("a win has occurred. The game automatically resets if the user plays again.", 60, 280);
	c.setFont (new Font ("Helvetica", 3, 15));
	c.drawString ("*All input must be followed by an enter to be registered!", 60, 310);
	// GENERAL- instructions
	for (int i = 0 ; i <= 140 ; i += 28)
	{
	    c.setColor (Color.black);
	    c.fillRect (48 + i, 328, 33, 33);
	    c.setColor (Color.white);
	    c.fillRect (48 + i, 328, 28, 28);
	    c.setColor (Color.black);
	    c.drawRect (48 + i, 328, 28, 28);
	}
	// crossword background for second topic- PLAY
	c.setFont (new Font ("SansSerif.BOLD", 1, 28));
	for (int i = 0 ; i <= 3 ; i++) // displays each character of the title of the second topic- PLAY
	{
	    c.drawString (String.valueOf (INSTRUCT_TWO.charAt (i)), 50 + i * 28, 352);
	}
	c.setColor (DARK_GRAY);
	c.fillRect (189, 329, 27, 27);
	c.fillRect (161, 329, 27, 27);
	// part of crossword background for title of second topic
	c.setColor (Color.black);
	c.setFont (new Font ("Helvetica", 4, 15));
	c.drawString ("a) A cheat option will be available in timed games for testing.", 60, 380);
	c.drawString ("b) Cells answered using cheats are permanent and can't be changed.", 75, 400);
	c.drawString ("c) Your guess should contain letters only- no symbols!", 60, 420);
	c.drawString ("d) Scores will not be considered if the user quits their level at any time.", 60, 440);
	c.drawString ("e) Color Legend: Black- Right; Red- Wrong; Blue- Cheat", 60, 460);
	// PLAY- instructions
	for (int i = 0 ; i <= 308 ; i += 28)
	{
	    c.setColor (Color.black);
	    c.fillRect (48 + i, 468, 33, 33);
	    c.setColor (Color.white);
	    c.fillRect (48 + i, 468, 28, 28);
	    c.setColor (Color.black);
	    c.drawRect (48 + i, 468, 28, 28);
	}
	// crossword background for third topic- LEADERBOARD
	c.setFont (new Font ("SansSerif.BOLD", 1, 28));
	for (int i = 0 ; i <= 10 ; i++) // displays each character of the title of the third topic- LEADERBOARD
	{
	    c.drawString (String.valueOf (INSTRUCT_THREE.charAt (i)), 78 + i * 28, 492);
	}
	c.setColor (DARK_GRAY);
	c.fillRect (49, 469, 27, 27);
	c.setFont (new Font ("Helvetica", 4, 15));
	c.setColor (Color.black);
	c.drawString ("The point system is only considered for timed levels:", 60, 520);
	c.drawString ("Score: Number of cells completed without help x 10,000 / (time [seconds])", 90, 540);
	// LEADERBOARD- instructions
	while (true) // loop displays instruction page until user enters '!' to go back to main menu
	{
	    c.setColor (new Color (176, 176, 176, 100));
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (20 + x, 20, 40, 40);
	    }
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (60 + x, 60, 40, 40);
	    }
	    c.setColor (Color.white);
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (60 + x, 20, 40, 40);
	    }
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (20 + x, 60, 40, 40);
	    }
	    // redraws part of the checkered crossword background for exit instructions- Menu box
	    c.setColor (Color.black);
	    c.fillRect (52, 25, 100, 55);
	    c.setColor (new Color (245, 149, 149));
	    c.fillRect (54, 27, 96, 51);
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
	    c.drawString ("!", 55, 55);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 40));
	    c.drawString ("M", 65, 70);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 25));
	    c.drawString ("enu", 100, 70);
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 15));
	    c.drawString ("*Enter '!'", 70, 95);
	    // Menu box- exit instructions
	    if (pauseMenu == true) // arrow indicators if user chooses to exit by pressing '!'
	    {
		for (int i = 0 ; i <= 20 ; i++) // draws two arrows pointed at the menu box
		{
		    c.setColor (Color.black);
		    c.drawLine (27 + i, 35 + i, 27 + i, 75 - i);
		    c.drawLine (177 - i, 35 + i, 177 - i, 75 - i);
		}
	    }
	    instructOption = c.getChar (); // program takes input so that user can go back to mainMenu()
	    if (instructOption == '!') // If the user presses '!'
	    {
		pauseMenu = true; // displays indicators
	    }
	    else if (instructOption == '\n' && pauseMenu == true) // Program exits loop if user presses enter after pressing '!'
	    {
		break;
	    }
	    else if (pauseMenu == true) // if user presses a key other than enter and has pressed '!'
	    {
		JOptionPane.showMessageDialog (null, "Press enter to register your option. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    else // Errortrap- message instructs user how to go back to main menu
	    {
		JOptionPane.showMessageDialog (null, "Press '!' to go back to main menu. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }


    // highScoreMenu displays a background and title for the leaderboard; method is used for efficiency as background has to be refreshed
    public void highScoreMenu ()
    {
	menuBackground (); // menuBackground() displays a checkered background for leaderboard
	c.setColor (Color.black);
	c.fillRoundRect (280, 140, 170, 50, 25, 25);
	c.setColor (Color.white);
	c.fillRoundRect (283, 143, 164, 44, 25, 25);
	// Background for title- Leaderboard
	c.setColor (Color.black);
	c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	c.drawString ("3", 295, 160);
	c.setFont (new Font ("SansSerif.BOLD", 1, 35));
	c.drawString ("L", 310, 175);
	c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	c.drawString ("eaderboard", 330, 175);
	// Title- 3 Leaderboard
	c.setFont (new Font ("SansSerif.BOLD", 1, 35));
	c.setColor (Color.black);
	c.drawString ("Name", 50, 228);
	c.drawString ("Level", 320, 228);
	c.drawString ("Score", 500, 228);
	c.setColor (new Color (255, 255, 84));
	c.drawString ("Name", 50, 225);
	c.drawString ("Level", 320, 225);
	c.drawString ("Score", 500, 225);
	// Leaderboard is separated by name, level, and score
    }


    /*
	Local Variable List- highScore()

	Name            |   Data type    |   Description
       -----------------|------------ ---|-------------------------------------------------------------------------------------------------------------
	scorePause      | boolean        | Used to check whether the user has pressed '!' to go back to main menu
	refresh         | boolean        | Used to determine whether the user wants to clear the leaderboard
	linesScore      | String         | Used to take in each line (player information) from the high score text file for display
	playerInfo      | String[]       | Different information from linesScore is split into playerInfo so that elements can be separately displayed
	scoreOption     | char           | Takes in all input in highScore()
	scoreSpace      | int            | Used in a for loop so that scores are automatically placed going down
	rank            | int            | Used in a for loop so that the ranks of the players are automatically displayed (1-10)
    */
    // highScore() displays a top-10 leaderboard separated by name, level, score; user also has the choice to clear the leaderboard
    public void highScore ()
    {
	// continue
	boolean scorePause = false;
	boolean refresh = false;
	String linesScore = "";
	String[] playerInfo = new String [2]; // playerInfo has 3 values: contains level, score, and player name, in order
	char scoreOption = ' ';
	int scoreSpace;
	int rank;
	highScoreMenu (); // highScoreMenu displays a background and title for highScore()
	while (true) // while loop repeats until the user returns to main menu
	{
	    scoreSpace = 0; // scoreSpace resets for display; player information on leaderboard incremently goes down
	    rank = 1; // rank resets so that leaderboard goes from 1-10 (if necessary)
	    try
	    {
		BufferedReader readerScore = new BufferedReader (new FileReader ("HighScores_Crossword.txt"));
		for (int i = 1 ; i <= 10 ; i++) // 10 lines are read for the 10 players on the leaderboard (player information)
		{
		    linesScore = readerScore.readLine (); // line is read
		    if (i == 1 && linesScore.charAt (0) == '9') // if the levelNum of the highest score is 9- 9 doesn't exist and used to tell "null" scores
		    {
			c.setColor (new Color (245, 149, 149));
			c.setFont (new Font ("SansSerif.BOLD", 10, 25));
			c.drawString ("Play to be the first one on the leaderboard!", 105, 302);
			c.setColor (Color.black);
			c.setFont (new Font ("SansSerif.BOLD", 10, 25));
			c.drawString ("Play to be the first one on the leaderboard!", 105, 300);
			break;
			// Message if no one has yet placed on the leaderboard
		    }
		    if (linesScore.charAt (0) != '9') // If there is at least one user on the leaderboard
		    {
			playerInfo = linesScore.split (" "); // playerInfo contains levelNum, player score, and player name
			c.setColor (Color.black);
			c.setFont (new Font ("SansSerif.BOLD", 10, 25));
			c.drawString (Integer.parseInt (playerInfo [0]) + 1 + "", 320, 270 + scoreSpace); // level from playerInfo is displayed
			c.drawString (playerInfo [1], 500, 270 + scoreSpace); // player score from playerInfo is displayed
			c.drawString (rank + "." + playerInfo [2], 50, 270 + scoreSpace); // player name from playerInfo is displayed
			rank++; // rank moves down (1-10)
			scoreSpace += 30; // score display position moves down
		    }
		}
		readerScore.close (); // readerScore closes
	    }
	    catch (IOException e)
	    {
	    }
	    c.setColor (new Color (176, 176, 176, 100)); // color is set to light gray
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (20 + x, 20, 40, 40);
	    }
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (60 + x, 60, 40, 40);
	    }
	    c.setColor (Color.white);
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (60 + x, 20, 40, 40);
	    }
	    for (int x = 0 ; x <= 80 ; x += 80)
	    {
		c.fillRect (20 + x, 60, 40, 40);
	    }
	    // redraws small portion background of the menu box to cover the arrows displayed to illustrate menu box
	    c.setColor (Color.black);
	    c.fillRect (52, 25, 100, 55);
	    c.setColor (new Color (245, 149, 149));
	    c.fillRect (54, 27, 96, 51);
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
	    c.drawString ("!", 55, 55);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 40));
	    c.drawString ("M", 65, 70);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 25));
	    c.drawString ("enu", 100, 70);
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 15));
	    c.drawString ("*Enter '!'", 70, 95);
	    // dislays menu box
	    if (scorePause == true) // if user has pressed '!'
	    {
		for (int i = 0 ; i <= 20 ; i++)
		{
		    c.setColor (Color.black);
		    c.drawLine (27 + i, 35 + i, 27 + i, 75 - i);
		    c.drawLine (177 - i, 35 + i, 177 - i, 75 - i);
		}
		// displays arrows that indicate the user has to press enter once '!' is pressed
	    }
	    c.setColor (Color.black);
	    c.fillRect (560, 25, 130, 60);
	    c.setColor (Color.yellow);
	    c.fillRect (565, 30, 120, 50);
	    c.setColor (Color.black);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 30));
	    c.drawString ("C", 590, 58);
	    c.setFont (new Font ("SansSerif.BOLD", 1, 25));
	    c.drawString ("lear", 610, 57);
	    c.setFont (new Font ("SansSerif", 3, 15));
	    c.drawString ("Enter 'c'/'C'", 585, 75);
	    // Clear box- Highscores
	    scoreOption = c.getChar (); // scoreOption takes in user input from highScore
	    if (scoreOption == '!') // If user presses '!' to go back to main menu
	    {
		scorePause = true;
	    }
	    else if (scorePause == true && scoreOption == '\n') // Program goes back to main menu if user enters '\n' and has already pressed '!'
	    {
		break;
	    }
	    else if (scoreOption == 'c' || scoreOption == 'C') // If user presses 'c'/'C' to clear the high scores
	    {
		scorePause = false;
		refresh = true;
	    }
	    else if (scorePause == true && scoreOption != '\n') // If user has already pressed '!' but doesn't enter '\n'
	    {
		JOptionPane.showMessageDialog (null, "Press enter to return back to main menu. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    else // If the user doesn't input a proper option
	    {
		JOptionPane.showMessageDialog (null, "Input a valid option. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    if (refresh == true) // if user has chosen to clear the high scores
	    {
		c.setColor (new Color (0, 0, 0, 200));
		c.fillRect (0, 0, 720, 620);
		// black shadow is drawn over the screen
		c.setColor (Color.black);
		c.fillRect (560, 25, 130, 60);
		c.setColor (Color.yellow);
		c.fillRect (565, 30, 120, 50);
		c.setColor (Color.black);
		c.setFont (new Font ("SansSerif.BOLD", 1, 30));
		c.drawString ("C", 590, 58);
		c.setFont (new Font ("SansSerif.BOLD", 1, 25));
		c.drawString ("lear", 610, 57);
		c.setFont (new Font ("SansSerif", 3, 15));
		c.drawString ("Enter 'c'/'C'", 585, 75);
		// Clear box is redrawn and highlighted
		c.setColor (Color.white);
		c.setFont (new Font ("SansSerif.BOLD", 3, 15));
		c.drawString ("*Warning- Erases player data", 480, 110);
		c.drawString ("Press 'y' or 'Y' to clear leaderboard", 435, 130);
		c.drawString ("Press ANY OTHER KEY to go back", 440, 150);
		// Additional instructions/ warning if user presses 'c'/'C' to clear high score
		scoreOption = c.getChar (); // scoreOption is used to either delete data or cancel option
		if (scoreOption == 'y' || scoreOption == 'Y') // Resets the file if user chooses to clear leaderboard by pressing 'y' or 'Y'
		{
		    try
		    {
			PrintWriter output = new PrintWriter (new FileWriter ("HighScores_Crossword.txt"));
			for (int i = 0 ; i < 10 ; i++) // Default player information is printed 10 times to the high score text file
			{
			    output.println ("9" + " 0" + " 0"); // 9 at the beginning of the player information lets the program know it shouldn't be displayed
			}
			output.close ();
		    }
		    catch (IOException e)
		    {
		    }
		    refresh = false; // refresh is reset to fresh once the program has succesfully cleared the file
		}
		else // If the user has pressed a key other than 'y'/ 'Y'- doesn't want to refresh the leaderboard
		{
		    refresh = false; // refresh is reset
		}
		highScoreMenu (); // checkered background is redrawn
	    }
	}
    }


    // goodbye() diplays a goodbye message and contact information if user chooses to exit
    public void goodbye ()
    {
	menuBackground (); // displays basic checkered background
	c.setColor (Color.black);
	c.fillRoundRect (310, 140, 100, 50, 25, 25);
	c.setColor (Color.white);
	c.fillRoundRect (313, 143, 94, 44, 25, 25);
	c.setColor (Color.black);
	c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	c.drawString ("4", 325, 160);
	c.setFont (new Font ("SansSerif.BOLD", 1, 35));
	c.drawString ("E", 340, 175);
	c.setFont (new Font ("SansSerif.BOLD", 1, 20));
	c.drawString ("xit", 365, 175);
	// 4. Exit- Menu Title
	c.setColor (Color.black);
	c.fillRect (180, 250, 360, 100);
	c.setColor (Color.white);
	c.fillRect (185, 255, 350, 90);
	c.setColor (Color.black);
	c.setFont (new Font ("Helvetica.BOLD", 1, 25));
	c.drawString ("Thank you for using this program!", 160, 230);
	c.setFont (new Font ("Serif", 10, 25));
	c.drawString ("This program was made by", 215, 285);
	c.setFont (new Font ("Serif.BOLD", 10, 25));
	c.setColor (Color.red);
	c.drawString ("OSCAR HAN PRODUCTIONS", 192, 327);
	c.setColor (Color.black);
	c.drawString ("OSCAR HAN PRODUCTIONS", 190, 325);
	c.setFont (new Font ("Helvetica", 10, 25));
	c.drawString ("Contact us at:", 280, 390);
	c.setFont (new Font ("Helvetica.BOLD", 1, 25));
	c.drawString ("oscarhan.productions@gmail.com", 155, 430);
	// Production name and contact information
	try
	{
	    Thread.sleep (5000);
	}
	catch (Exception e)
	{
	}
	c.close (); // program closes after 5 seconds, allowing the user to view contact information
    }


    public static void main (String[] args)  // main method controls program execution
    {
	CrosswordPuzzle cp = new CrosswordPuzzle (); // creates instance of the class
	cp.splashScreen ();
	while (true) // program repeats until the user chooses to exit from main menu
	{
	    cp.mainMenu (); // user enters their respective coice (cp.mainNum) from mainMenu()
	    if (cp.mainNum == 1) // If user wishes to play a crossword level
	    {
		cp.levelScreen ();
	    }
	    else if (cp.mainNum == 2) // If user wishes to view instructions
	    {
		cp.instructions ();
	    }
	    else if (cp.mainNum == 3) // If user wishes to view the leaderboard
	    {
		cp.highScore ();
	    }
	    else if (cp.mainNum == 4) // If user wishes to exit the program
	    {
		break; // Program leaves while loop and executes goodbye()
	    }
	}
	cp.goodbye ();
    }
}


