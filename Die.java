/**
 * Represents one die (singular of dice) with faces showing values
 * between 1-6
 * 
 * @author Yip
 * @version 18/02/22
 */
public class Die
{
    // instance variables - replace the example below with your own
    private final int MAX = 6;  // maximum face value
    private int faceValue;      // current value showing on the die

    /**
     * Constructor for objects of class Die
     */
    public Die()
    {
        // initialise instance variables
        roll();
    }

    /**
     * Rolls the die and returns the result
     *
     */
    public void roll()
    {
        faceValue = (int)(Math.random() * MAX) + 1;     // Returns a random number between 1-6
    }
    
    /**
     * faceValue getter
     * 
     * @return int the new face value
     */
    public int getFaceValue()
    {
        return faceValue;
    }
}