/**
 * Write a description of class PigGame here.
 *
 * @author Yip
 * @version 09-03-2003
 */

import java.util.Scanner;

public class PigGame
{
    // create a pair of die
    private Dice dice;
    
    // create player
    private Player player;
    
    // create score counter
    private int user_total, comp_total;
    private int user_rtotal, comp_rtotal;
    
    // instance variables 
    private int sum, D1, D2;
    private int balance, bet;
    final int GOAL = 100; // goal score
    final int COMPMAX = 20; // maxiumum turn score for computer 
    final int MINBET = 1; // minumum bet
    final int MINBAL = 1; // minumum balance needed
    
    private String cont;
    
    boolean reachGoal, userWins, compWins, again, choice, enoughMoney;
    
    /**
     * Constructor for objects of class SimpleDiceGame
     */
    public void PigGame() {
        // initialise string constant
        cont = null;
    }
    
    /**
     * Put non-static run round method in a static main method
     */
    public static void main(String []args)throws Exception {
        // convert non-static runRound() to static 
        PigGame sm = new PigGame();
        sm.runGamble();
    }
    
    /**
     * Line break
     */
    public void lineBreak() {
        System.out.print(System.lineSeparator());
    }
    
    /**
     * Main loop for gambling
     */
    public void runGamble() {
        // create a new player and get their balance
        player = new Player();
        balance = player.getBalance();
        do {
            newBet();
            runRound();
            if (userWins) {
                balance += bet*2; // double the bet and add it to acc
                lineBreak();
                System.out.println("Congratulations! You won $" + bet*2 + ".");
                System.out.println("BALANCE: " + balance);
                lineBreak();
            } else {
                balance -= bet; // take out the bet from their acc
                lineBreak();
                System.out.println("Sorry. You lost $" + bet + ".");
                System.out.println("BALANCE: " + balance);
                lineBreak();
            }
            betAgain(); // ask if user wants to bet again
        } while (balance > 0 && again == true);
        endGamble();
    }
    
    public void newBet() {
        Scanner scan = new Scanner(System.in);
        
        enoughMoney = false;
        System.out.println("BALANCE: " + balance);
        
        while (enoughMoney == false) {
            // ask user to enter a bet
            System.out.println("Enter a bet");
            bet = scan.nextInt();
            
            if (bet > balance) {
                // limit bet to less than the balance
                System.out.println("You don't have enough balance.");
                lineBreak();
                enoughMoney = false;
            } else if (bet < MINBET) {
                // limit bet to be at least 1 dollar
                System.out.println("Please bet at least $1.");
                lineBreak();
                enoughMoney = false;
            } else {
                System.out.println("You bet " + bet + ". Good luck.");
                lineBreak();
                enoughMoney = true;
            }
        }
    }
    
    public void endGamble() {
        if (balance <= 0) { // if player has used up the money
            System.out.println("OUT OF BALANCE!");
        } else { // if player indicated to end the game
            System.out.println("BALANCE: " + balance);
            System.out.println("Thanks for playing.");
        }
    }
    
    /**
     * Run Round
     */
    public void runRound() {
        // initialise booleans
        reachGoal = false;
        userWins = false;
        compWins = false;
        
        // initialise counter
        user_total = 0;
        user_rtotal = 0;
        comp_total = 0;
        comp_rtotal = 0;
        
        // main game loop
        while (reachGoal == false) {
            // run user's turn
            user();
            user_total += user_rtotal;
            user_rtotal = 0;
            System.out.println("USER'S GRAND SCORE: " + user_total);
            
            // announce user wins and end game
            if (user_total >= GOAL) {
                System.out.println("USER WINS!");
                reachGoal = true;
                userWins = true;
                break;
            } 
            System.out.println("----------------------------------------");
            
            // run computer's turn
            computer();
            comp_total += comp_rtotal;
            comp_rtotal = 0;
            System.out.println("COMPUTER'S GRAND SCORE: " + comp_total);
            
            // announce computer wins and end game
            if (comp_total >= GOAL) {
                System.out.println("USER LOSES!");
                reachGoal = true;
                compWins = true;
                break;
            } 
            System.out.println("----------------------------------------");
        }
    }
    
