import java.util.ArrayList;

public class Abilities extends Menu {


    //will attack for 10
    public boolean attack(Character attackingToon, Character inactiveToon) {
        if (attackingToon.isDead()) return false;

//        if (attemptBlock()) {
//            System.out.printf("%s blocks %s's attack!\n", inactiveToon.getName(),
//                    attackingToon.getName());
//            return false;
//        } else {
//            inactiveToon.healthPoints += -10;
//        }
        inactiveToon.healthPoints += -10; //here for testing
        if (inactiveToon.healthPoints <= 0) {
            inactiveToon.healthPoints = 0;
            attackingToon.setDead(true);
        }
        return true;
    }




    //heals for 8 HP
    public int heal(Character healingToon) {
        //int passHealValue = -1;
        int healed = 8;
        if (healingToon.isDead()) return -1;

        if (healingToon.getHealthPoints() >= 93) {
            healed = 100 - healingToon.getHealthPoints();
            //passHealValue = super.amountHealed;
            healingToon.healthPoints += healed;
        } else {
            healingToon.healthPoints += 8;
        }
        return healed;
    }

    public int block(Character blockingToon) {

        if (blockingToon.getBlockTimer() < 0.0) {
            return 0;
        } else {
             return -10;
        }
    }

    //level /
    public boolean attemptBlock() {
        generateRandoms();
        return getFirstRandom() >= getSecondRandom();
    }


}
