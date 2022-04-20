/**
 * Create two Die objects and rolls them serveal times
 *
 * @author Yip
 * @version 18/02/22
 */
public class Dice
{
    private int sum;    // Total of two dice
    private Die die1, die2;     // Declare two Dice
    private int die1Face, die2Face;
    
    /**
     * Construct two Dice and set the sum
     */
    public Dice() {
        // Create instances of the dice
        die1 = new Die();
        die2 = new Die();
        
        getDie1Face();
        getDie2Face();
        getSum();
    }
    
    /**
     * Rolls the dice
     */
    public void rollDice() {
        die1.roll();
        die2.roll();
    }
 
    /**
     * Dice 1 getter
     * Returns the value of the first die
     */
    public int getDie1Face() {
        die1Face = die1.getFaceValue();
        return die1Face;
    }
    
    /**
     * Dice 2 setter
     * Returns the value of the second die
     */
    public int getDie2Face() {
        die2Face = die2.getFaceValue();
        return die2Face;
    }
    
    /**
     * Dice sum getter
     * Returns the total of the two dice
     */
    public int getSum() {
        sum =  getDie1Face() + getDie2Face();
        return sum;
    }
}