    /**
     * Roll dice and evaluate result and effect on user
     */
    public void user() {
        System.out.println("User rolling...");
        // get the dice result and sum
        getResult();
        // return the meaning of dice result
        DiceChecker(D1, D2);
        
        // evaluate the returned value and manipulate counters
        if (cont == "nS") {
            // reset counter to 0, lose all points
            user_total = 0;
            user_rtotal = 0;
        } else if (cont == "nO") {
            user_rtotal = 0;
        } else if (cont == "y") {
            // add dice result to round counter
            user_rtotal += sum;
            System.out.println("USER'S TURN SCORE: " + user_rtotal);
            lineBreak();
            // ask if user wants to roll again
            userRollAgain();
        }
    }
    
    /**
     * Roll dice and evaluate result and effect on computer
     */
    public void computer() {
        System.out.println("Computer rolling...");
        getResult();
        // return the meaning of dice result
        DiceChecker(D1, D2);
        
        if (cont == "nS") {
            comp_total = 0;
            comp_rtotal = 0;
        } else if (cont == "nO") {
            comp_rtotal = 0;
        } else if (cont == "y") {
            comp_rtotal += sum;
            System.out.println("COMPUTER'S TURN SCORE: " + comp_rtotal);
            lineBreak();
            compRollAgain();
        }
    }
    
    /**
     * Check if user wants to roll again
     */
    public void userRollAgain() {
        String option;
        Scanner sc = new Scanner(System.in);
        
        // only run if hasn't reach goal score yet
        if (user_rtotal + user_total < GOAL) {
            do {
                // ask if user wants to roll or hold
                System.out.println("Roll (r) / Hold (h)");
                option = sc.nextLine().toLowerCase(); 
                
                if (option.equals("r")) {
                    // rerun user loop
                    user();
                } else if (option.equals("h")) {
                    // break out of user loop
                    return;
                }
            } while ((!option.equals("r")) && (!option.equals("h"))); // prevents invalid input
        }
    }
    
    /**
     * Check if computer CAN to roll again
     */
    public void compRollAgain() {
        // check if it has reached it's round limit score yet and if it has won 
        if (comp_rtotal < COMPMAX && comp_rtotal + comp_total < GOAL) {
            computer();
        } else if (comp_rtotal >= COMPMAX && comp_rtotal + comp_total < GOAL) {
            System.out.println("REACHED 20 / TURN OVER");
            lineBreak();
        } 
    }
    
    /**
     * Check if there is any "1"
     */
    public String DiceChecker(int D1, int D2) {
        if (D1 == 1 && D2 == 1) { // 2 "1"s. return "nS" (no continue, snake eye)
            System.out.println("SNAKE EYES / TURN OVER");
            lineBreak();
            cont = "nS";
        } else if (D1 == 1 || D2 == 1) { // 1 "1". return "nO" (no continue, one was rolled)
            System.out.println("ONE WAS ROLLED / TURN OVER");
            lineBreak();
            cont = "nO";
        } else { // no "1". return "y" (yes continue)
            cont = "y";
        }
        return cont;
    }
    
    /**
     * Roll dice and record result
     */
    public void getResult() {
        // create new dice
        dice = new Dice();
        
        // roll the dice
        dice.rollDice();
        
        // get result and rename them
        D1 = dice.getDie1Face();
        D2 = dice.getDie2Face();
        sum = dice.getSum();
        
        // print out result
        System.out.println("Result: " + D1 + " , " + D2);
    }
    
    /**
     * Check if user wants to bet again
     */
    public boolean betAgain() {
        String option;
        Scanner sc = new Scanner(System.in);
        
        choice = false;
        while (balance >= MINBAL && choice == false) { // check if there is enough balance
            // ask if player wants to play again
            System.out.println("Would you like to play again? (y/n)");
            option = sc.nextLine().toLowerCase(); 
                
            if (option.equals("y")) {
                // rerun gamble loop
                choice = true;
                again = true;
            } else if (option.equals("n")) {
                // break out of the gamble loop
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

