import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Menu extends CharacterList {

    private Scanner stdIn = new Scanner(System.in);
    private int currentPlayerIndex = -1;
    private int inactivePlayerIndex = -1;
    private int menuOption = -1;
    private boolean winnerFound = false;
    public int amountHealed;
    private int firstRandom;
    private int secondRandom;
   // private int[] randoms;

    private String chooseName() {
        int temp = getToons().size() + 1;
        System.out.printf("Choose Player %s's character name: ", temp);

        return formatName(stdIn.next());
    }

    private String formatName(String unformattedName) {
        StringBuilder choppedName = new StringBuilder();
        for (int i = 1; i < unformattedName.length(); i++) {
            choppedName.append(unformattedName.charAt(i));
        }
        return String.valueOf(unformattedName.charAt(0)).toUpperCase() + choppedName;
    }

    public void createPlayers() {
        while (getToons().size() < 2) {
            getToons().add(new Character(chooseName()));
        }
    }

    public void displayPlayerMenuOptions() {
        String[] playerMenuOptions = new String[]{
                "Attack",
                "Block",
                "Heal"
        };

        for (int i = 0; i < playerMenuOptions.length; i++) {
            System.out.printf("%s. %s\n", i+1, playerMenuOptions[i]);
        }


        menuOption = collectMenuOption(playerMenuOptions);
    }

    //used to validate player selection is an integer and between 1 - size of Array
    private int collectMenuOption(String[] menuOptions) {
        while (!stdIn.hasNextInt()) {
            System.out.println("Submit a numerical value");
            stdIn.next();
        }
        int temp = stdIn.nextInt();
        while (temp > menuOptions.length || temp < 1) {
            System.out.printf("Must be between 1-%s\n", menuOptions.length);
            while (!stdIn.hasNextInt()) {
                System.out.println("Submit a numerical value");
                stdIn.next();
            }
            temp = stdIn.nextInt();
        }
        return temp;
    }

    public void executeMove(int validatedInput, int currentPlayerIndex) {
        Abilities abilities = new Abilities();
        boolean successfulAttack = false;
        int amountHealed = -1;
        switch (validatedInput) {
            case 1:
                successfulAttack = abilities.attack(getToons().get(currentPlayerIndex),
                        getToons().get(inactivePlayerIndex));
                break;
            case 2:
               // abilities.block(getToons().get(currentPlayerIndex));
                break;
            case 3:
                //announceHeal(getToons(), currentPlayerIndex);
                amountHealed = abilities.heal(getToons().get(currentPlayerIndex));
                break;
        }

        if (successfulAttack) {
            announceAttack(getToons(), currentPlayerIndex);
        } else if (validatedInput == 2) {
            //announceBlock here
        } else if (validatedInput == 3) {
            announceHeal(getToons(), currentPlayerIndex, amountHealed);
        }
        accountForMove();
    }

    public void generateRandoms() {
        Random random = new Random();
        int[] randoms = new int[getToons().size()];

        for (int i = 0; i < randoms.length; i++) {
            randoms[i] = getToons().get(i).getName().length() + random.nextInt(50);
            if (i == 1) {
                if (randoms[0] == randoms[1]) {
                    randoms[0] = getToons().get(0).getName().length() + random.nextInt(50);
                }
            }
        }
        //what the fuuuuuck

////        while (randoms[0] == randoms[1]) {
////            randoms[0] = getToons().get(0).getName().length() + random.nextInt(50);
////        }
//        firstRandom = randoms[0];
//        secondRandom = randoms[1];
    }

    public void determineFirstPlayer(ArrayList<Character> toons) {
        if (firstRandom > secondRandom) {
            currentPlayerIndex = 0;
            inactivePlayerIndex = 1;
        } else {
            currentPlayerIndex = 1;
            inactivePlayerIndex = 0;
        }
        announceFirstMove(toons, currentPlayerIndex);
    }

    private void announceFirstMove(ArrayList<Character> toons, int index) {
        System.out.printf("\n%s moves first!\n", toons.get(index).getName());
    }

    public void accountForMove() {
        //if player 1 is active, make player 2 active then make p1 inactive
        if (currentPlayerIndex == 0) {
            currentPlayerIndex = 1;
            inactivePlayerIndex = 0;
            // if player 2 is active, make player 1 active then make p2 inactive
        } else if (currentPlayerIndex == 1) {
            currentPlayerIndex = 0;
            inactivePlayerIndex = 1;
        }
        System.out.printf("\n%s is up next!\n", getToons().get(currentPlayerIndex).getName());
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void announceAttack(ArrayList<Character> toons, int attackingToonIndex) {
        int defendingToonIndex = -1;

        if (attackingToonIndex == 0) {
            defendingToonIndex = 1;
        } else if (attackingToonIndex == 1) {
            defendingToonIndex = 0;
        }

        System.out.printf("%s attacks %s for 10 HP.\n", toons.get(attackingToonIndex).getName(),
                toons.get(defendingToonIndex).getName());
    }

    public void announceHeal(ArrayList<Character> toons, int healingToonIndex, int amountHealed) {
        if (toons.get(healingToonIndex).getHealthPoints() == 100) {
            System.out.printf("%s is already at full health!\n", toons.get(healingToonIndex).getName());
        } else if (toons.get(healingToonIndex).getHealthPoints() >= 93) {
           // amount = 100 - toons.get(healingToonIndex).getHealthPoints();
            if (toons.get(healingToonIndex).getHealthPoints() == 100) {
                System.out.printf("%s heals themselves for %s HP, back to full health!\n",
                        toons.get(healingToonIndex).getName(), amountHealed);
            } else {
                System.out.printf("%s heals themselves for %s HP!\n",
                        toons.get(healingToonIndex).getName(), amountHealed);
            }

        } else if (toons.get(healingToonIndex).getHealthPoints() <= 92) {
            System.out.printf("%s heals themselves for 8 HP to %s HP!\n",
                    toons.get(healingToonIndex).getName(),
                    //line break here for debugging
                    toons.get(healingToonIndex).getHealthPoints());  //fix this +8
        }

        System.out.printf("%s has %s HP left!\n", toons.get(healingToonIndex).getName(),
                toons.get(currentPlayerIndex).getHealthPoints()); //fix this +8
    }

    public boolean checkForWinner(ArrayList<Character> toons) {
        for (int i = 0; i < toons.size(); i++) {
            if (toons.get(i).isDead()) {
                announceWinner(toons, i);
                return true;
            }
        }
        return false;
    }

    public void announceWinner(ArrayList<Character> toons, int loserIndex) {
        //wtf is this dude
        int winnerIndex = -1;
        if (loserIndex == 0) {
            winnerIndex = 1;
        } else if (loserIndex == 1) {
            winnerIndex = 0;
        }
        System.out.printf("%s has defeated %s!\n", toons.get(winnerIndex).getName(),
                toons.get(loserIndex).getName());
    }

    public int getMenuOption() {
        return menuOption;
    }

    public boolean isWinnerFound() {
        return winnerFound;
    }

    public int getAmountHealed() {
        return amountHealed;
    }

    public int getFirstRandom() {
        return firstRandom;
    }

    public int getSecondRandom() {
        return secondRandom;
    }
}
