import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackjackGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        // Initialize the deck of cards
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        
        // Shuffle the deck
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
        
        // Initialize player and dealer hands
        List<Card> playerHand = new ArrayList<>();
        List<Card> dealerHand = new ArrayList<>();
        
        // Deal two cards to each player
        for (int i = 0; i < 2; i++) {
            playerHand.add(deck.remove(0));
            dealerHand.add(deck.remove(0));
        }
        
        // Display the initial hands
        System.out.println("Your hand: " + playerHand);
        System.out.println("Dealer's hand: [" + dealerHand.get(0) + ", ???]");
        
        // Player's turn
        while (true) {
            int playerScore = getHandValue(playerHand);
            System.out.println("Your current score: " + playerScore);
            
            if (playerScore > 21) {
                System.out.println("Bust! You lose.");
                break;
            }
            
            System.out.print("Do you want to hit (h) or stand (s)? ");
            String choice = scanner.nextLine().toLowerCase();
            
            if (choice.equals("h")) {
                playerHand.add(deck.remove(0));
                System.out.println("Your hand: " + playerHand);
            } else if (choice.equals("s")) {
                break;
            } else {
                System.out.println("Invalid choice. Enter 'h' to hit or 's' to stand.");
            }
        }
        
        // Dealer's turn
        while (getHandValue(dealerHand) < 17) {
            dealerHand.add(deck.remove(0));
        }
        
        System.out.println("Dealer's hand: " + dealerHand);
        int dealerScore = getHandValue(dealerHand);
        System.out.println("Dealer's score: " + dealerScore);
        
        // Determine the winner
        int playerScore = getHandValue(playerHand);
        if (playerScore > 21 || (dealerScore <= 21 && dealerScore >= playerScore)) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("You win!");
        }
        
        scanner.close();
    }
    
    private static int getHandValue(List<Card> hand) {
        int value = 0;
        int numAces = 0;
        
        for (Card card : hand) {
            value += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                numAces++;
            }
        }
        
        while (numAces > 0 && value > 21) {
            value -= 10;
            numAces--;
        }
        
        return value;
    }
}

enum Suit {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES
}

enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11);
    
    private final int value;
    
    Rank(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}

class Card {
    private final Rank rank;
    private final Suit suit;
    
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}