package trivia;

public class Player {
    public static final int MAX_NUMBER_OF_PLACE = 12;
    public static final String POP = "Pop";
    public static final String SCIENCE = "Science";
    public static final String SPORTS = "Sports";
    public static final String ROCK = "Rock";
    private String playerName;
    private int place = 0;
    private int sumOfGoldCoins = 0;
    private boolean isInPenaltyBox = false;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return this.playerName;
    }

    public void moveForwardSteps(int steps) {
        this.place += steps;
        if (this.place > MAX_NUMBER_OF_PLACE - 1) this.place -= MAX_NUMBER_OF_PLACE;
    }

    public int getPlace() {
        return this.place;
    }

    public String getCurrentCategory() {
    	switch(place % 4){
    		case 0: return POP;
    		case 1: return SCIENCE;
    		case 2: return SPORTS;
    		case 3: return ROCK;
    	}
    	return "There is a bug";
    }

    public void winAGoldCoin() {
        this.sumOfGoldCoins++;
    }

    public int countGoldCoins() {
        return this.sumOfGoldCoins;
    }

    public boolean isInPenaltyBox() {
        return this.isInPenaltyBox;
    }

    public void getOutOfPenaltyBox() {
        this.isInPenaltyBox = false;
    }

    public void sentToPenaltyBox() {
        this.isInPenaltyBox = true;
    }
}
