/**
 *  Street Crap automatically rolls a number
 *  If it is 7/11, user wins immediately
 *  If it is 2/3/12, user loses immediately
 *  If it is anything else, it will be set as the point number
 *  User continues to roll until the point number is rolled (then win)
 *  If a 7 is rolled in this process, user loses
 *
 * @author Yip
 * @version 11-03-2022
 */

import java.util.Scanner;   // Import the Scanner class

public class StreetCrap
{
    // instance variables - replace the example below with your own
    private int comeOut, secondRoll;
    private int round;
    private Dice dice;
    private int winCount, loseCount;
    private int pointNum;
    private boolean again, choice, endGame;

    /**
     * Constructor for objects of class streetCrap
     */
    public StreetCrap()
    {
        // initialise instance variables
        round = 1;
        
        // initialise counter
        winCount = 0;
        loseCount = 0;
    }
    
    /**
     * Line break
     */
    public void lineBreak() {
        System.out.print(System.lineSeparator());
    }
    
    /**
     * Put non-static main method in a static method
     */
    public static void main(String []args)throws Exception {
        // convert non-static runRound() to static 
        StreetCrap sm = new StreetCrap();
        sm.main();
    }
    
    /** 
     * MAIN LOOP
     */
    public void main() {
        System.out.println("Welcome to Street Craps.");
        again = true;
        while (again == true) {
            mainGame();
            playAgain();
            round++;
        }
        printStats();
    }
    
    /**
     * Main game loop. Re-roll the dice if 'r' is pressed 
     */
    public void mainGame() {
        comeOutRoll();
        
        while (endGame == false) {
            String option;
            Scanner sc = new Scanner(System.in);
            
            // ask the user to enter 'r' to roll the dice 
            System.out.println("Press 'r' to roll");
            option = sc.nextLine().toLowerCase();
            
            // prevent invalid input
            if (option.equals("r")) { 
                mainRoll(); 
            } else {
                System.out.println("Please enter 'r'.");
            }
        }
    }
    
    public void mainRoll() {
        System.out.println("Rolling...");
        // roll the dice
        dice.rollDice(); 
        // get the sum of the pair of dice
        secondRoll = dice.getSum();
            
        System.out.println("Result: " + secondRoll);
        if (secondRoll == pointNum) { // match point number (win)
            System.out.println("You got the point number. You won!");
            lineBreak();
            winCount += 1;
            endGame = true;
        } else if (secondRoll == 7) { // got a 7 (lose)
            System.out.println("You got an 'Out 7'. You lost.");
            lineBreak();
            loseCount += 1;
            endGame = true;
        } else { // got something else (re-roll)
            System.out.println("You can roll again.");
            lineBreak();
            endGame = false;
        }
    }
    
    /**
     * Come Out roll (the first automatic roll)
     */
    public int comeOutRoll() {
        endGame = false;
        System.out.println("-------------- ROUND " + round + " --------------");
        System.out.println("----------- COME OUT ROLL -----------");
        // create new dice
        dice = new Dice();
        
        dice.rollDice();
        comeOut = dice.getSum();
        
        if (comeOut == 7 || comeOut == 11) { // wins immediately
            System.out.println("You got " + comeOut + " in the come out roll. You won!");
            lineBreak();
            winCount++; // add 1 to win coutner
            endGame = true; // leave game loop
        } else if (comeOut == 2 || comeOut == 3 || comeOut == 12) {
            System.out.println("You got " + comeOut + " in the come out roll. You lost.");
            lineBreak();
            loseCount++;
            endGame = true;
        } else {
            System.out.println(comeOut + " has been set as the point number.");
            lineBreak();
            pointNum = comeOut; // set point number as the number rolled
            endGame = false; // continue the next roll loop
        } 
        return pointNum;
    }
    
    /**
     * print out the win/lose statistics
     */
    public void printStats()
    {
       System.out.println("WIN: " + winCount + "  LOSE: " + loseCount);
    }
    
    /**
     * Check if user wants to play again
     */
    public boolean playAgain() {
        String option;
        Scanner sc = new Scanner(System.in);
        
        choice = false;
        again = false;
        
        while (choice == false) { // check if there is enough balance
            // ask if player wants to play again
            System.out.println("Would you like to play again? (y/n)");
            option = sc.nextLine().toLowerCase(); 
                
            if (option.equals("y")) {
                // rerun the game loop
                choice = true;
                again = true;
            } else if (option.equals("n")) {
                // break out of the game loop
                choice = true;
                again = false;
            } else {
                // prevent invalid input
                System.out.println("Enter 'y' or 'n'");
                choice = false;
            }
        }
        return again;
    }
}
