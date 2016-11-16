# Testing Lab Files

This repository is the Java version of the lab files. Git tags are used 
to mark various points in the development history.

## Instructions

You are to test-drive the development of a console-based game of 2-player blackjack where the computer
plays the dealer. You may *not* run the game (or even write a `main()`) until it has a complete, runnable specification. 

You must write runnable tests for every algorithm you write (anything with logic). You do not have
to write tests for library classes/functions (e.g. System.in can be assumed to read from the process's 
standard input), but *you must test any code that uses them*.

### Part 1 -- Basics of specification testing and injection

- Specifications must name and test all behaviors
- Generate enumerations for Rank and Suit.
    - Define the value of Rank as the max scoring value of that rank, e.g. Ace = 11
- Generate a Card abstraction that holds rank/suit
    - Cards must have a functional equals(), hashCode(), and toString() (with spec)
- Generate a Deck abstraction
    - Methods: 
        - Constructor generates a deck in rank/suit order
        - `int size()` indicates how many cards remain
        - `Card dealCard()` removes the "top card" and returns it. Throws OutOfCardsError if no cards remain.
        - `shuffle()` mutates the deck in place to randomize the location of cards
            - The shuffle algorithm requires some advanced thinking: how do you easily 
              prove your logic is correct without basing the tests on what you are specifically doing?
  
### Part 2 -- I/O Layer: integration/manual testing

Use your result from part 1 to continue:

- Create an I/O layer for obtaining console input and showing output: ConsoleIO
    - Must be injectable
    - Make manual integration tests
    
### Part 3 -- Injection

- Create a `String Prompt.prompt(question, legalResponsePattern, defaultReturnValue)` function:
    - Empty response (or IO exceptions) must return the default
    - Re-asks question until legal response (if resp is non-empty)
    - Trims the user input (removes whitespace from endpoints)
    - Must be UNIT testable (not integration tested)
- Improve your `Deck.shuffle()` tests, if you have not yet used injection there.

### Part 4 -- Round out algorithms

- Make a Hand abstraction (container of cards)
    - `String visibleHand(boolean hideBottom)` - Returns a string that represents current hand. The option indicates that the first card should be obscured.
    - `Set<Card> getCards()` - Returns cards as a set
    - `void addCard(Card)` 
    - `int size()` - returns the number of cards in the hand
- Make a Player interface or abstract class
    - `Action nextAction(Hand otherHand)` - Returns `Hit`, `Stay`, or `Busted` (based on known cards of the other player's hand)
    - `Hand getHand();` - Returns the player's hand
- Implement and spec test a `HumanPlayer` that interacts with the console. 
- Implement and spec test a `BotPlayer` 
    - Hits if not won score is under 17 (or something similar).

### Part 4 -- Dealing with more complex logic.

- Create a `BlackjackGame` class with:
    - `play()`: the main logic of game
    - Cleanly code algorithms, and make each routine cleanly testable. This
      is harder than it sounds.

Discuss with classmates the various ways you go about splitting this up 
and making it all testable. Algorithms that have multiple steps and decisions can
make for complicated test, which in turn indicate that you should redesign
your algorithm's structure. 

### Part 5 -- Implement main() AND TEST it before running it:

- Main logic is:
    - Inject a game
    - Play the game
- Write behavior tests for main()!

Now run your fully functioning game for the first time!

