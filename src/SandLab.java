import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int ACID = 4;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[5];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[ACID] = "Acid";
    
    //1. Add code to initialize the data member grid with same dimensions
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
	  grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
	for (int row = 0; row < grid[0].length; row++)
	{
		for (int col = 0; col < grid[1].length; col++)
		{
			if (grid[row][col] == METAL)
			{
				display.setColor(row, col, Color.GRAY);
			}
			else if (grid[row][col] == SAND)
			{
				display.setColor(row, col, Color.YELLOW);
			}
			else if (grid[row][col] == WATER)
			{
				display.setColor(row, col, Color.BLUE);
			}
			else if (grid[row][col] == ACID)
			{
				display.setColor(row, col, Color.GREEN);
			}
			else
			{
				display.setColor(row, col, Color.BLACK);
			}
		}
	}
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
	  int rowRandom = (int)(Math.random() * grid[0].length - 1);
	  int colRandom = (int)(Math.random() * grid[1].length - 1);
	  
	  if (grid[rowRandom][colRandom] == SAND)
	  {
		  if (grid[rowRandom + 1][colRandom] == EMPTY)
		  {
			  grid[rowRandom][colRandom] = EMPTY;
			  grid[rowRandom + 1][colRandom] = SAND;
		  }
		  else if (grid[rowRandom + 1][colRandom] == WATER)
		  {
			  grid[rowRandom][colRandom] = WATER;
			  grid[rowRandom + 1][colRandom] = SAND;
		  }
	  }
	  
	  if (grid[rowRandom][colRandom] == WATER)
	  {
		  if (grid[rowRandom + 1][colRandom] == EMPTY)
		  {
			  grid[rowRandom][colRandom] = EMPTY;
			  grid[rowRandom + 1][colRandom] = WATER;
		  }
		  else if (grid[rowRandom][colRandom + 1] == EMPTY)
		  {
			  grid[rowRandom][colRandom] = EMPTY;
			  grid[rowRandom][colRandom + 1] = WATER;
		  }
		  else if (grid[rowRandom][colRandom - 1] == EMPTY)
		  {
			  grid[rowRandom][colRandom] = EMPTY;
			  grid[rowRandom][colRandom - 1] = WATER;
		  }
		  else
		  {
			  grid[rowRandom + 1][colRandom] = WATER;
		  }
	  }
	  
	  if (grid[rowRandom][colRandom] == ACID)
	  {
		  if (grid[rowRandom + 1][colRandom] != ACID)
		  {
			  grid[rowRandom][colRandom] = EMPTY;
			  grid[rowRandom + 1][colRandom] = ACID;
		  }
		  else if (grid[rowRandom][colRandom + 1] != ACID)
		  {
			  grid[rowRandom][colRandom] = EMPTY;
			  grid[rowRandom][colRandom + 1] = ACID;
		  }
		  else if (grid[rowRandom][colRandom - 1] != ACID)
		  {
			  grid[rowRandom][colRandom] = EMPTY;
			  grid[rowRandom][colRandom - 1] = ACID;
		  }
	  }
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
