/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.Scanner;

/**
 *
 * @author Sanket
 */
public class BlackJack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Welcome to BlackJack");
        
        //Creating playing Deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffle(); 
        //Creating a deck for the player
        Deck playerDeck = new Deck();
        //Creating a deck for the dealer
        Deck dealerDeck = new Deck();
        
        double playerMoney = 1000.00;
        //Scanner for user input
	Scanner userInput = new Scanner(System.in);
        
        //Game Loop
        while(playerMoney>0){
            //Take Bet
            System.out.println("You have $" + playerMoney + ", how much would you like to bet?");
            double playerBet = userInput.nextDouble();
            boolean endRound = false;
            
            if(playerBet > playerMoney){
                    //Break if they bet too much
                    System.out.println("You cannot bet more than you have.");
                    break;
            }



            System.out.println("Dealing...");
            
            //Player gets two cards
            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);

            //Dealer gets two cards
            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);

            //While loop for drawing new cards
            while(true)
            {
                //Display player cards
                System.out.println("Your Hand:" + playerDeck.toString());

                //Display Value
                System.out.println("Your hand is currently valued at: " + playerDeck.cardsValue());

                //Display dealer cards
                System.out.println("Dealer Hand: " + dealerDeck.getCard(0).toString() + " and [hidden]");

                //What do they want to do
                System.out.println("Would you like to (1)Hit or (2)Stand");
                int response = userInput.nextInt();	
                
                //They hit
                if(response == 1){
                    playerDeck.draw(playingDeck);
                    System.out.println("You draw a:" + playerDeck.getCard(playerDeck.deckSize()-1).toString());
                    
                    //Bust if they go over 21
                    if(playerDeck.cardsValue() > 21){
                        System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }

                //Stand
                if(response == 2){
                        break;
                }

            }

            //Reveal Dealer Cards
            System.out.println("Dealer Cards:" + dealerDeck.toString());
            
            //See if dealer has more points than player
            if((dealerDeck.cardsValue() > playerDeck.cardsValue())&&endRound == false){
                System.out.println("Dealer beats you " + dealerDeck.cardsValue() + " to " + playerDeck.cardsValue());
                playerMoney -= playerBet;
                endRound = true;
            }
            
            //Dealer hits at 16 stands at 17
            while((dealerDeck.cardsValue() < 17) && endRound == false){
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
            }
            
            //Display value of dealer
            System.out.println("Dealers hand value: " + dealerDeck.cardsValue());
            
            //Determine if dealer busted
            if((dealerDeck.cardsValue()>21)&& endRound == false){
                System.out.println("Dealer Busts. You win!");
                playerMoney += playerBet;
                endRound = true;
            }
            
            //Determine if push
            if((dealerDeck.cardsValue() == playerDeck.cardsValue()) && endRound == false){
                System.out.println("Push.");
                endRound = true;
            }
            
            //Determine if player wins
            if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false){
                System.out.println("You win the hand.");
                playerMoney += playerBet;
                endRound = true;
            }
            
            else if(endRound == false) //dealer wins
            {
                System.out.println("Dealer wins.");
                playerMoney -= playerBet;
            }

            //End of hand - put cards back in deck
            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("End of Hand.");

        }
        //Game is over
        System.out.println("Game over! You lost all your money. :(");

        //Close Scanner
        userInput.close();

    }
    
}
